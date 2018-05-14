package com.sensorfields.grekster.android.utils;

import android.support.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public final class DaggerViewModelFactory implements Factory {

  private final Map<Class<?>, Provider<ViewModel>> viewModelProviders;

  @Inject
  public DaggerViewModelFactory(Map<Class<?>, Provider<ViewModel>> viewModelProviders) {
    this.viewModelProviders = viewModelProviders;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) viewModelProviders.get(modelClass).get();
  }
}
