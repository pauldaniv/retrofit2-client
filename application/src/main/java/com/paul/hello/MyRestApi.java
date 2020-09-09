package com.paul.hello;

import com.pauldaniv.retrofit2.application.test.DummyModel;
import com.pauldaniv.retrofit2.client.RetrofitClient;
import retrofit2.http.GET;

@RetrofitClient("/v1")
public interface MyRestApi {
    @GET("/test1")
    DummyModel test();
}
