package com.sensorfields.cyborg;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ActivityService {

  interface ActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
  }

  final List<ActivityResultListener> activityResultListeners = new ArrayList<>();

  Activity activity;

  @Inject
  ActivityService() {}

  public void onCreate(Activity activity) {
    this.activity = activity;
  }

  public void onDestroy(Activity activity) {
    if (this.activity == activity) {
      this.activity = null;
    }
  }

  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    for (ActivityResultListener listener : activityResultListeners) {
      listener.onActivityResult(requestCode, resultCode, data);
    }
  }

  public Single<ActivityResult> activityResult(Intent intent, @Nullable Bundle options) {
    return Single.create(new ActivityResultOnSubscribe(this, intent, options));
  }

  static final class ActivityResultOnSubscribe implements SingleOnSubscribe<ActivityResult> {

    private static int REQUEST_CODE = 0;

    private final ActivityService activityService;
    private final Intent intent;
    private final int requestCode;
    @Nullable private final Bundle options;

    ActivityResultOnSubscribe(
        ActivityService activityService, Intent intent, @Nullable Bundle options) {
      this.activityService = activityService;
      this.intent = intent;
      this.requestCode = REQUEST_CODE++; // TODO think of a better solution
      this.options = options;
    }

    @Override
    public void subscribe(SingleEmitter<ActivityResult> emitter) {
      ActivityResultListener listener =
          (requestCode, resultCode, data) -> {
            if (requestCode == this.requestCode) {
              emitter.onSuccess(new ActivityResult(resultCode, data));
            }
          };

      activityService.activityResultListeners.add(listener);
      activityService.activity.startActivityForResult(intent, requestCode, options);

      emitter.setCancellable(() -> activityService.activityResultListeners.remove(listener));
    }
  }
}
