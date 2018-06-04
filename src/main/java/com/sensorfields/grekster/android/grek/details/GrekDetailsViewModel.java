package com.sensorfields.grekster.android.grek.details;

import static com.spotify.mobius.Next.noChange;

import com.sensorfields.cyborg.CyborgViewModel;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import javax.inject.Inject;

public final class GrekDetailsViewModel extends CyborgViewModel<Model, Event, Effect> {

  @Inject
  GrekDetailsViewModel(LoggerFactory loggerFactory) {
    super(
        loggerFactory,
        GrekDetailsViewModel::update,
        RxMobius.<Effect, Event>subtypeEffectHandler().build(),
        Model.initial());
  }

  static Next<Model, Effect> update(Model model, Event event) {
    return noChange();
  }
}
