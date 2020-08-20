package com.pauldaniv.test.clients


import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.util.ClassUtils

class RetrofitClientsRegistrar : ImportBeanDefinitionRegistrar {

  override fun registerBeanDefinitions(metadata: AnnotationMetadata,
                                       registry: BeanDefinitionRegistry) {

    val scanner: ClassPathScanningCandidateComponentProvider = object : ClassPathScanningCandidateComponentProvider(
        false) {
      override fun isCandidateComponent(
          beanDefinition: AnnotatedBeanDefinition): Boolean {
        return (beanDefinition.metadata.isIndependent
            && !beanDefinition.metadata.isAnnotation)
      }
    }
    scanner.addIncludeFilter(AnnotationTypeFilter(RetrofitClient::class.java))
    scanner.findCandidateComponents(ClassUtils.getPackageName(metadata.className))
        .forEach { candidateComponent: BeanDefinition? ->
          val beanDefinition = candidateComponent as AnnotatedBeanDefinition
          val attributes = beanDefinition.metadata.getAnnotationAttributes(RetrofitClient::class.java.canonicalName)

          val retrofitClientFactoryBeanBuilder = BeanDefinitionBuilder
              .genericBeanDefinition(RetrofitClientFactoryBean::class.java)

          val beanClassName = beanDefinition.beanClassName
          retrofitClientFactoryBeanBuilder.addPropertyValue("type", beanClassName)
          val beanName = beanClassName!!.substringAfter("$").decapitalize()
          retrofitClientFactoryBeanBuilder.addPropertyValue("name", beanName)
          val clientName = if (attributes?.get("name") != "") attributes?.get("name") as String else beanName
          retrofitClientFactoryBeanBuilder.addPropertyValue("type", beanClassName)
          retrofitClientFactoryBeanBuilder.setLazyInit(true)

          registry.registerBeanDefinition(
              clientName,
              retrofitClientFactoryBeanBuilder.beanDefinition)
        }
  }
}
