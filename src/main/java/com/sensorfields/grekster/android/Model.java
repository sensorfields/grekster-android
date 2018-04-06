package com.sensorfields.grekster.android;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Model {

  public abstract int counter();

  public static Model initial() {
    return create(3);
  }

  public Model increase() {
    return create(counter() + 1);
  }

  public Model decrease() {
    return create(counter() - 1);
  }

  private static Model create(int counter) {
    return new AutoValue_Model(counter);
  }
}
