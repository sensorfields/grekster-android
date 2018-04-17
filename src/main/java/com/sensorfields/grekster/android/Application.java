package com.sensorfields.grekster.android;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.content.Context;
import android.os.StrictMode;
import com.sensorfields.grekster.android.grek.list.GrekListViewModel;
import com.sensorfields.grekster.android.utils.DaggerViewModelFactory;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import dagger.Binds;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
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

  public static Factory viewModelFactory(Context context) {
    return component(context).viewModelFactory();
  }

  @Singleton
  @dagger.Component(modules = Module.class)
  public interface Component {

    FragmentManagerProvider fragmentManagerProvider();

    Factory viewModelFactory();
  }

  @SuppressWarnings("unused")
  @dagger.Module
  abstract static class Module {

    @Binds
    abstract Factory viewModelFactory(DaggerViewModelFactory daggerViewModelFactory);

    @Binds
    @IntoMap
    @ClassKey(GrekListViewModel.class)
    abstract ViewModel grekListViewModel(GrekListViewModel viewModel);
  }
}
