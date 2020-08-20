package com.pauldaniv.test.clients

import okhttp3.OkHttpClient
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


class RetrofitClientFactoryBean : FactoryBean<Any>, ApplicationContextAware, InitializingBean {

  lateinit var type: Class<*>
  lateinit var name: String
  lateinit var clientProperties: ClientProperties.ApiProperties
  lateinit var ctx: ApplicationContext

  override fun getObject(): Any {
    val retrofitClientContext = ctx.getBean(RetrofitClientContext::class.java)
    val retrofitClientProperties = retrofitClientContext.getInstance(name, ClientProperties::class.java)
    clientProperties = retrofitClientProperties.services[name] ?: error("Missing $name configuration property")
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(clientProperties.url)
        .client(createHttpClient())
        .addCallAdapterFactory(SyncCallFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())

    val okHttpClient = Optional
        .ofNullable(
            retrofitClientContext.getInstance(name, OkHttpClient::class.java))
        .orElse(createHttpClient())
    retrofitBuilder.client(okHttpClient)

    Optional.ofNullable(
        retrofitClientContext.getInstances(name, Converter.Factory::class.java))
        .ifPresent { a: Map<String?, Converter.Factory?> ->
          a.values.forEach(Consumer { factory: Converter.Factory? -> retrofitBuilder.addConverterFactory(factory!!) })
        }

    Optional.ofNullable(
        retrofitClientContext.getInstances(name, CallAdapter.Factory::class.java))
        .ifPresent { instances: Map<String?, CallAdapter.Factory?> ->
          instances.values
              .forEach(Consumer { factory: CallAdapter.Factory? -> retrofitBuilder.addCallAdapterFactory(factory!!) })
        }
    return retrofitBuilder.build().create(type)
  }

  override fun getObjectType(): Class<*>? {
    return if (::type.isInitialized) {
      type
    } else null
  }

  override fun isSingleton(): Boolean {
    return true
  }

  override fun afterPropertiesSet() {}

  override fun setApplicationContext(applicationContext: ApplicationContext) {
    ctx = applicationContext
  }

  private fun createHttpClient() = OkHttpClient.Builder()
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .retryOnConnectionFailure(true)
      .build()
}
