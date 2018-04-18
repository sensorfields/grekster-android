package com.sensorfields.grekster.android.utils;

import com.spotify.mobius.MobiusLoop.Logger;
import com.spotify.mobius.android.AndroidLogger;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class LoggerFactory {

  @Inject
  LoggerFactory() {}

  public final <M, E, F> Logger<M, E, F> create(Class<?> type) {
    return new AndroidLogger<>(parseTag(type));
  }

  private static String parseTag(Class<?> type) {
    String tag = type.getSimpleName();
    return tag.replace("ViewModel", "");
  }
}
