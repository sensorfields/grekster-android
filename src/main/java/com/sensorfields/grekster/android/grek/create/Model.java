package com.sensorfields.grekster.android.grek.create;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.io.File;

@AutoValue
abstract class Model {

  @Nullable
  abstract File photo();

  abstract Builder toBuilder();

  static Model initial() {
    return new AutoValue_Model.Builder().build();
  }

  @AutoValue.Builder
  interface Builder {

    Builder photo(@Nullable File photo);

    Model build();
  }
}
