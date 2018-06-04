package com.sensorfields.grekster.android.grek.create.handler;

import static com.sensorfields.grekster.android.grek.create.Event.photoReceived;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import androidx.core.util.Pair;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.cyborg.FileService;
import com.sensorfields.grekster.android.grek.create.Effect.GetPhotoFromCamera;
import com.sensorfields.grekster.android.grek.create.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;

public final class GetPhotoFromCameraHandler
    implements ObservableTransformer<GetPhotoFromCamera, Event> {

  private final ActivityService activityService;
  private final FileService fileService;

  @Inject
  GetPhotoFromCameraHandler(ActivityService activityService, FileService fileService) {
    this.activityService = activityService;
    this.fileService = fileService;
  }

  @Override
  public ObservableSource<Event> apply(Observable<GetPhotoFromCamera> upstream) {

    return upstream.switchMap(
        effect ->
            Single.fromCallable(
                    () -> {
                      File file = fileService.cacheFile(UUID.randomUUID() + ".jpeg");
                      return Pair.create(file, fileService.getUriForFile(file));
                    })
                .flatMap(
                    data ->
                        activityService
                            .activityResult(
                                new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    .putExtra(MediaStore.EXTRA_OUTPUT, data.second),
                                null)
                            .map(activityResult -> Pair.create(data.first, activityResult)))
                .filter(
                    data ->
                        data.second != null && data.second.getResultCode() == Activity.RESULT_OK)
                .map(data -> photoReceived(data.first))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable());
  }
}
