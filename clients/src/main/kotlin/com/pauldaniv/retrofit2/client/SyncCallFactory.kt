package com.pauldaniv.retrofit2.client

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SyncCallFactory : CallAdapter.Factory() {
  override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
    return SyncCallAdapter<Any>(returnType)
  }
}
