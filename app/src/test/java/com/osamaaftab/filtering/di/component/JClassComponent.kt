package com.osamaaftab.filtering.di.component

import com.osamaaftab.filtering.InteractorTest
import com.osamaaftab.filtering.di.module.JClassModule
import dagger.Component

@Component(modules = [JClassModule::class])
interface JClassComponent {
    fun inject(interactorTest: InteractorTest)
}