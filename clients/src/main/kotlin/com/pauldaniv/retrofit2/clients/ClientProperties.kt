package com.pauldaniv.retrofit2.clients

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("retrofit")
data class ClientProperties(val services: Map<String, ApiProperties>) {
  data class ApiProperties(val url: String, val writeTimeout: Long?, val readTimeout: Long?)
}
