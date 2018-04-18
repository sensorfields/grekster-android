package com.sensorfields.grekster.android.model;

import android.os.SystemClock;
import com.google.common.collect.ImmutableList;
import io.reactivex.Single;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GrekRepository {

  @Inject
  GrekRepository() {}

  public final Single<ImmutableList<Grek>> find() {
    return Single.fromCallable(
        () -> {
          SystemClock.sleep(700);
          return GREKS;
        });
  }

  private static final ImmutableList<Grek> GREKS =
      ImmutableList.<Grek>builder()
          .add(Grek.create(UUID.randomUUID().toString(), "Cool grek here"))
          .add(Grek.create(UUID.randomUUID().toString(), "#GREK"))
          .add(Grek.create(UUID.randomUUID().toString(), "#GREK is way beyond yo"))
          .add(Grek.create(UUID.randomUUID().toString(), "#grek #audiobooks #teacher"))
          .add(Grek.create(UUID.randomUUID().toString(), "stop it plz"))
          .build();
}
