package com.sensorfields.grekster.android.grek.details;

import static com.jakewharton.rxbinding2.support.v7.widget.RxToolbar.navigationClicks;
import static com.sensorfields.grekster.android.Application.viewModelFactory;
import static com.sensorfields.grekster.android.grek.details.Event.upClicked;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sensorfields.grekster.android.R;
import com.sensorfields.grekster.android.model.Grek;
import com.sensorfields.grekster.android.utils.CyborgView;
import io.reactivex.Observable;
import timber.log.Timber;

public final class GrekDetailsFragment extends Fragment implements CyborgView<Model, Event> {

  public static GrekDetailsFragment create(Grek grek) {
    return new GrekDetailsFragment();
  }

  private GrekDetailsViewModel viewModel;

  private Toolbar toolbarView;
  private TextView descriptionView;

  @Override
  public void render(Model model) {
    Timber.e("MODEL: %s", model);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Observable<Event> events() {
    return Observable.mergeArray(navigationClicks(toolbarView).map(ignored -> upClicked()));
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.grek_details_fragment, container, false);
    toolbarView = view.findViewById(R.id.toolbar);
    descriptionView = view.findViewById(R.id.grekDetailsDescription);

    viewModel =
        ViewModelProviders.of(this, viewModelFactory(getContext())).get(GrekDetailsViewModel.class);
    viewModel.connect(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    viewModel.disconnect();
    super.onDestroyView();
  }
}
