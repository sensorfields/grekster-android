package com.sensorfields.grekster.android.grek.create;

import static com.sensorfields.grekster.android.grek.create.Effect.navigateUp;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.Next.dispatch;

import android.support.annotation.NonNull;
import com.sensorfields.cyborg.CyborgViewModel;
import com.sensorfields.grekster.android.grek.create.Effect.NavigateUp;
import com.sensorfields.grekster.android.grek.create.handler.NavigateUpHandler;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import javax.inject.Inject;

public final class GrekCreateViewModel extends CyborgViewModel<Model, Event, Effect> {

  @Inject
  GrekCreateViewModel(NavigateUpHandler navigateUpHandler) {
    super(
        RxMobius.loop(
                GrekCreateViewModel::update,
                RxMobius.<Effect, Event>subtypeEffectHandler()
                    .add(NavigateUp.class, navigateUpHandler)
                    .build())
            .startFrom(Model.initial()));
  }

  @NonNull
  static Next<Model, Effect> update(Model model, Event event) {
    return event.map(upButtonClicked -> dispatch(effects(navigateUp())));
  }
}
