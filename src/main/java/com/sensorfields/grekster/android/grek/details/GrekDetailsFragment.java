package com.sensorfields.grekster.android.grek.details;

import android.support.v4.app.Fragment;
import com.sensorfields.grekster.android.model.Grek;

public final class GrekDetailsFragment extends Fragment {

  public static GrekDetailsFragment create(Grek grek) {
    return new GrekDetailsFragment();
  }
}
