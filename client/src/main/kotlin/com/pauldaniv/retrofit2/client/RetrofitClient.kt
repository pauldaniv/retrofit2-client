package com.pauldaniv.retrofit2.client

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class RetrofitClient(val value: String = "", val name: String = "")
