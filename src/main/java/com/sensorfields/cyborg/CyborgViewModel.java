package com.sensorfields.cyborg;

import androidx.lifecycle.ViewModel;
import com.spotify.mobius.MobiusLoop;
import io.reactivex.disposables.Disposable;

public abstract class CyborgViewModel<M, E, F> extends ViewModel {

  private final MobiusLoop<M, E, F> loop;

  protected CyborgViewModel(MobiusLoop<M, E, F> loop) {
    this.loop = loop;
  }

  public Disposable connect(CyborgView<M, E> view) {
    return new ViewModelConnection(loop, view);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    loop.dispose();
  }
}
