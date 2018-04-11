package com.sensorfields.grekster.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sensorfields.grekster.android.grek.list.GrekListFragment;

public final class Activity extends AppCompatActivity {

  private static final int CONTAINER_VIEW_ID = android.R.id.content;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getSupportFragmentManager().findFragmentById(CONTAINER_VIEW_ID) == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(CONTAINER_VIEW_ID, GrekListFragment.create())
          .commit();
    }
  }
}
