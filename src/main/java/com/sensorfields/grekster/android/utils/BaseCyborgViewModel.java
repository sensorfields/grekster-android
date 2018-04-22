package com.sensorfields.grekster.android.utils;

import static com.spotify.mobius.rx2.RxConnectables.fromTransformer;

import android.arch.lifecycle.ViewModel;
import com.spotify.mobius.Init;
import com.spotify.mobius.MobiusLoop.Controller;
import com.spotify.mobius.MobiusLoop.Factory;
import com.spotify.mobius.Update;
import com.spotify.mobius.android.MobiusAndroid;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

public abstract class BaseCyborgViewModel<M, E, F> extends ViewModel
    implements CyborgViewModel<M, E> {

  private final Controller<M, E> controller;

  protected BaseCyborgViewModel(
      Update<M, E, F> update,
      Init<M, F> init,
      ObservableTransformer<F, E> effectHandler,
      M defaultModel,
      LoggerFactory loggerFactory) {
    Factory<M, E, F> factory =
        RxMobius.loop(update, effectHandler).init(init).logger(loggerFactory.create(getClass()));
    controller = MobiusAndroid.controller(factory, defaultModel);
  }

  @Override
  public void connect(CyborgView<M, E> view) {
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
}
