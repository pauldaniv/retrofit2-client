package com.pauldaniv.retrofit2.application.test;

import com.pauldaniv.retrofit2.client.RetrofitClient;
import retrofit2.http.GET;

@RetrofitClient(name = "myTestClient")
public interface TestClient {
    @GET("/tags")
    DummyModel test();
}
