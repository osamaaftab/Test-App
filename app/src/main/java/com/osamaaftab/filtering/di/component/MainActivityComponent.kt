package com.osamaaftab.filtering.di.component

import com.osamaaftab.filtering.di.AppScope
import com.osamaaftab.filtering.di.module.presenter.UserListPresenterModule
import com.osamaaftab.filtering.ui.activity.MainActivity
import dagger.Component

@AppScope
@Component(modules = [UserListPresenterModule::class])
interface MainActivityComponents {
    fun inject(activity: MainActivity)
}