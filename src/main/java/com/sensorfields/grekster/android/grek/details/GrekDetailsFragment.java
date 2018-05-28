package com.sensorfields.grekster.android.grek.details;

import static com.jakewharton.rxbinding3.androidx.appcompat.widget.RxToolbar.navigationClicks;
import static com.sensorfields.grekster.android.Application.viewModelFactory;
import static com.sensorfields.grekster.android.grek.details.Event.upClicked;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.sensorfields.cyborg.CyborgView;
import com.sensorfields.grekster.android.R;
import com.sensorfields.grekster.android.model.Grek;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public final class GrekDetailsFragment extends Fragment implements CyborgView<Model, Event> {

  public static GrekDetailsFragment create(Grek grek) {
    return new GrekDetailsFragment();
  }

  private Toolbar toolbarView;
  private TextView descriptionView;

  private Disposable disposable;

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

    disposable =
        ViewModelProviders.of(this, viewModelFactory(getContext()))
            .get(GrekDetailsViewModel.class)
            .connect(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    disposable.dispose();
    super.onDestroyView();
  }
}
