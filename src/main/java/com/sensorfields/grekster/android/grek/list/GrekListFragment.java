package com.sensorfields.grekster.android.grek.list;

import static com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout.refreshes;
import static com.sensorfields.grekster.android.Application.viewModelFactory;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sensorfields.grekster.android.R;
import com.sensorfields.grekster.android.utils.CyborgView;
import io.reactivex.Observable;

public final class GrekListFragment extends Fragment implements CyborgView<Model, Event> {

  public static GrekListFragment create() {
    return new GrekListFragment();
  }

  private GrekListViewModel viewModel;

  private SwipeRefreshLayout swipeRefreshView;
  private GrekListAdapter listAdapter;

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
        listAdapter.itemClicks().map(Event::grekClicked));
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

    viewModel =
        ViewModelProviders.of(this, viewModelFactory(getContext())).get(GrekListViewModel.class);
    viewModel.connect(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    viewModel.disconnect();
    super.onDestroyView();
  }
}
