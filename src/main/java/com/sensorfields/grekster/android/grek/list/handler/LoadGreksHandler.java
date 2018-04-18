package com.sensorfields.grekster.android.grek.list.handler;

import com.sensorfields.grekster.android.grek.list.Effect.LoadGreks;
import com.sensorfields.grekster.android.grek.list.Event;
import com.sensorfields.grekster.android.model.GrekRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public final class LoadGreksHandler implements ObservableTransformer<LoadGreks, Event> {

  private final GrekRepository grekRepository;

  @Inject
  LoadGreksHandler(GrekRepository grekRepository) {
    this.grekRepository = grekRepository;
  }

  @Override
  public ObservableSource<Event> apply(Observable<LoadGreks> upstream) {
    return upstream.flatMap(
        effect ->
            grekRepository
                .find()
                .map(Event::greksLoaded)
                .onErrorReturn(Event::greksLoadingFailed)
                .toObservable());
  }
}
