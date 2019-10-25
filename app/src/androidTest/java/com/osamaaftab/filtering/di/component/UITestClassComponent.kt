package com.osamaaftab.filtering.di.component

import com.osamaaftab.filtering.MainActivityTest
import com.osamaaftab.filtering.di.AppScope
import com.osamaaftab.filtering.di.module.UITestClassModule
import dagger.Component


@AppScope
@Component(modules = [UITestClassModule::class])
interface UITestClassComponent {
    fun inject(mainActivityTest: MainActivityTest)
}