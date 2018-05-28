package com.sensorfields.grekster.android.grek.list.handler;

import com.sensorfields.grekster.android.grek.create.GrekCreateFragment;
import com.sensorfields.grekster.android.grek.list.Effect.ShowGrekCreate;
import com.sensorfields.grekster.android.grek.list.Event;
import com.sensorfields.grekster.android.utils.FragmentManagerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public class ShowGrekCreateHandler implements ObservableTransformer<ShowGrekCreate, Event> {

  private final FragmentManagerProvider fragmentManagerProvider;

  @Inject
  ShowGrekCreateHandler(FragmentManagerProvider fragmentManagerProvider) {
    this.fragmentManagerProvider = fragmentManagerProvider;
  }

  @Override
  public ObservableSource<Event> apply(Observable<ShowGrekCreate> upstream) {
    return upstream.flatMap(
        effect -> {
          fragmentManagerProvider
              .fragmentManager()
              .beginTransaction()
              .replace(fragmentManagerProvider.containerViewId(), GrekCreateFragment.create())
              .addToBackStack(null)
              .commit();
          return Observable.empty();
        });
  }
}
