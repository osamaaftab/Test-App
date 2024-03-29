package com.osamaaftab.filtering.di.module

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.osamaaftab.filtering.di.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [NetworkModule::class])
class ApiClientModule {


    @Provides
    @AppScope
    internal fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {

        IdlingRegistry
            .getInstance()
            .register(OkHttp3IdlingResource.create("okhttp", okHttpClient))
        return Retrofit.Builder()
            .baseUrl("https://testa-pp.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
