package com.pauldaniv.retrofit2.client

import org.springframework.cloud.context.named.NamedContextFactory

class RetrofitClientContext :
  NamedContextFactory<RetrofitClientSpecification?>(
      DefaultRetrofitClientConfiguration::class.java, "retrofit", "retrofit.client.name")
