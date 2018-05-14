package com.sensorfields.grekster.android.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

@Singleton
public final class FragmentManagerProvider {

  @Nullable private FragmentManager fragmentManager;

  @Inject
  FragmentManagerProvider() {}

  public void fragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  public FragmentManager fragmentManager() {
    if (fragmentManager == null) {
      throw new IllegalStateException("FragmentManager is not set");
    }
    return fragmentManager;
  }

  public int containerViewId() {
    return android.R.id.content;
  }
}
