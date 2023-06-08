package com.pauldaniv.retrofit2.application.test;

import com.paul.hello.MyRestApi;
import com.pauldaniv.retrofit2.application.test.mypackage.RestConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(RestConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Data
    @RestController
    @RequestMapping("/v1")
    public static class TestController {

//        @Autowired
//        private TestClient testClient;
        @Autowired
        private MyRestApi myRestApi;
        @GetMapping("/test1")
        DummyModel test1() {
            return new DummyModel("It's working!!");
        }

        @GetMapping("/test")
        DummyModel test() {
            return myRestApi.test();
        }
    }
}
