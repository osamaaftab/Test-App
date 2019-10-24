package com.osamaaftab.filtering.di.module.presenter

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import com.osamaaftab.filtering.Interactor.UserListInteractor
import com.osamaaftab.filtering.contractor.UserListContractor
import com.osamaaftab.filtering.di.AppScope
import com.osamaaftab.filtering.di.module.ApiServicesModule
import com.osamaaftab.filtering.di.module.ContextModule
import com.osamaaftab.filtering.repository.remote.UserServices
import com.osamaaftab.filtering.ui.adapter.UserListAdapter
import com.osamaaftab.filtering.ui.model.FilterModel
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module(includes = [ApiServicesModule::class, ContextModule::class])
class UserListPresenterModule(var iView: UserListContractor.IView) {


    @Provides
    @AppScope
    fun provideView(): UserListContractor.IView {
        return iView
    }


    @Provides
    @AppScope
    fun provideInteractor(userServices: UserServices): UserListContractor.IIntractor {
        return UserListInteractor(userServices, FilterModel(), CompositeDisposable())
    }


    @Provides
    @AppScope
    fun provideAdapter(context: Context): UserListAdapter {
        return UserListAdapter(context)
    }


    @Provides
    @AppScope
    fun provideDialog(context: Context): Dialog {
        return Dialog(context)
    }


    @Provides
    @AppScope
    fun provideProgress(context: Context): ProgressDialog {
        return ProgressDialog(context)
    }
}