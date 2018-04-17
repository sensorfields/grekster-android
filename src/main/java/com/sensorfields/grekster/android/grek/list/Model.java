package com.sensorfields.grekster.android.grek.list;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
abstract class Model {

  abstract boolean activity();

  @Nullable
  abstract ImmutableList<String> greks();

  @Nullable
  abstract Throwable error();

  abstract Builder toBuilder();

  public static Model initial() {
    return new AutoValue_Model.Builder().activity(false).build();
  }

  @AutoValue.Builder
  abstract static class Builder {

    public abstract Builder activity(boolean activity);

    public abstract Builder greks(ImmutableList<String> greks);

    public abstract Builder error(Throwable error);

    public abstract Model build();
  }
}