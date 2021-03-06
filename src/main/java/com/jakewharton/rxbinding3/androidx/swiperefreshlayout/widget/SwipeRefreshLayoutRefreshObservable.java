package com.jakewharton.rxbinding3.androidx.swiperefreshlayout.widget;

import static com.jakewharton.rxbinding3.internal.Preconditions.checkMainThread;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.jakewharton.rxbinding2.internal.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class SwipeRefreshLayoutRefreshObservable extends Observable<Object> {
  private final SwipeRefreshLayout view;

  SwipeRefreshLayoutRefreshObservable(SwipeRefreshLayout view) {
    this.view = view;
  }

  @Override protected void subscribeActual(Observer<? super Object> observer) {
    if (!checkMainThread(observer)) {
      return;
    }
    Listener listener = new Listener(view, observer);
    observer.onSubscribe(listener);
    view.setOnRefreshListener(listener);
  }

  static final class Listener extends MainThreadDisposable implements OnRefreshListener {
    private final SwipeRefreshLayout view;
    private final Observer<? super Object> observer;

    Listener(SwipeRefreshLayout view, Observer<? super Object> observer) {
      this.view = view;
      this.observer = observer;
    }

    @Override public void onRefresh() {
      if (!isDisposed()) {
        observer.onNext(Notification.INSTANCE);
      }
    }

    @Override protected void onDispose() {
      view.setOnRefreshListener(null);
    }
  }
}
