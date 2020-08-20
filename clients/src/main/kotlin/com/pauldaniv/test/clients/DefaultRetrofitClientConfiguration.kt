package com.pauldaniv.test.clients

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Converter
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class DefaultRetrofitClientConfiguration {

  @Bean
  @Autowired
  fun jacksonConverter(objectMapper: ObjectMapper): Converter.Factory {
    objectMapper.registerKotlinModule()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return JacksonConverterFactory.create(objectMapper)
  }

  @Bean
  @ConditionalOnMissingBean
  fun objectMapper() = ObjectMapper()
}
