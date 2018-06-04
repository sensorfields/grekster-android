package com.sensorfields.cyborg;

import androidx.lifecycle.ViewModel;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.MobiusLoop;
import com.spotify.mobius.Update;
import com.spotify.mobius.rx2.RxMobius;
import com.spotify.mobius.rx2.SchedulerWorkRunner;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public abstract class CyborgViewModel<M, E, F> extends ViewModel {

  private final MobiusLoop<M, E, F> loop;

  protected CyborgViewModel(
      LoggerFactory loggerFactory,
      Update<M, E, F> update,
      ObservableTransformer<F, E> effectHandler,
      M startModel) {
    loop =
        configure(
                RxMobius.loop(update, effectHandler)
                    .logger(loggerFactory.create(getClass()))
                    .eventRunner(() -> new SchedulerWorkRunner(AndroidSchedulers.mainThread()))
                    .effectRunner(() -> new SchedulerWorkRunner(AndroidSchedulers.mainThread())))
            .startFrom(startModel);
  }

  protected MobiusLoop.Builder<M, E, F> configure(MobiusLoop.Builder<M, E, F> builder) {
    return builder;
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
