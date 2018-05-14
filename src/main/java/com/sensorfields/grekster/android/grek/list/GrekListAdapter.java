package com.sensorfields.grekster.android.grek.list;

import static com.jakewharton.rxbinding2.view.RxView.clicks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.common.collect.ImmutableList;
import com.sensorfields.grekster.android.grek.list.GrekListAdapter.GrekViewHolder;
import com.sensorfields.grekster.android.model.Grek;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

final class GrekListAdapter extends Adapter<GrekViewHolder> {

  private final Subject<Grek> itemClicksSubject = PublishSubject.create();

  private ImmutableList<Grek> greks = ImmutableList.of();

  public void setGreks(@Nullable ImmutableList<Grek> greks) {
    this.greks = greks == null ? ImmutableList.of() : greks;
    notifyDataSetChanged();
  }

  Observable<Grek> itemClicks() {
    return itemClicksSubject;
  }

  @NonNull
  @Override
  public GrekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new GrekViewHolder(
        LayoutInflater.from(parent.getContext())
            .inflate(android.R.layout.simple_selectable_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull GrekViewHolder holder, int position) {
    holder.descriptionView.setText(greks.get(position).description());
    clicks(holder.itemView)
        .map(ignored -> greks.get(holder.getAdapterPosition()))
        .subscribe(itemClicksSubject);
  }

  @Override
  public int getItemCount() {
    return greks.size();
  }

  static final class GrekViewHolder extends ViewHolder {

    private final TextView descriptionView;

    GrekViewHolder(View itemView) {
      super(itemView);
      descriptionView = itemView.findViewById(android.R.id.text1);
    }
  }
}
