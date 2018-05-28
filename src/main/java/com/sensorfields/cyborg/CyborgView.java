package com.sensorfields.cyborg;

import io.reactivex.Observable;

public interface CyborgView<M, E> {

  void render(M model);

  Observable<E> events();
}
