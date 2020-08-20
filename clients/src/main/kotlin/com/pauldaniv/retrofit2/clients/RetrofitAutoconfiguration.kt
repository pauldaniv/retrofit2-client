package com.pauldaniv.retrofit2.clients

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.util.*

@Configuration
@EnableConfigurationProperties(ClientProperties::class)
@Import(RetrofitClientsRegistrar::class)
class RetrofitAutoconfiguration {
  @Bean
  fun retrofitContext(specs: Optional<List<RetrofitClientSpecification?>?>): RetrofitClientContext {
    val retrofitClientContext = RetrofitClientContext()
    specs.ifPresent(retrofitClientContext::setConfigurations)
    return retrofitClientContext
  }
}
