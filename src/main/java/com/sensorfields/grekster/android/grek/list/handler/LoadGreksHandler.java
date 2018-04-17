package com.sensorfields.grekster.android.grek.list.handler;

import android.os.SystemClock;
import com.google.common.collect.ImmutableList;
import com.sensorfields.grekster.android.grek.list.Effect.LoadGreks;
import com.sensorfields.grekster.android.grek.list.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import javax.inject.Inject;

public final class LoadGreksHandler implements ObservableTransformer<LoadGreks, Event> {

  private static final ImmutableList<String> GREKS =
      ImmutableList.<String>builder()
          .add("Cool grek here")
          .add("#GREK")
          .add("#GREK is way beyond yo")
          .add("#grek #audiobooks #teacher")
          .add("stop it plz")
          .build();

  @Inject
  LoadGreksHandler() {}

  @Override
  public ObservableSource<Event> apply(Observable<LoadGreks> upstream) {
    return upstream.flatMap(
        effect ->
            Single.fromCallable(
                    () -> {
                      SystemClock.sleep(700);
                      return GREKS;
                    })
                .toObservable()
                .map(Event::greksLoaded)
                .onErrorReturn(Event::greksLoadingFailed));
  }
}
