package com.sensorfields.grekster.android.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

public final class FragmentManagerProvider {

  @Nullable private FragmentManager fragmentManager;

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
