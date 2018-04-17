package com.sensorfields.grekster.android.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.support.annotation.NonNull;
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
