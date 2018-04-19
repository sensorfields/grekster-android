package com.sensorfields.grekster.android.grek.details;

import static com.spotify.mobius.First.first;
import static com.spotify.mobius.Next.noChange;
import static com.spotify.mobius.rx2.RxConnectables.fromTransformer;

import android.arch.lifecycle.ViewModel;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.First;
import com.spotify.mobius.MobiusLoop.Controller;
import com.spotify.mobius.MobiusLoop.Factory;
import com.spotify.mobius.Next;
import com.spotify.mobius.android.MobiusAndroid;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

public final class GrekDetailsViewModel extends ViewModel {

  private final Controller<Model, Event> controller;

  @Inject
  GrekDetailsViewModel(LoggerFactory loggerFactory) {
    Factory<Model, Event, Effect> factory =
        RxMobius.loop(
                GrekDetailsViewModel::update,
                RxMobius.<Effect, Event>subtypeEffectHandler().build())
            .init(GrekDetailsViewModel::init)
            .logger(loggerFactory.create(GrekDetailsViewModel.class));
    controller = MobiusAndroid.controller(factory, Model.initial());
  }

  void start(ObservableTransformer<Model, Event> view) {
    controller.connect(fromTransformer(view));
    controller.start();
  }

  void stop() {
    controller.stop();
    controller.disconnect();
  }

  static First<Model, Effect> init(Model model) {
    return first(model);
  }

  static Next<Model, Effect> update(Model model, Event event) {
    return noChange();
  }
}
