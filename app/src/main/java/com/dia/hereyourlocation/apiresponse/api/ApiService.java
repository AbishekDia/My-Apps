package com.dia.hereyourlocation.apiresponse.api;

import com.dia.hereyourlocation.apiresponse.model.APIResponse;
import com.dia.hereyourlocation.apiresponse.model.Auth;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by abishek on 2/13/2017.
 */

public interface ApiService {

    @Headers("Content-type: application/json")
    @POST("/pbb/sec/login")
    Call<APIResponse> login(@Body Auth auth);
}
