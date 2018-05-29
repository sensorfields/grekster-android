package com.jakewharton.rxbinding3.androidx.swiperefreshlayout.widget;

import static com.jakewharton.rxbinding3.internal.Preconditions.checkNotNull;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class RxSwipeRefreshLayout {
  /**
   * Create an observable of refresh events on {@code view}.
   * <p>
   * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
   * to free this reference.
   */
  @CheckResult @NonNull public static Observable<Object> refreshes(
      @NonNull SwipeRefreshLayout view) {
    checkNotNull(view, "view == null");
    return new SwipeRefreshLayoutRefreshObservable(view);
  }

  /**
   * An action which sets whether the layout is showing the refreshing indicator.
   * <p>
   * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
   * to free this reference.
   *
   * @deprecated Use view::setRefreshing method reference.
   */
  @Deprecated
  @CheckResult @NonNull public static Consumer<? super Boolean> refreshing(
      @NonNull final SwipeRefreshLayout view) {
    checkNotNull(view, "view == null");
    return new Consumer<Boolean>() {
      @Override public void accept(Boolean value) {
        view.setRefreshing(value);
      }
    };
  }

  private RxSwipeRefreshLayout() {
    throw new AssertionError("No instances.");
  }
}
