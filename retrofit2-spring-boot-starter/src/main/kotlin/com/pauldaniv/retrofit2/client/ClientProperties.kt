package com.pauldaniv.retrofit2.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("retrofit")
data class ClientProperties(val services: Map<String, ApiProperties>) {
  data class ApiProperties(val url: String, val writeTimeout: Long?, val readTimeout: Long?)
}
