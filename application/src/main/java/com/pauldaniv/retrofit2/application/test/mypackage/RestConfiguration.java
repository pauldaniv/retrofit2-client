package com.pauldaniv.retrofit2.application.test.mypackage;

import com.pauldaniv.retrofit2.client.EnableRetrofitClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRetrofitClients(basePackages = "com.paul")
@Slf4j
public class RestConfiguration {

}
