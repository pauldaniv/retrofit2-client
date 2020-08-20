package com.pauldaniv.test.clients

import org.springframework.cloud.context.named.NamedContextFactory.Specification


class RetrofitClientSpecification(private val name: String, private val configs: Array<Class<*>>) :
  Specification {
  override fun getName(): String {
    return name
  }

  override fun getConfiguration(): Array<Class<*>> {
    return configs
  }
}
