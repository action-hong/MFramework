package com.jornco.mframework.services;

import com.jornco.mframework.libs.models.BaseEntry;
import com.jornco.mframework.services.apiresponses.RobotModel;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kkopite on 2018/7/11.
 */

public interface ApiService {

    @GET("info/models/{version}/{lang}/json")
    Observable<Response<BaseEntry<List<RobotModel>>>> getNewModels(@Path("version") int version, @Path("lang") String lang);
}
