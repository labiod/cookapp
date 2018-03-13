package com.kgb.cooapp.service;

import com.kgb.cooapp.service.endpoint.EndPointList;
import com.kgb.cooapp.service.endpoint.UserEndPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface VApiService {
    @GET("apiv2/")
    Call<EndPointList> listProvider(@Header("Authorization") String authKey);

    @GET("apiv2/user")
    Call<UserEndPoint> getUserInfo(@Header("Authorization") String authKey);
}
