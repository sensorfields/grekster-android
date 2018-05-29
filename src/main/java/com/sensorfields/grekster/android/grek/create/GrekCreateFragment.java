package com.sensorfields.grekster.android.grek.create;

import static com.jakewharton.rxbinding3.androidx.appcompat.widget.RxToolbar.navigationClicks;
import static com.sensorfields.grekster.android.Application.viewModelFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.sensorfields.cyborg.CyborgView;
import com.sensorfields.grekster.android.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public final class GrekCreateFragment extends Fragment implements CyborgView<Model, Event> {

  public static GrekCreateFragment create() {
    return new GrekCreateFragment();
  }

  private Toolbar toolbarView;

  private Disposable disposable;

  @Override
  public void render(Model model) {}

  @Override
  public Observable<Event> events() {
    return navigationClicks(toolbarView).map(ignored -> Event.upButtonClicked());
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.grek_create_fragment, container, false);
    toolbarView = view.findViewById(R.id.toolbar);
    toolbarView.inflateMenu(R.menu.grek_create_toolbar);

    disposable =
        ViewModelProviders.of(this, viewModelFactory(getContext()))
            .get(GrekCreateViewModel.class)
            .connect(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    disposable.dispose();
    super.onDestroyView();
  }
}
