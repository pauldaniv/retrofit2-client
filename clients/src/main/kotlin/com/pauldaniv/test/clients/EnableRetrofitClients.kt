package com.pauldaniv.test.clients

import org.springframework.context.annotation.Import
import kotlin.reflect.KClass


/**
 * Scans for interfaces that declare they are clients (via [ &lt;code&gt;@RetrofitClient&lt;/code&gt;][RetrofitClient]). Configures component scanning directives for use with
 * [ &lt;code&gt;@Configuration&lt;/code&gt;][org.springframework.context.annotation.Configuration] classes.
 *
 * @author Spencer Gibb
 * @author Dave Syer
 * @since 1.0
 */
//@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
//@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
//@MustBeDocumented
//@Import(RetrofitClientsRegistrar::class)
//annotation class EnableRetrofitClients
