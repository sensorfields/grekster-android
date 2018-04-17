package com.sensorfields.grekster.android;

import android.content.Context;
import android.os.StrictMode;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import timber.log.Timber;

public final class Application extends android.app.Application {

  @Override
  public void onCreate() {
    setupStrictMode();
    super.onCreate();
    setupTimber();
    setupDi();
  }

  // StrictMode

  private void setupStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.enableDefaults();
    }
  }

  // Timber

  private void setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  // DI

  private Component component;

  private void setupDi() {
    component = new Component();
  }

  public static Component component(Context context) {
    return ((Application) context.getApplicationContext()).component;
  }

  public final class Component {

    private FragmentManagerProvider fragmentManagerProvider;

    public FragmentManagerProvider fragmentManagerProvider() {
      if (fragmentManagerProvider == null) {
        fragmentManagerProvider = new FragmentManagerProvider();
      }
      return fragmentManagerProvider;
    }
  }
}
