package com.pauldaniv.retrofit2.clients

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class RetrofitClient(val name: String = "")
