package com.sensorfields.grekster.android.utils;

import io.reactivex.Observable;

public interface CyborgView<M, E> {

  void render(M model);

  Observable<E> events();
}
