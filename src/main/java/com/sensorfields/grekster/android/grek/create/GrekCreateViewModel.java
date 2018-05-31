package com.sensorfields.grekster.android.grek.create;

import static com.sensorfields.grekster.android.grek.create.Effect.getPhotoFromCamera;
import static com.sensorfields.grekster.android.grek.create.Effect.navigateUp;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.Next.dispatch;
import static com.spotify.mobius.Next.noChange;

import android.support.annotation.NonNull;
import com.sensorfields.cyborg.CyborgViewModel;
import com.sensorfields.grekster.android.grek.create.Effect.GetPhotoFromCamera;
import com.sensorfields.grekster.android.grek.create.Effect.NavigateUp;
import com.sensorfields.grekster.android.grek.create.handler.GetPhotoFromCameraHandler;
import com.sensorfields.grekster.android.grek.create.handler.NavigateUpHandler;
import com.sensorfields.grekster.android.utils.LoggerFactory;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import javax.inject.Inject;

public final class GrekCreateViewModel extends CyborgViewModel<Model, Event, Effect> {

  @Inject
  GrekCreateViewModel(
      LoggerFactory loggerFactory,
      NavigateUpHandler navigateUpHandler,
      GetPhotoFromCameraHandler getPhotoFromCameraHandler) {
    super(
        RxMobius.loop(
                GrekCreateViewModel::update,
                RxMobius.<Effect, Event>subtypeEffectHandler()
                    .add(NavigateUp.class, navigateUpHandler)
                    .add(GetPhotoFromCamera.class, getPhotoFromCameraHandler)
                    .build())
            .logger(loggerFactory.create(GrekCreateViewModel.class))
            .startFrom(Model.initial()));
  }

  @NonNull
  static Next<Model, Effect> update(Model model, Event event) {
    return event.map(
        upButtonClicked -> dispatch(effects(navigateUp())),
        photoCameraButtonClicked -> dispatch(effects(getPhotoFromCamera())),
        photosReceived -> noChange());
  }
}
