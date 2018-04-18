package com.sensorfields.grekster.android.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Grek {

  public abstract String id();

  public abstract String description();

  public static Grek create(String id, String description) {
    return new AutoValue_Grek(id, description);
  }
}
