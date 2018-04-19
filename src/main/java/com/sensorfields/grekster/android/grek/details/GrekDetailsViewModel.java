package com.sensorfields.grekster.android.grek.details;

import static com.spotify.mobius.First.first;
import static com.spotify.mobius.Next.noChange;
import static com.spotify.mobius.rx2.RxConnectables.fromTransformer;

import android.arch.lifecycle.ViewModel;
import com.sensorfields.grekster.android.utils.CyborgView;
import com.sensorfields.grekster.android.utils.CyborgViewModel;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.First;
import com.spotify.mobius.MobiusLoop.Controller;
import com.spotify.mobius.MobiusLoop.Factory;
import com.spotify.mobius.Next;
import com.spotify.mobius.android.MobiusAndroid;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public final class GrekDetailsViewModel extends ViewModel implements CyborgViewModel<Model, Event> {

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

  @Override
  public void connect(CyborgView<Model, Event> view) {
    controller.connect(
        fromTransformer(
            upstream -> {
              Disposable disposable = upstream.subscribe(view::render);
              return view.events().doOnDispose(disposable::dispose);
            }));
    controller.start();
  }

  @Override
  public void disconnect() {
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
