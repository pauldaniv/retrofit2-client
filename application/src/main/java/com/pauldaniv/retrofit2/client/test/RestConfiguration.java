package com.pauldaniv.retrofit2.client.test;

import com.pauldaniv.retrofit2.clients.EnableRetrofitClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRetrofitClients(basePackages = "com.test")
public class RestConfiguration {
}
