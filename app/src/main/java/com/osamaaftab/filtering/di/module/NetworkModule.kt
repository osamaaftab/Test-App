package com.osamaaftab.filtering.di.module

import com.osamaaftab.filtering.di.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@Module
class NetworkModule {


    @Provides
    @AppScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    }


    @Provides
    @AppScope
    fun provideLoggingInceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }
}