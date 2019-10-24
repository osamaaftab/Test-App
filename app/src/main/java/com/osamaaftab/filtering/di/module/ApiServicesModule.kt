package com.osamaaftab.filtering.di.module

import com.osamaaftab.filtering.di.AppScope
import com.osamaaftab.filtering.repository.remote.UserServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module(includes = [ApiClientModule::class])
class ApiServicesModule{

    @Provides
    @AppScope
    fun provideUserService(retrofit: Retrofit): UserServices{
        return retrofit.create(UserServices::class.java)
    }
}