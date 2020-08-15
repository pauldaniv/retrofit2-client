package com.pauldaniv.retrofit2.clients

import okhttp3.OkHttpClient
import org.reflections.Reflections
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class ClientBuilder : BeanFactoryPostProcessor {
  private lateinit var apiProperties: ClientProperties.ApiProperties
  private val clientNames = mutableListOf<String>()
  private val dummy = Any()

  @Suppress("UNCHECKED_CAST")
  override fun postProcessBeanFactory(factory: ConfigurableListableBeanFactory) {
    val typesAnnotatedWith = Reflections("<package> to be configured :D ").getTypesAnnotatedWith(Client::class.java)
    typesAnnotatedWith.forEach {
      val registry = factory as BeanDefinitionRegistry
      clientNames.add(it.simpleName.decapitalize())
      registry.registerBeanDefinition(
          it.simpleName.decapitalize(),
          BeanDefinitionBuilder
              .genericBeanDefinition(it as Class<Any>) { dummy }
              .beanDefinition
      )
    }
  }

  @Bean
  fun beanPostProcessor(clients: ClientProperties,
                        configurableListableBeanFactory: ConfigurableListableBeanFactory
  ): BeanPostProcessor {
    return object : BeanPostProcessor {
      override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return if (clientNames.contains(beanName)) {
          apiProperties = clients.apis[beanName]
              ?: throw IllegalStateException("Could not find $beanName client properties")
          buildClient(Class.forName(configurableListableBeanFactory.getBeanDefinition(beanName).beanClassName))
        } else bean
      }
    }
  }

  private fun buildClient(client: Class<*>) = getRetrofit().create(client)

  private fun getRetrofit() = Retrofit.Builder()
      .baseUrl(apiProperties.url)
      .client(createHttpClient())
      .addCallAdapterFactory(SyncCallFactory())
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  private fun createHttpClient() = OkHttpClient.Builder()
      .writeTimeout(apiProperties.readTimeout ?: 30, TimeUnit.SECONDS)
      .readTimeout(apiProperties.readTimeout ?: 30, TimeUnit.SECONDS)
      .retryOnConnectionFailure(true)
      .build()
}
