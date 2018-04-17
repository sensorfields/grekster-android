package com.sensorfields.grekster.android;

import static com.sensorfields.grekster.android.Application.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.sensorfields.grekster.android.grek.list.GrekListFragment;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;

public final class Activity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FragmentManagerProvider fragmentManagerProvider = component(this).fragmentManagerProvider();
    fragmentManagerProvider.fragmentManager(getSupportFragmentManager());
    FragmentManager fragmentManager = fragmentManagerProvider.fragmentManager();
    if (fragmentManager.findFragmentById(fragmentManagerProvider.containerViewId()) == null) {
      fragmentManager
          .beginTransaction()
          .add(fragmentManagerProvider.containerViewId(), GrekListFragment.create())
          .commit();
    }
  }
}
