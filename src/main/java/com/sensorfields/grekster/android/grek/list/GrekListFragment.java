package com.sensorfields.grekster.android.grek.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.common.collect.ImmutableList;
import com.sensorfields.grekster.android.R;
import io.reactivex.disposables.CompositeDisposable;

public final class GrekListFragment extends Fragment {

  public static GrekListFragment create() {
    return new GrekListFragment();
  }

  private CompositeDisposable disposables;

  private GrekListAdapter listAdapter;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.grek_list_fragment, container, false);
    RecyclerView listView = view.findViewById(R.id.grekList);
    listView.setLayoutManager(new LinearLayoutManager(getContext()));
    listView.setAdapter(listAdapter = new GrekListAdapter());
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    disposables =
        new CompositeDisposable(
            listAdapter
                .itemClicks()
                .subscribe(
                    grek ->
                        Snackbar.make(
                                view,
                                getString(R.string.grek_list_item_click_message, grek),
                                Snackbar.LENGTH_SHORT)
                            .show()));
    listAdapter.setGreks(GREKS);
  }

  @Override
  public void onDestroyView() {
    disposables.dispose();
    super.onDestroyView();
  }

  private static final ImmutableList<String> GREKS =
      ImmutableList.<String>builder()
          .add("Cool grek here")
          .add("#GREK")
          .add("#GREK is way beyond yo")
          .add("#grek #audiobooks #teacher")
          .add("stop it plz")
          .build();
}
