package com.jakewharton.rxbinding3.androidx.appcompat.widget;

import static com.jakewharton.rxbinding3.internal.Preconditions.checkMainThread;

import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class ToolbarItemClickObservable extends Observable<MenuItem> {
  private final Toolbar view;

  ToolbarItemClickObservable(Toolbar view) {
    this.view = view;
  }

  @Override protected void subscribeActual(Observer<? super MenuItem> observer) {
    if (!checkMainThread(observer)) {
      return;
    }
    Listener listener = new Listener(view, observer);
    observer.onSubscribe(listener);
    view.setOnMenuItemClickListener(listener);
  }

  static final class Listener extends MainThreadDisposable implements OnMenuItemClickListener {
    private final Toolbar toolbar;
    private final Observer<? super MenuItem> observer;

    Listener(Toolbar toolbar, Observer<? super MenuItem> observer) {
      this.toolbar = toolbar;
      this.observer = observer;
    }

    @Override public boolean onMenuItemClick(MenuItem item) {
      if (!isDisposed()) {
        observer.onNext(item);
      }
      return true;
    }

    @Override protected void onDispose() {
      toolbar.setOnMenuItemClickListener(null);
    }
  }
}
