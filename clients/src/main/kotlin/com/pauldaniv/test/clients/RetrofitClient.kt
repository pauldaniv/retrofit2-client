package com.pauldaniv.test.clients

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class RetrofitClient(val name: String = "")
