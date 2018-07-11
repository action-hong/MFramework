package com.jornco.mframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import com.jornco.mframework.libs.ActivityLifecycleType;
import com.jornco.mframework.libs.ActivityViewModel;
import com.jornco.mframework.ui.data.ActivityResult;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/12.
 */

public abstract class BaseActivity<ViewModelType extends ActivityViewModel> extends AppCompatActivity implements ActivityLifecycleType, ActivityLifecycleProvider {

    private final PublishSubject<Void> back = PublishSubject.create();
    private final BehaviorSubject<ActivityEvent> lifecycle = BehaviorSubject.create();
    private static final String VIEW_MODEL_KEY = "viewModel";
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    protected ViewModelType viewModel;

    public ViewModelType viewModel() {
        return this.viewModel;
    }

    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycle.asObservable();
    }

    public final <T> Observable.Transformer<T, T> bindUntilEvent(final ActivityEvent event) {
        return RxLifecycle.bindUntilActivityEvent(this.lifecycle, event);
    }

    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity(this.lifecycle);
    }

    @CallSuper
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        this.viewModel.activityResult(ActivityResult.create(requestCode, resultCode, intent));
    }

    @CallSuper
    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate %s", this.toString());

        this.lifecycle.onNext(ActivityEvent.CREATE);

        assignViewModel(savedInstanceState);

        this.viewModel.intent(getIntent());
    }

    @CallSuper
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        this.viewModel.intent(intent);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart %s", this.toString());
        this.lifecycle.onNext(ActivityEvent.START);

        // 这里就可以监听返回事件, 重写goBack 做相应处理
        this.back
                .compose(bindUntilEvent(ActivityEvent.STOP))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> goBack());
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume %s", this.toString());
        this.lifecycle.onNext(ActivityEvent.RESUME);

        assignViewModel(null);
        if (this.viewModel != null) {
            this.viewModel.onResume(this);
        }
    }

    @CallSuper
    @Override
    protected void onPause() {
        this.lifecycle.onNext(ActivityEvent.PAUSE);
        super.onPause();
        Timber.d("onPause %s", this.toString());

        if (this.viewModel != null) {
            this.viewModel.onPause();
        }
    }

    @CallSuper
    @Override
    protected void onStop() {
        this.lifecycle.onNext(ActivityEvent.STOP);
        super.onStop();
        Timber.d("onStop %s", this.toString());
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        this.lifecycle.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        Timber.d("onDestroy %s", this.toString());

        this.subscriptions.clear();

        if (isFinishing()) {
            if (this.viewModel != null) {
                ActivityViewModelManager.getInstance().destroy(this.viewModel);
                this.viewModel = null;
            }
        }
    }

    public void back() {
        this.back.onNext(null);
    }

    protected @Nullable
    Pair<Integer, Integer> exitTransition() {
        return null;
    }

}
