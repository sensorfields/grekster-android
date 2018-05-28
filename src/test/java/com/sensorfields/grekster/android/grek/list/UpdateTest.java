package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.sensorfields.grekster.android.grek.list.Effect.showGrekCreate;
import static com.sensorfields.grekster.android.grek.list.Effect.showGrekDetails;
import static com.sensorfields.grekster.android.grek.list.Event.createButtonClicked;
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
import com.sensorfields.grekster.android.model.Grek;
import com.spotify.mobius.Next;
import org.junit.Test;

public final class UpdateTest {

  private static final ImmutableList<Grek> GREKS =
      ImmutableList.of(
          Grek.create("id1", "One"), Grek.create("id2", "Two"), Grek.create("id3", "Three"));

  @Test
  public void greksLoadedEventReturnsModelWithActivityFalseAndGreks() {
    Model model = Model.initial().toBuilder().activity(true).build();

    Next<Model, Effect> next = update(model, greksLoaded(GREKS));

    assertThat(next, hasModel(model.toBuilder().activity(false).greks(GREKS).build()));
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
    Model model = Model.initial().toBuilder().greks(GREKS).build();

    Next<Model, Effect> next = update(model, swipeRefreshTriggered());

    assertThat(next, hasModel(model.toBuilder().activity(true).build()));
    assertThat(next, hasEffects(loadGreks()));
  }

  @Test
  public void grekClickedEventReturnsNoModelAndShowGrekDetailsEffect() {
    Model model = Model.initial().toBuilder().greks(GREKS).build();

    Next<Model, Effect> next = update(model, grekClicked(GREKS.get(1)));

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(showGrekDetails(GREKS.get(1))));
  }

  @Test
  public void grekClickedEventWithUnknownGrekReturnsNoModelAndNoEffect() {
    Model model = Model.initial().toBuilder().greks(GREKS).build();

    Next<Model, Effect> next = update(model, grekClicked(Grek.create("id4", "Four")));

    assertThat(next, hasNoModel());
    assertThat(next, hasNoEffects());
  }

  @Test
  public void createButtonClickedEvent_noModel_showGrekCreateEffect() {
    Next<Model, Effect> next = update(Model.initial(), createButtonClicked());

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(showGrekCreate()));
  }
}
