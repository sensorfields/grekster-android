package com.sensorfields.grekster.android;

import static com.sensorfields.grekster.android.Effect.reportErrorNegative;
import static com.spotify.mobius.Effects.effects;
import static com.spotify.mobius.Next.dispatch;
import static com.spotify.mobius.Next.next;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.spotify.mobius.MobiusLoop;
import com.spotify.mobius.MobiusLoop.Controller;
import com.spotify.mobius.Next;
import com.spotify.mobius.android.AndroidLogger;
import com.spotify.mobius.android.MobiusAndroid;
import com.spotify.mobius.rx2.RxConnectables;
import com.spotify.mobius.rx2.RxMobius;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

public final class Activity extends AppCompatActivity {

  private final ObservableTransformer<Effect, Event> effectHandler =
      RxMobius.<Effect, Event>subtypeEffectHandler()
          .add(Effect.ReportErrorNegative.class, this::reportErrorNegativeHandler)
          .build();

  private final MobiusLoop.Builder<Model, Event, Effect> loopFactory =
      RxMobius.loop(Activity::update, this.effectHandler)
          .logger(new AndroidLogger<>(Activity.class.getSimpleName()));
  private final Controller<Model, Event> controller =
      MobiusAndroid.controller(loopFactory, Model.initial());

  private Button upButton;
  private Button downButton;
  private TextView counterView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity);
    upButton = findViewById(R.id.up);
    downButton = findViewById(R.id.down);
    counterView = findViewById(R.id.counter);
  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    controller.connect(RxConnectables.fromTransformer(this::connectViews));
    controller.start();
  }

  @Override
  public void onDetachedFromWindow() {
    controller.stop();
    controller.disconnect();
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

  private Observable<Event> reportErrorNegativeHandler(
      Observable<Effect.ReportErrorNegative> effects) {
    return effects
        .doOnNext(
            effect ->
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "NEGATIVE YO YO",
                        BaseTransientBottomBar.LENGTH_SHORT)
                    .show())
        .switchMap(effect -> Observable.empty());
  }

  @SuppressWarnings("unchecked")
  private Observable<Event> connectViews(Observable<Model> models) {
    Disposable disposable =
        models.subscribe(model -> counterView.setText(String.valueOf(model.counter())));

    return Observable.mergeArray(
            RxView.clicks(upButton).map(ignored -> Event.up()),
            RxView.clicks(downButton).map(ignored -> Event.down()))
        .doOnDispose(disposable::dispose);
  }
}
