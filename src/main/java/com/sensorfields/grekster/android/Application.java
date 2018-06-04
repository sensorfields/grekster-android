package com.sensorfields.grekster.android;

import android.content.ContentResolver;
import android.content.Context;
import android.os.StrictMode;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.grekster.android.grek.create.GrekCreateViewModel;
import com.sensorfields.grekster.android.grek.details.GrekDetailsViewModel;
import com.sensorfields.grekster.android.grek.list.GrekListViewModel;
import com.sensorfields.grekster.android.utils.DaggerViewModelFactory;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
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
    component = DaggerApplication_Component.builder().context(this).build();
  }

  public static Component component(Context context) {
    return ((Application) context.getApplicationContext()).component;
  }

  public static Factory viewModelFactory(Context context) {
    return component(context).viewModelFactory();
  }

  @Singleton
  @dagger.Component(modules = {AndroidModule.class, ApplicationModule.class})
  public interface Component {

    FragmentManagerProvider fragmentManagerProvider();

    ActivityService activityService();

    Factory viewModelFactory();

    @dagger.Component.Builder
    interface Builder {

      @BindsInstance
      Builder context(Context context);

      Component build();
    }
  }

  @Module
  abstract static class AndroidModule {

    @Provides
    static ContentResolver contentResolver(Context context) {
      return context.getContentResolver();
    }

    @Provides
    static String fileProviderAuthority(Context context) {
      return context.getString(R.string.file_provider_authority);
    }
  }

  @SuppressWarnings("unused")
  @Module
  abstract static class ApplicationModule {

    @Binds
    abstract Factory viewModelFactory(DaggerViewModelFactory daggerViewModelFactory);

    @Binds
    @IntoMap
    @ClassKey(GrekCreateViewModel.class)
    abstract ViewModel grekCreateViewModel(GrekCreateViewModel viewModel);

    @Binds
    @IntoMap
    @ClassKey(GrekDetailsViewModel.class)
    abstract ViewModel grekDetailsViewModel(GrekDetailsViewModel viewModel);

    @Binds
    @IntoMap
    @ClassKey(GrekListViewModel.class)
    abstract ViewModel grekListViewModel(GrekListViewModel viewModel);
  }
}
