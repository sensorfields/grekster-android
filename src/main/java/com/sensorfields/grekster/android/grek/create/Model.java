package com.sensorfields.grekster.android.grek.create;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class Model {

  static Model initial() {
    return new AutoValue_Model();
  }
}
