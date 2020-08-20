package com.pauldaniv.retrofit2.client

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.util.*

@Configuration
@EnableConfigurationProperties(ClientProperties::class)
@Import(RetrofitClientsRegistrar::class)
@EnableRetrofitClients(basePackages = ["com.paul", "com.pauldaniv"])
class RetrofitAutoconfiguration {
  @Bean
  fun retrofitContext(specs: Optional<List<RetrofitClientSpecification?>?>): RetrofitClientContext {
    val retrofitClientContext = RetrofitClientContext()
    specs.ifPresent(retrofitClientContext::setConfigurations)
    return retrofitClientContext
  }
}
