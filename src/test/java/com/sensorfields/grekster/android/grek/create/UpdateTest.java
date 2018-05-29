package com.sensorfields.grekster.android.grek.create;

import static com.sensorfields.grekster.android.grek.create.Event.upButtonClicked;
import static com.sensorfields.grekster.android.grek.create.GrekCreateViewModel.update;
import static com.spotify.mobius.test.NextMatchers.hasEffects;
import static com.spotify.mobius.test.NextMatchers.hasNoModel;
import static org.junit.Assert.assertThat;

import com.spotify.mobius.Next;
import org.junit.Test;

public final class UpdateTest {

  @Test
  public void upButtonClickedEvent_returns_noModel_navigateUpEffect() {
    Next<Model, Effect> next = update(Model.initial(), upButtonClicked());

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(Effect.navigateUp()));
  }
}
