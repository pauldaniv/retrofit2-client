package com.pauldaniv.retrofit2.client


/**
 * Scans for interfaces that declare they are clients (via [ &lt;code&gt;@RetrofitClient&lt;/code&gt;][RetrofitClient]). Configures component scanning directives for use with
 * [ &lt;code&gt;@Configuration&lt;/code&gt;][org.springframework.context.annotation.Configuration] classes.
 *
 * @author Spencer Gibb
 * @author Dave Syer
 * @since 1.0
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@MustBeDocumented
annotation class EnableRetrofitClients(
    /**
     * Base packages to scan for annotated components.
     *
     * [.value] is an alias for (and mutually exclusive with) this attribute.
     *
     * Use [.basePackageClasses] for a type-safe alternative to String-based
     * package names.
     *
     * @return the array of 'basePackages'.
     */
    val basePackages: Array<String> = []
)
