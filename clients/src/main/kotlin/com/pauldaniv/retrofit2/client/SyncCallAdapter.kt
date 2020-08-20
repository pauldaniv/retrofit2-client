package com.pauldaniv.retrofit2.client

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class SyncCallAdapter<R>(private val type: Type) : CallAdapter<R, R> {
  override fun responseType() = type
  override fun adapt(call: Call<R>) = call.execute().body()!!
}
