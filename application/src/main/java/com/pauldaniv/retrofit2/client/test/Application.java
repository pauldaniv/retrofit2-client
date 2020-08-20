package com.pauldaniv.retrofit2.client.test;

import com.pauldaniv.retrofit2.clients.ClientProperties;
//import com.pauldaniv.test.clients.EnableRetrofitClients;
import com.pauldaniv.retrofit2.clients.EnableRetrofitClients;
import com.pauldaniv.retrofit2.clients.RetrofitClient;
import com.test.hello.MyRestApi;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.GET;

@SpringBootApplication
@EnableRetrofitClients(basePackages = "com.test")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Data
    @RestController
    @RequestMapping("/test")
    public static class TestController {
        @Autowired
        private TestClient testClient;
        @Autowired
        private MyRestApi test;
        @GetMapping
        String test() {
            return "It's working";
        }
    }

    @RetrofitClient(name = "myTestClient")
    public interface TestClient {
        @GET("/tags")
        Object test();
    }


}
