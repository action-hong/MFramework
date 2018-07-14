package com.jornco.mframework.libs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import com.jornco.mframework.ApplicationComponent;
import com.jornco.mframework.KSApplication;
import com.jornco.mframework.libs.qualifiers.RequiresActivityViewModel;
import com.jornco.mframework.libs.utils.BundleUtils;
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

        // 创建viewModel, or 从 savedInstanceState中恢复viewModel
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

    @CallSuper
    @Override
    @Deprecated
    public void onBackPressed() {
        back();
    }

    public void back() {
        this.back.onNext(null);
    }

    /**
     * Override in subclasses for custom exit transitions. First item in pair is the enter animation,
     * second item in pair is the exit animation.
     */
    protected @Nullable
    Pair<Integer, Integer> exitTransition() {
        return null;
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSaveInstanceState %s", this.toString());

        final Bundle viewModelEnvelope = new Bundle();
        if (this.viewModel != null) {
            ActivityViewModelManager.getInstance().save(this.viewModel, viewModelEnvelope);
        }

        outState.putBundle(VIEW_MODEL_KEY, viewModelEnvelope);
    }

    protected final void startActivityWithTransition(final @NonNull Intent intent, final @AnimRes int enterAnim,
                                                     final @AnimRes int exitAnim) {
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    protected @NonNull
    KSApplication application() {
        return (KSApplication) getApplication();
    }

    protected @NonNull
    ApplicationComponent component() {
        return application().component();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public @NonNull Environment environment() {
        return component().environment();
    }

    private void goBack(){
        super.onBackPressed();

        Pair<Integer, Integer> exitTransition = exitTransition();
        if (exitTransition != null) {
            overridePendingTransition(exitTransition.first, exitTransition.second);
        }
    }

    private void assignViewModel(final @Nullable Bundle viewModelEnvelope) {
        if (this.viewModel == null) {
            final RequiresActivityViewModel annotation = getClass().getAnnotation(RequiresActivityViewModel.class);
            final Class<ViewModelType> viewModelClass = annotation == null ? null : (Class<ViewModelType>) annotation.value();
            if (viewModelClass != null) {
                // fetch 的第三个参数就是下面这个 在 onSaveInstanceState()声明的变量
//                final Bundle viewModelEnvelope = new Bundle();
                this.viewModel = ActivityViewModelManager.getInstance().fetch(this,
                        viewModelClass,
                        BundleUtils.maybeGetBundle(viewModelEnvelope, VIEW_MODEL_KEY));
            }
        }
    }
}
