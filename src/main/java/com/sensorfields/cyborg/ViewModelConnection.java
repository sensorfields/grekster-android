package com.sensorfields.cyborg;

import com.spotify.mobius.MobiusLoop;
import io.reactivex.disposables.Disposable;

final class ViewModelConnection implements Disposable {

  private final com.spotify.mobius.disposables.Disposable modelDisposable;
  private final Disposable eventDisposable;

  <M, E, F> ViewModelConnection(MobiusLoop<M, E, F> loop, CyborgView<M, E> view) {
    modelDisposable = loop.observe(view::render);
    eventDisposable = view.events().subscribe(loop::dispatchEvent);
  }

  @Override
  public void dispose() {
    modelDisposable.dispose();
    eventDisposable.dispose();
  }

  @Override
  public boolean isDisposed() {
    return eventDisposable.isDisposed();
  }
}
