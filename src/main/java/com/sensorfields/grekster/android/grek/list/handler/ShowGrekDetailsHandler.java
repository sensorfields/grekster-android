package com.sensorfields.grekster.android.grek.list.handler;

import com.sensorfields.grekster.android.grek.details.GrekDetailsFragment;
import com.sensorfields.grekster.android.grek.list.Effect.ShowGrekDetails;
import com.sensorfields.grekster.android.grek.list.Event;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ShowGrekDetailsHandler implements ObservableTransformer<ShowGrekDetails, Event> {

  private final FragmentManagerProvider fragmentManagerProvider;

  @Inject
  ShowGrekDetailsHandler(FragmentManagerProvider fragmentManagerProvider) {
    this.fragmentManagerProvider = fragmentManagerProvider;
  }

  @Override
  public ObservableSource<Event> apply(Observable<ShowGrekDetails> upstream) {
    return upstream.flatMap(
        effect -> {
          fragmentManagerProvider
              .fragmentManager()
              .beginTransaction()
              .replace(
                  fragmentManagerProvider.containerViewId(),
                  GrekDetailsFragment.create(effect.grek()))
              .addToBackStack(null)
              .commit();
          return Observable.empty();
        });
  }
}
