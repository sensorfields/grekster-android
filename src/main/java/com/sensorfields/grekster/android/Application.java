package com.sensorfields.grekster.android;

import android.os.StrictMode;
import timber.log.Timber;

public final class Application extends android.app.Application {

  @Override
  public void onCreate() {
    setupStrictMode();
    super.onCreate();
    setupTimber();
  }

  private void setupStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.enableDefaults();
    }
  }

  private void setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
