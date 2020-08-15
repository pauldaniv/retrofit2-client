package com.pauldaniv.retrofit2.clients

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("app")
data class ClientProperties(val apis: Map<String, ApiProperties>) {
  data class ApiProperties(val url: String, val writeTimeout: Long?, val readTimeout: Long?)
}
