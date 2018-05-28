package com.sensorfields.grekster.android.grek.list;

import static com.jakewharton.rxbinding2.view.RxView.clicks;
import static com.jakewharton.rxbinding3.androidx.swiperefreshlayout.widget.RxSwipeRefreshLayout.refreshes;
import static com.sensorfields.grekster.android.Application.viewModelFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.sensorfields.cyborg.CyborgView;
import com.sensorfields.grekster.android.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public final class GrekListFragment extends Fragment implements CyborgView<Model, Event> {

  public static GrekListFragment create() {
    return new GrekListFragment();
  }

  private SwipeRefreshLayout swipeRefreshView;
  private GrekListAdapter listAdapter;
  private View createButton;

  private Disposable disposable;

  @Override
  public void render(Model model) {
    swipeRefreshView.setRefreshing(model.activity());
    listAdapter.setGreks(model.greks());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Observable<Event> events() {
    return Observable.mergeArray(
        refreshes(swipeRefreshView).map(ignored -> Event.swipeRefreshTriggered()),
        listAdapter.itemClicks().map(Event::grekClicked),
        clicks(createButton).map(ignored -> Event.createButtonClicked()));
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.grek_list_fragment, container, false);
    swipeRefreshView = view.findViewById(R.id.grekListSwipeRefresh);
    RecyclerView listView = view.findViewById(R.id.grekList);
    listView.setLayoutManager(new LinearLayoutManager(getContext()));
    listView.setAdapter(listAdapter = new GrekListAdapter());
    createButton = view.findViewById(R.id.grekListCreateButton);

    disposable =
        ViewModelProviders.of(this, viewModelFactory(getContext()))
            .get(GrekListViewModel.class)
            .connect(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    disposable.dispose();
    super.onDestroyView();
  }
}
