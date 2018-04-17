package com.sensorfields.grekster.android;

import android.content.Context;
import android.os.StrictMode;
import com.sensorfields.grekster.android.grek.list.handler.ShowGrekDetailsHandler;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import javax.inject.Singleton;
import timber.log.Timber;

public final class Application extends android.app.Application {

  @Override
  public void onCreate() {
    setupStrictMode();
    super.onCreate();
    setupTimber();
    setupDagger();
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

  // Dagger

  private Component component;

  private void setupDagger() {
    component = DaggerApplication_Component.create();
  }

  public static Component component(Context context) {
    return ((Application) context.getApplicationContext()).component;
  }

  @Singleton
  @dagger.Component
  public interface Component {

    FragmentManagerProvider fragmentManagerProvider();

    ShowGrekDetailsHandler showGrekDetailsHandler();
  }
}
