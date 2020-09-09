package com.pauldaniv.retrofit2.client

import okhttp3.OkHttpClient
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

class RetrofitClientFactoryBean : FactoryBean<Any>, ApplicationContextAware, InitializingBean {

  lateinit var type: Class<*>
  lateinit var name: String
  lateinit var value: String
  lateinit var clientProperties: ClientProperties.ApiProperties
  lateinit var ctx: ApplicationContext

  override fun getObject(): Any {
    val retrofitContext = ctx.getBean(RetrofitClientContext::class.java)
    val retrofitProperties = retrofitContext.getInstance(name, ClientProperties::class.java)

    clientProperties = retrofitProperties.services[name] ?: error("Missing $name configuration property")
    val retrofitBuilder = Retrofit.Builder().baseUrl(clientProperties.url)

    retrofitBuilder.client(retrofitContext.getInstance(name, OkHttpClient::class.java) ?: createHttpClient())

    retrofitContext.getInstances(name, Converter.Factory::class.java).let {
      it.values.forEach { factory -> retrofitBuilder.addConverterFactory(factory!!) }
    }

    retrofitContext.getInstances(name, CallAdapter.Factory::class.java).let {
      it.values.forEach { factory -> retrofitBuilder.addCallAdapterFactory(factory!!) }
    }

    if (value.isNotBlank()) {
      val methods = type.methods.filter { m -> supportedHttpAnnotations().any { m.isAnnotationPresent(it) } }
      val handlers = methods.flatMap { it.declaredAnnotations.asIterable() }.map { Proxy.getInvocationHandler(it) }
      handlers.forEach { handler ->
        handler::class.java.declaredFields
            .filter { it.name == "memberValues" }
            .onEach { it.isAccessible = true }
            .map { it.get(handler) as MutableMap<String, String> }
            .forEach {
              it["value"] = "$value${it["value"]}"
            }
      }
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

  private fun supportedHttpAnnotations(): List<Class<out Annotation>> = listOf(
      GET::class.java,
      POST::class.java,
      PUT::class.java,
      DELETE::class.java,
      PATCH::class.java,
      OPTIONS::class.java,
      HEAD::class.java
  )
}
