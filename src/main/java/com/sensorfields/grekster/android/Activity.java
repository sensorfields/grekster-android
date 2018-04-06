package com.sensorfields.grekster.android;

import static com.sensorfields.grekster.android.Effect.reportErrorNegative;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.Next.dispatch;
import static com.spotify.mobius.Next.next;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.spotify.mobius.MobiusLoop;
import com.spotify.mobius.Next;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import timber.log.Timber;

public final class Activity extends AppCompatActivity {

  static ObservableTransformer<Effect, Event> effectHandler =
      RxMobius.<Effect, Event>subtypeEffectHandler()
          .add(Effect.ReportErrorNegative.class, Activity::reportErrorNegativeHandler)
          .build();

  private final MobiusLoop<Model, Event, Effect> loop =
      RxMobius.loop(Activity::update, Activity.effectHandler).startFrom(Model.create(3));

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

  static Observable<Event> reportErrorNegativeHandler(
      Observable<Effect.ReportErrorNegative> effects) {
    return effects
        .doOnNext(effect -> Timber.e("NEGATIVE YO YO"))
        .switchMap(effect -> Observable.empty());
  }
}
