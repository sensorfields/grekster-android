package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.sensorfields.grekster.android.grek.list.Effect.showGrekDetails;
import static com.sensorfields.grekster.android.grek.list.Event.grekClicked;
import static com.sensorfields.grekster.android.grek.list.Event.greksLoaded;
import static com.sensorfields.grekster.android.grek.list.Event.greksLoadingFailed;
import static com.sensorfields.grekster.android.grek.list.Event.swipeRefreshTriggered;
import static com.sensorfields.grekster.android.grek.list.GrekListViewModel.update;
import static com.spotify.mobius.test.NextMatchers.hasEffects;
import static com.spotify.mobius.test.NextMatchers.hasModel;
import static com.spotify.mobius.test.NextMatchers.hasNoEffects;
import static com.spotify.mobius.test.NextMatchers.hasNoModel;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import com.spotify.mobius.Next;
import org.junit.Test;

public final class UpdateTest {

  @Test
  public void greksLoadedEventReturnsModelWithActivityFalseAndGreks() {
    Model model = Model.initial().toBuilder().activity(true).build();
    ImmutableList<String> greks = ImmutableList.of("One", "Two", "Three");

    Next<Model, Effect> next = update(model, greksLoaded(greks));

    assertThat(next, hasModel(model.toBuilder().activity(false).greks(greks).build()));
    assertThat(next, hasNoEffects());
  }

  @Test
  public void greksLoadingFailedEventReturnsModelWithActivityFalseAndError() {
    Model model = Model.initial().toBuilder().activity(true).build();
    Throwable error = new IllegalArgumentException();

    Next<Model, Effect> next = update(model, greksLoadingFailed(error));

    assertThat(next, hasModel(model.toBuilder().activity(false).error(error).build()));
    assertThat(next, hasNoEffects());
  }

  @Test
  public void swipeRefreshTriggeredEventReturnsModelWithActivityTrueAndLoadGreksEffect() {
    ImmutableList<String> greks = ImmutableList.of("One", "Two", "Three");
    Model model = Model.initial().toBuilder().greks(greks).build();

    Next<Model, Effect> next = update(model, swipeRefreshTriggered());

    assertThat(next, hasModel(model.toBuilder().activity(true).build()));
    assertThat(next, hasEffects(loadGreks()));
  }

  @Test
  public void grekClickedEventReturnsNoModelAndShowGrekDetailsEffect() {
    ImmutableList<String> greks = ImmutableList.of("One", "Two", "Three");
    Model model = Model.initial().toBuilder().greks(greks).build();

    Next<Model, Effect> next = update(model, grekClicked("Two"));

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(showGrekDetails("Two")));
  }

  @Test
  public void grekClickedEventWithUnknownGrekReturnsNoModelAndNoEffect() {
    ImmutableList<String> greks = ImmutableList.of("One", "Two", "Three");
    Model model = Model.initial().toBuilder().greks(greks).build();

    Next<Model, Effect> next = update(model, grekClicked("Four"));

    assertThat(next, hasNoModel());
    assertThat(next, hasNoEffects());
  }
}
