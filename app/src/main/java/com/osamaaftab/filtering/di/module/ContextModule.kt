package com.osamaaftab.filtering.di.module

import android.content.Context
import com.osamaaftab.filtering.di.AppScope
import dagger.Module
import dagger.Provides


@Module
class ContextModule(var context: Context){

    @Provides
    @AppScope
    fun provideContext(): Context {
        return this.context
    }
}