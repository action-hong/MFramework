package com.jornco.mframework.libs;

/**
 * Created by kkopite on 2018/7/17.
 */

public final class ApiCapabilities {
    private ApiCapabilities() {}

    public static boolean canCheckMediaControllerIsAttachedToWindow() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    public static boolean canCreateNotificationChannels() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O;
    }

    public static boolean canDebugWebViews() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    public static boolean canDetectMemoryLeaks() {
        // LeakCanary is currently broken on 23+. See https://github.com/square/leakcanary/issues/285 and
        // https://github.com/square/leakcanary/issues/267.
        return android.os.Build.VERSION.SDK_INT <= 22;
    }

    public static boolean canEvaluateJavascript() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    public static boolean canSetDarkStatusBarIcons() {
        return android.os.Build.VERSION.SDK_INT >= 23;
    }

    public static boolean canSetImmersiveSystemUI() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    public static boolean canSetNestingScrollingEnabled() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    public static boolean canSetStatusBarColor() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    public static boolean tls1_2IsEnabledByDefault() {
        return android.os.Build.VERSION.SDK_INT > 19;
    }
}
