package com.sensorfields.grekster.android.grek.create.handler;

import static com.sensorfields.grekster.android.grek.create.Event.photosReceived;

import android.content.Intent;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.grekster.android.grek.create.Effect.GetPhotoFromGallery;
import com.sensorfields.grekster.android.grek.create.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public final class GetPhotoFromGalleryHandler
    implements ObservableTransformer<GetPhotoFromGallery, Event> {

  private final ActivityService activityService;

  @Inject
  GetPhotoFromGalleryHandler(ActivityService activityService) {
    this.activityService = activityService;
  }

  @Override
  public ObservableSource<Event> apply(Observable<GetPhotoFromGallery> upstream) {
    return upstream.switchMap(
        effect ->
            activityService
                .activityResult(
                    Intent.createChooser(
                        new Intent(Intent.ACTION_GET_CONTENT)
                            .setType("image/*")
                            .addCategory(Intent.CATEGORY_OPENABLE),
                        null),
                    null)
                .map(activityResult -> photosReceived())
                .toObservable());
  }
}
