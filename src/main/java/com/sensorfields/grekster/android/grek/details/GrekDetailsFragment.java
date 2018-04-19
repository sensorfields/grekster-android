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
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public final class GrekDetailsFragment extends Fragment {

  public static GrekDetailsFragment create(Grek grek) {
    return new GrekDetailsFragment();
  }

  private GrekDetailsViewModel viewModel;

  private Toolbar toolbarView;
  private TextView descriptionView;

  private Observable<Event> connectViews(Observable<Model> models) {
    Disposable disposable =
        models.subscribe(
            model -> {
              Timber.e("MODEL: %s", model);
            });

    //noinspection unchecked
    return Observable.mergeArray(navigationClicks(toolbarView).map(ignored -> upClicked()))
        .doOnDispose(disposable::dispose);
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
    viewModel.start(this::connectViews);

    return view;
  }

  @Override
  public void onDestroyView() {
    viewModel.stop();
    super.onDestroyView();
  }
}
