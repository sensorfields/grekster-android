package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Event.greksLoaded;
import static com.sensorfields.grekster.android.grek.list.Event.greksLoadingFailed;
import static com.sensorfields.grekster.android.grek.list.GrekListViewModel.update;
import static com.spotify.mobius.test.NextMatchers.hasModel;
import static com.spotify.mobius.test.NextMatchers.hasNoEffects;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import com.spotify.mobius.Next;
import org.junit.Test;

public final class UpdateTest {

  @Test
  public void modelActivityTrue_eventGreksLoaded_returns_modelActivityFalseAndGreks() {
    Model model = Model.initial().toBuilder().activity(true).build();
    ImmutableList<String> greks = ImmutableList.of("One", "Two", "Three");

    Next<Model, Effect> next = update(model, greksLoaded(greks));

    assertThat(next, hasModel(model.toBuilder().activity(false).greks(greks).build()));
    assertThat(next, hasNoEffects());
  }

  @Test
  public void modelActivityTrue_eventGreksLoadingFailed_returns_modelActivityFalseAndError() {
    Model model = Model.initial().toBuilder().activity(true).build();
    Throwable error = new IllegalArgumentException();

    Next<Model, Effect> next = update(model, greksLoadingFailed(error));

    assertThat(next, hasModel(model.toBuilder().activity(false).error(error).build()));
    assertThat(next, hasNoEffects());
  }
}
