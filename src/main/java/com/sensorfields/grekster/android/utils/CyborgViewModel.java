package com.sensorfields.grekster.android.utils;

public interface CyborgViewModel<M, E> {

  void connect(CyborgView<M, E> view);

  void disconnect();
}
