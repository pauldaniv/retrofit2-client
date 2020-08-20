package com.pauldaniv.retrofit2.application.test;

import com.pauldaniv.retrofit2.client.EnableRetrofitClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRetrofitClients(basePackages = "com.paul")
public class RestConfiguration {
}
