package com.sensorfields.grekster.android.grek.create;

import static com.sensorfields.grekster.android.grek.create.Effect.getPhotoFromCamera;
import static com.sensorfields.grekster.android.grek.create.Effect.getPhotoFromGallery;
import static com.sensorfields.grekster.android.grek.create.Effect.navigateUp;
import static com.sensorfields.grekster.android.grek.create.Event.photoCameraButtonClicked;
import static com.sensorfields.grekster.android.grek.create.Event.photoGalleryButtonClicked;
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
    assertThat(next, hasEffects(navigateUp()));
  }

  @Test
  public void photoCameraButtonClickedEvent_returns_noModel_getPhotoFromCameraEffect() {
    Next<Model, Effect> next = update(Model.initial(), photoCameraButtonClicked());

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(getPhotoFromCamera()));
  }

  @Test
  public void photoGalleryButtonClickedEvent_returns_noModel_getPhotoFromGalleryEffect() {
    Next<Model, Effect> next = update(Model.initial(), photoGalleryButtonClicked());

    assertThat(next, hasNoModel());
    assertThat(next, hasEffects(getPhotoFromGallery()));
  }
}
