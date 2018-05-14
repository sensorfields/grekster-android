package com.jakewharton.rxbinding3.androidx.appcompat.widget;

import androidx.appcompat.widget.Toolbar;
import io.reactivex.Observable;

public final class RxToolbar {

  public static Observable<Object> navigationClicks(Toolbar view) {
    return Observable.never();
  }
}
