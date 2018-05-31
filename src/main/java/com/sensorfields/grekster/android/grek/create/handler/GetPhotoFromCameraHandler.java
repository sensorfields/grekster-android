package com.sensorfields.grekster.android.grek.create.handler;

import static com.sensorfields.grekster.android.grek.create.Event.photosReceived;

import android.content.Intent;
import android.provider.MediaStore;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.grekster.android.grek.create.Effect.GetPhotoFromCamera;
import com.sensorfields.grekster.android.grek.create.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public final class GetPhotoFromCameraHandler
    implements ObservableTransformer<GetPhotoFromCamera, Event> {

  private final ActivityService activityService;

  @Inject
  GetPhotoFromCameraHandler(ActivityService activityService) {
    this.activityService = activityService;
  }

  @Override
  public ObservableSource<Event> apply(Observable<GetPhotoFromCamera> upstream) {
    return upstream.switchMap(
        effect ->
            activityService
                .activityResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), null)
                .map(activityResult -> photosReceived())
                .toObservable());
  }
}
