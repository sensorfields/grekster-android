package com.sensorfields.grekster.android.grek.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.sensorfields.grekster.android.R;

public final class GrekCreateFragment extends Fragment {

  public static GrekCreateFragment create() {
    return new GrekCreateFragment();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.grek_create_fragment, container, false);
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.inflateMenu(R.menu.grek_create_toolbar);
    return view;
  }
}
