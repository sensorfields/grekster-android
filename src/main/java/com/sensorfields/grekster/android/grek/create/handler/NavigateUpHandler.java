package com.sensorfields.grekster.android.grek.create.handler;

import com.sensorfields.grekster.android.grek.create.Effect.NavigateUp;
import com.sensorfields.grekster.android.grek.create.Event;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public final class NavigateUpHandler implements ObservableTransformer<NavigateUp, Event> {

  private final FragmentManagerProvider fragmentManagerProvider;

  @Inject
  NavigateUpHandler(FragmentManagerProvider fragmentManagerProvider) {
    this.fragmentManagerProvider = fragmentManagerProvider;
  }

  @Override
  public ObservableSource<Event> apply(Observable<NavigateUp> upstream) {
    return upstream.flatMap(
        effect -> {
          fragmentManagerProvider.fragmentManager().popBackStack();
          return Observable.empty();
        });
  }
}
