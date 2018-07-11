package com.jornco.mframework.libs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.jornco.mframework.ui.data.ActivityResult;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.Timer;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/11.
 */

public class ActivityViewModel<ViewType extends ActivityLifecycleType> {

    // 干嘛用的?
    private final PublishSubject<ViewType> viewChange = PublishSubject.create();
    private final Observable<ViewType> view = this.viewChange.asObservable();
    private CompositeSubscription subscriptions = new CompositeSubscription();

    private final PublishSubject<ActivityResult> mActivityResult = PublishSubject.create();

    private final PublishSubject<Intent> intent = PublishSubject.create();

    public ActivityViewModel(final @NonNull Environment environment) {
        // TODO: koala
    }

    public void activityResult(final @NonNull ActivityResult activityResult) {
        this.mActivityResult.onNext(activityResult);
    }

    public void intent(final @NonNull Intent intent) {
        this.intent.onNext(intent);
    }

    @CallSuper
    protected void onCreate(final @NonNull Context context, final @Nullable Bundle savedInstanceState) {
        Timber.d("onCreate %s", this.toString());
        dropView();
    }

    @CallSuper
    protected void onResume(final @NonNull ViewType view) {
        Timber.d("onResume %s", this.toString());
        onTakeView(view);
    }

    @CallSuper
    protected void onPause() {
        Timber.d("onPause %s", this.toString());
        dropView();
    }

    @CallSuper
    protected void onDestroy() {
        Timber.d("onDestroy %s", this.toString());

        this.subscriptions.clear();
        this.viewChange.onCompleted();
    }

    private void onTakeView(final @NonNull ViewType view) {
        Timber.d("onTakeView %s %s", this.toString(), view.toString());
        this.viewChange.onNext(view);
    }

    private void dropView() {
        Timber.d("dropView %s", this.toString());
        this.viewChange.onNext(null);
    }

    protected @NonNull Observable<ActivityResult> activityResult() {
        return this.mActivityResult;
    }

    protected @NonNull Observable<Intent> intent() {
        return this.intent;
    }

    // 转化上游source, 到一个isFinished发送时, 该流onComplete()
    public @NonNull <T> Observable.Transformer<T, T> bindToLifecycle() {
        return source -> source.takeUntil(
                this.view.switchMap(v -> v.lifecycle().map(e -> Pair.create(v, e)))
                    .filter(ve -> isFinished(ve.first, ve.second))
        );
    }

    private Boolean isFinished(final @NonNull ViewType view, final @NonNull ActivityEvent event) {
//        if (view instanceof BaseActivity) {
//
//        }
        return event == ActivityEvent.DESTROY;
    }


}
