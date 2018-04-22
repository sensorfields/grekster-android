package com.sensorfields.grekster.android.grek.details;

import static com.spotify.mobius.First.first;
import static com.spotify.mobius.Next.noChange;

import com.sensorfields.grekster.android.utils.BaseCyborgViewModel;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.First;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import javax.inject.Inject;

public final class GrekDetailsViewModel extends BaseCyborgViewModel<Model, Event, Effect> {

  @Inject
  GrekDetailsViewModel(LoggerFactory loggerFactory) {
    super(
        GrekDetailsViewModel::update,
        GrekDetailsViewModel::init,
        RxMobius.<Effect, Event>subtypeEffectHandler().build(),
        Model.initial(),
        loggerFactory);
  }

  static First<Model, Effect> init(Model model) {
    return first(model);
  }

  static Next<Model, Effect> update(Model model, Event event) {
    return noChange();
  }
}
