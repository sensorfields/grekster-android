package com.sensorfields.grekster.android;

import static com.sensorfields.grekster.android.Effect.reportErrorNegative;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.Next.dispatch;
import static com.spotify.mobius.Next.next;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.spotify.mobius.Connection;
import com.spotify.mobius.Mobius;
import com.spotify.mobius.MobiusLoop;
import com.spotify.mobius.Next;
import com.spotify.mobius.functions.Consumer;
import timber.log.Timber;

public final class Activity extends AppCompatActivity {

  MobiusLoop<Model, Event, Effect> loop =
      Mobius.loop(Activity::update, Activity::effectHandler).startFrom(Model.create(3));

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity);
    findViewById(R.id.up).setOnClickListener(ignored -> loop.dispatchEvent(Event.up()));
    findViewById(R.id.down).setOnClickListener(ignored -> loop.dispatchEvent(Event.down()));
  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    loop.observe(model -> Timber.e("MODEL: %s", model));
  }

  @Override
  public void onDetachedFromWindow() {
    loop.dispose();
    super.onDetachedFromWindow();
  }

  @NonNull
  static Next<Model, Effect> update(Model model, Event event) {
    return event.map(
        up -> next(model.increase()),
        down -> {
          if (model.counter() > 0) {
            return next(model.decrease());
          }
          return dispatch(effects(reportErrorNegative()));
        });
  }

  static Connection<Effect> effectHandler(Consumer<Event> eventConsumer) {
    return new Connection<Effect>() {
      @Override
      public void accept(@NonNull Effect effect) {
        effect.match(reportErrorNegative -> Timber.e("NEGATIVE YO YO"));
      }

      @Override
      public void dispose() {
        Timber.e("EFFECT HANDLER DISPOSE");
      }
    };
  }
}
