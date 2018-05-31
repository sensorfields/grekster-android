package com.sensorfields.grekster.android;

import static com.sensorfields.grekster.android.Application.component;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.grekster.android.grek.list.GrekListFragment;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;

public final class Activity extends AppCompatActivity {

  private ActivityService activityService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityService = component(this).activityService();
    activityService.onCreate(this);
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

  @Override
  protected void onDestroy() {
    activityService.onDestroy(this);
    super.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    activityService.onActivityResult(requestCode, resultCode, data);
  }
}
