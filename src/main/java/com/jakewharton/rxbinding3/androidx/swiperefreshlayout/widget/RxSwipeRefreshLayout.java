package com.jakewharton.rxbinding3.androidx.swiperefreshlayout.widget;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observable;

public final class RxSwipeRefreshLayout {

  public static Observable<Object> refreshes(SwipeRefreshLayout view) {
    return Observable.never();
  }
}
