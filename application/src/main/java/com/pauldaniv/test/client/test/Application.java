package com.pauldaniv.test.client.test;

import com.pauldaniv.test.clients.ClientProperties;
//import com.pauldaniv.test.clients.EnableRetrofitClients;
import com.pauldaniv.test.clients.RetrofitClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.GET;

@SpringBootApplication
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
        private ClientProperties clientProperties;
        @GetMapping
        String test() {
            return "It's working";
        }
    }

    @RetrofitClient
    public interface TestClient {
        @GET("/tags")
        Object test();
    }
}
