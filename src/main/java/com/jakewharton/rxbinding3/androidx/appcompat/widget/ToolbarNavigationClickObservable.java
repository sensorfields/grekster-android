package com.jakewharton.rxbinding3.androidx.appcompat.widget;

import static com.jakewharton.rxbinding3.internal.Preconditions.checkMainThread;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import com.jakewharton.rxbinding2.internal.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class ToolbarNavigationClickObservable extends Observable<Object> {
  private final Toolbar view;

  ToolbarNavigationClickObservable(Toolbar view) {
    this.view = view;
  }

  @Override protected void subscribeActual(Observer<? super Object> observer) {
    if (!checkMainThread(observer)) {
      return;
    }
    Listener listener = new Listener(view, observer);
    observer.onSubscribe(listener);
    view.setNavigationOnClickListener(listener);
  }

  static final class Listener extends MainThreadDisposable implements View.OnClickListener {
    private final Toolbar toolbar;
    private final Observer<? super Object> observer;

    Listener(Toolbar toolbar, Observer<? super Object> observer) {
      this.toolbar = toolbar;
      this.observer = observer;
    }

    @Override public void onClick(View view) {
      if (!isDisposed()) {
        observer.onNext(Notification.INSTANCE);
      }
    }

    @Override protected void onDispose() {
      toolbar.setNavigationOnClickListener(null);
    }
  }
}
