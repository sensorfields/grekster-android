package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.First.first;
import static com.spotify.mobius.Next.next;
import static com.spotify.mobius.Next.noChange;
import static com.spotify.mobius.rx2.RxConnectables.fromTransformer;

import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.sensorfields.grekster.android.grek.list.Effect.LoadGreks;
import com.sensorfields.grekster.android.grek.list.Effect.ShowGrekDetails;
import com.spotify.mobius.First;
import com.spotify.mobius.MobiusLoop;
import com.spotify.mobius.MobiusLoop.Controller;
import com.spotify.mobius.Next;
import com.spotify.mobius.android.AndroidLogger;
import com.spotify.mobius.android.MobiusAndroid;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import timber.log.Timber;

public final class GrekListViewModel extends ViewModel {

  private final MobiusLoop.Factory<Model, Event, Effect> factory =
      RxMobius.loop(GrekListViewModel::update, EFFECT_HANDLER)
          .init(GrekListViewModel::init)
          .logger(AndroidLogger.tag("Grek"));
  private final Controller<Model, Event> controller =
      MobiusAndroid.controller(factory, Model.initial());

  public GrekListViewModel() {}

  void start(ObservableTransformer<Model, Event> view) {
    controller.connect(fromTransformer(view));
    controller.start();
  }

  void stop() {
    controller.stop();
    controller.disconnect();
  }

  static First<Model, Effect> init(Model model) {
    return first(model.toBuilder().activity(true).build(), effects(loadGreks()));
  }

  @NonNull
  static Next<Model, Effect> update(Model model, Event event) {
    return event.map(
        screenLoaded -> next(model.toBuilder().activity(true).build(), effects(loadGreks())),
        swipeRefreshTriggered -> noChange(),
        grekClicked -> noChange(),
        greksLoaded -> noChange(),
        greksLoadingFailed -> noChange());
  }

  private static Observable<Event> loadGreksHandler(Observable<LoadGreks> effects) {
    return effects.flatMap(
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

  private static Observable<Event> showGrekDetailsHandler(Observable<ShowGrekDetails> effects) {
    return effects.flatMap(
        effect -> {
          Timber.d("Nagivate to %s", effect.grek());
          return Observable.empty();
        });
  }

  private static ObservableTransformer<Effect, Event> EFFECT_HANDLER =
      RxMobius.<Effect, Event>subtypeEffectHandler()
          .add(LoadGreks.class, GrekListViewModel::loadGreksHandler)
          .add(ShowGrekDetails.class, GrekListViewModel::showGrekDetailsHandler)
          .build();

  private static final ImmutableList<String> GREKS =
      ImmutableList.<String>builder()
          .add("Cool grek here")
          .add("#GREK")
          .add("#GREK is way beyond yo")
          .add("#grek #audiobooks #teacher")
          .add("stop it plz")
          .build();
}
