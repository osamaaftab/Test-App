package com.osamaaftab.filtering.di.module

import com.osamaaftab.filtering.di.AppScope
import com.osamaaftab.filtering.util.Util
import dagger.Module
import dagger.Provides


@Module
class UITestClassModule {


    @AppScope
    @Provides
    fun provideUtilClass(): Util {
        return Util()
    }
}