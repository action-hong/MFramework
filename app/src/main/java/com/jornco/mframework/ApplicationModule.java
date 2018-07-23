package com.jornco.mframework;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jornco.mframework.libs.ApiClient;
import com.jornco.mframework.libs.ApiClientType;
import com.jornco.mframework.libs.ApiEndpoint;
import com.jornco.mframework.libs.AutoParcelAdapterFactory;
import com.jornco.mframework.libs.Build;
import com.jornco.mframework.libs.CurrentUser;
import com.jornco.mframework.libs.CurrentUserType;
import com.jornco.mframework.libs.DateTimeTypeConverter;
import com.jornco.mframework.libs.Environment;
import com.jornco.mframework.libs.qualifiers.ApiRetrofit;
import com.jornco.mframework.libs.utils.Secrets;
import com.jornco.mframework.services.ApiService;
import com.jornco.mframework.services.KSWebViewClient;
import com.jornco.mframework.services.interceptors.ApiRequestInterceptor;
import com.jornco.mframework.services.interceptors.KSRequestInterceptor;
import com.jornco.mframework.services.interceptors.WebRequestInterceptor;

import org.joda.time.DateTime;

import java.net.CookieManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/11.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(final @NonNull Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    static Environment provideEnvironment(@NonNull Gson gson,
        @NonNull ApiClientType apiClient) {
        return Environment.builder()
                .gson(gson)
                .apiClient(apiClient)
                .build();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .registerTypeAdapterFactory(new AutoParcelAdapterFactory())
                .create();
    }

    @Provides
    @Singleton
    @NonNull
    static ApiClientType provideApiClientType(final @NonNull ApiService apiService, final @NonNull Gson gson) {
        return new ApiClient(apiService, gson);
    }

    @Provides
    @Singleton
    @NonNull
    static ApiService provideApiService(final @ApiRetrofit @NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    @ApiRetrofit
    @NonNull
    static Retrofit provideApiRetrofit(final @NonNull ApiEndpoint apiEndpoint,
                                       final @NonNull Gson gson,
                                       final @NonNull OkHttpClient okHttpClient) {
        return createRetrofit(apiEndpoint.url(), gson, okHttpClient);
    }

    @Provides
    @Singleton
    @NonNull
    static OkHttpClient provideOkHttpClient(final @NonNull ApiRequestInterceptor apiRequestInterceptor, final @NonNull CookieJar cookieJar,
                                            final @NonNull HttpLoggingInterceptor httpLoggingInterceptor, final @NonNull KSRequestInterceptor ksRequestInterceptor,
                                            final @NonNull Build build, final @NonNull WebRequestInterceptor webRequestInterceptor) {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.
        if (build.isDebug()) {
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return builder
                .addInterceptor(apiRequestInterceptor)
                .addInterceptor(webRequestInterceptor)
                .addInterceptor(ksRequestInterceptor)
                .cookieJar(cookieJar)
                .hostnameVerifier((hostname, session) -> {
                    Timber.d(hostname);
                    return true;
                })
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    static ApiRequestInterceptor provideApiRequestInterceptor(final @NonNull String clientId,
                                                              final @NonNull CurrentUserType currentUser, final @NonNull ApiEndpoint endpoint) {
        return new ApiRequestInterceptor(clientId, currentUser, endpoint.url());
    }

    @Provides
    @Singleton
    @NonNull
    static KSRequestInterceptor provideKSRequestInterceptor(final @NonNull Build build) {
        return new KSRequestInterceptor(build);
    }

    @Provides
    @Singleton
    @NonNull
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return interceptor;
    }

    @Provides
    @Singleton
    @NonNull
    static WebRequestInterceptor provideWebRequestInterceptor() {
        return new WebRequestInterceptor();
    }

    @Provides
    @Singleton
    static CurrentUserType provideCurrentUser() {
        return new CurrentUser();
    }

    @Provides
    @Singleton
    static String provideClientId(final @NonNull ApiEndpoint apiEndpoint) {
        return apiEndpoint == ApiEndpoint.PRODUCTION
                ? Secrets.Api.Client.PRODUCTION
                : Secrets.Api.Client.STAGING;
    }

    @Provides
    @Singleton
    @NonNull
    static Build provideBuild(final @NonNull PackageInfo packageInfo) {
        return new Build(packageInfo);
    }

    @Provides
    @Singleton
    static PackageInfo providePackageInfo(final @NonNull Application application) {
        try {
            return application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Provides
    @Singleton
    static CookieJar provideCookieJar(final @NonNull CookieManager cookieManager) {
        return new JavaNetCookieJar(cookieManager);
    }

    @Provides
    @Singleton
    static CookieManager provideCookieManager() {
        return new CookieManager();
    }

    @Provides
    static KSWebViewClient provideKSWebViewClient() {
        return new KSWebViewClient();
    }

    private static Retrofit createRetrofit(String url, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
