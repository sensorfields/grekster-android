package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.sensorfields.grekster.android.grek.list.Effect.showGrekCreate;
import static com.sensorfields.grekster.android.grek.list.Effect.showGrekDetails;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.First.first;
import static com.spotify.mobius.Next.dispatch;
import static com.spotify.mobius.Next.next;
import static com.spotify.mobius.Next.noChange;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import com.sensorfields.cyborg.CyborgViewModel;
import com.sensorfields.grekster.android.grek.list.Effect.LoadGreks;
import com.sensorfields.grekster.android.grek.list.Effect.ShowGrekCreate;
import com.sensorfields.grekster.android.grek.list.Effect.ShowGrekDetails;
import com.sensorfields.grekster.android.grek.list.handler.LoadGreksHandler;
import com.sensorfields.grekster.android.grek.list.handler.ShowGrekCreateHandler;
import com.sensorfields.grekster.android.grek.list.handler.ShowGrekDetailsHandler;
import com.sensorfields.grekster.android.model.Grek;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.First;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import javax.inject.Inject;

public final class GrekListViewModel extends CyborgViewModel<Model, Event, Effect> {

  @Inject
  GrekListViewModel(
      LoggerFactory loggerFactory,
      LoadGreksHandler loadGreksHandler,
      ShowGrekDetailsHandler showGrekDetailsHandler,
      ShowGrekCreateHandler showGrekCreateHandler) {
    super(
        loggerFactory,
        GrekListViewModel::update,
        RxMobius.<Effect, Event>subtypeEffectHandler()
            .add(LoadGreks.class, loadGreksHandler)
            .add(ShowGrekDetails.class, showGrekDetailsHandler)
            .add(ShowGrekCreate.class, showGrekCreateHandler)
            .build(),
        Model.initial());
  }

  static First<Model, Effect> init(Model model) {
    if (model.equals(Model.initial())) {
      return first(model.toBuilder().activity(true).build(), effects(loadGreks()));
    } else {
      return first(model);
    }
  }

  @NonNull
  static Next<Model, Effect> update(Model model, Event event) {
    return event.map(
        greksLoaded -> next(model.toBuilder().activity(false).greks(greksLoaded.greks()).build()),
        greksLoadingFailed ->
            next(model.toBuilder().activity(false).error(greksLoadingFailed.error()).build()),
        swipeRefreshTriggered ->
            next(model.toBuilder().activity(true).build(), effects(loadGreks())),
        grekClicked -> {
          Grek grek = grekClicked.grek();
          ImmutableList<Grek> greks = model.greks();
          if (greks != null && greks.contains(grek)) {
            return dispatch(effects(showGrekDetails(grek)));
          }
          return noChange();
        },
        createButtonClicked -> dispatch(effects(showGrekCreate())));
  }
}
