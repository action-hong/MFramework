package com.jornco.mframework.libs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornco.mframework.KSApplication;
import com.jornco.mframework.libs.utils.BundleUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by kkopite on 2018/7/12.
 */

public class ActivityViewModelManager {
    public static final String VIEW_MODEL_ID_KEY = "view_model_id";
    public static final String VIEW_MODEL_STATE_KEY = "view_model_state";

    private static final ActivityViewModelManager instance = new ActivityViewModelManager();
    private Map<String, ActivityViewModel> viewModels = new HashMap<>();

    public static @NonNull ActivityViewModelManager getInstance() {return instance;}

    public <T extends ActivityViewModel> T fetch (final @NonNull Context context, final @NonNull Class<T> viewModelClass,
                                                  final @Nullable Bundle savedInstanceState) {
        final String id = fetchId(savedInstanceState);
        ActivityViewModel activityViewModel = this.viewModels.get(id);

        if (activityViewModel == null) {
            activityViewModel = create(context, viewModelClass, savedInstanceState, id);
        }

        return (T) activityViewModel;
    }

    private String fetchId(Bundle savedInstanceState) {
        return savedInstanceState != null ?
                savedInstanceState.getString(VIEW_MODEL_ID_KEY) :
                UUID.randomUUID().toString();
    }

    // 反射创建ActivityViewModel
    private <T extends ActivityViewModel> ActivityViewModel create(Context context, Class<T> viewModelClass, Bundle savedInstanceState, String id) {
        final KSApplication application = (KSApplication) context.getApplicationContext();
        final Environment environment = application.component().environment();
        ActivityViewModel activityViewModel = null;

        try {
            Constructor<T> constructor = viewModelClass.getConstructor(Environment.class);
            activityViewModel = constructor.newInstance(environment);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 存好id
        this.viewModels.put(id, activityViewModel);
        // 这里的第二个参数便是, save()里面放进去的state
        // final Bundle state = new Bundle();
        // envelope.putBundle(VIEW_MODEL_STATE_KEY, state);
        activityViewModel.onCreate(context, BundleUtils.maybeGetBundle(savedInstanceState, VIEW_MODEL_STATE_KEY));

        return activityViewModel;

    }

    public void save(final @NonNull ActivityViewModel activityViewModel, final @NonNull Bundle envelope) {
        // 存好state, key
        envelope.putString(VIEW_MODEL_ID_KEY, findIdForViewModel(activityViewModel));

        final Bundle state = new Bundle();
        envelope.putBundle(VIEW_MODEL_STATE_KEY, state);
    }

    // 返回该viewModel 的对应id
    private String findIdForViewModel(ActivityViewModel activityViewModel) {
        for (Map.Entry<String, ActivityViewModel> entry : viewModels.entrySet()) {
            if (activityViewModel.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Cannot find view model in map!");
    }

    public void destroy(final @NonNull ActivityViewModel activityViewModel) {
        activityViewModel.onDestroy();

        final Iterator<Map.Entry<String, ActivityViewModel>> iterator = this.viewModels.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, ActivityViewModel> entry = iterator.next();
            if (activityViewModel.equals(entry.getValue())) {
                iterator.remove();
            }
        }
    }
}
