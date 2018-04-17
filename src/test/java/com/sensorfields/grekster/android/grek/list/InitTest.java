package com.sensorfields.grekster.android.grek.list;

import static com.sensorfields.grekster.android.grek.list.Effect.loadGreks;
import static com.sensorfields.grekster.android.grek.list.GrekListViewModel.init;
import static com.spotify.mobius.test.FirstMatchers.hasEffects;
import static com.spotify.mobius.test.FirstMatchers.hasModel;
import static org.junit.Assert.assertThat;

import com.spotify.mobius.First;
import org.junit.Test;

public final class InitTest {

  @Test
  public void initialModel_returns_modelActivityTrue_loadGreksEffect() {
    Model model = Model.initial();

    First<Model, Effect> first = init(model);

    assertThat(first, hasModel(model.toBuilder().activity(true).build()));
    assertThat(first, hasEffects(loadGreks()));
  }
}
