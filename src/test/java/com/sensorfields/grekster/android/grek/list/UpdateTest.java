package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.sensorfields.grekster.android.grek.list.Event.screenLoaded;
import static com.sensorfields.grekster.android.grek.list.GrekListViewModel.update;
import static com.spotify.mobius.test.NextMatchers.hasEffects;
import static com.spotify.mobius.test.NextMatchers.hasModel;
import static org.junit.Assert.assertThat;

import com.spotify.mobius.Next;
import org.junit.Test;

public final class UpdateTest {

  @Test
  public void screenLoaded_() {
    Next<Model, Effect> next = update(Model.initial(), screenLoaded());
    assertThat(next, hasModel(Model.initial().toBuilder().activity(true).build()));
    assertThat(next, hasEffects(loadGreks()));
  }
}
