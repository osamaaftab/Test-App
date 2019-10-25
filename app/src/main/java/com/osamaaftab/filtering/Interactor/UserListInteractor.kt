package com.osamaaftab.filtering.Interactor

import android.os.AsyncTask
import com.osamaaftab.filtering.contractor.UserListContractor
import com.osamaaftab.filtering.repository.remote.UserServices
import com.osamaaftab.filtering.ui.model.FilterModel
import com.osamaaftab.filtering.ui.model.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable


class UserListInteractor @Inject constructor(
    var userServices: UserServices,
    var filterModel: FilterModel,
    var mDisposable: CompositeDisposable
) :
    UserListContractor.IIntractor {
    override fun onDisposable() {
        mDisposable.clear()
    }

    override fun getResetUserListData(response: UserListContractor.IIntractor.OnResponse) {
        mDisposable.add(
            userServices.getUserList()
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(list: UserModel) {
                        response.onRefreshDataSuccess(list.matches)
                    }

                    override fun onError(e: Throwable) {
                        response.onNoInternet()
                    }
                })
        )
    }

    override fun getFilteredListDat(
        response: UserListContractor.IIntractor.OnResponse,
        hasPhoto: Boolean, inContact: Boolean, isFav: Boolean, scoreStart: String,
        scoreEnd: String,
        ageStart: String,
        ageEnd: String,
        heightStart: String,
        heightEnd: String,
        selectedBound: String
    ) {
        filterModel.hasPhoto = hasPhoto
        filterModel.inContact = inContact
        filterModel.isFavourite = isFav
        filterModel.score_start = Integer.parseInt(scoreStart) / 100.0
        filterModel.score_end = Integer.parseInt(scoreEnd) / 100.0
        filterModel.age_start = Integer.parseInt(ageStart)
        filterModel.age_end = Integer.parseInt(ageEnd)
        filterModel.height_start = Integer.parseInt(heightStart)
        filterModel.height_end = Integer.parseInt(heightEnd)
        filterModel.distance = Integer.parseInt(selectedBound)
        filterModel.currentuser = "Sharon"

        mDisposable.add(
            userServices.getUserFilterList(filterModel)
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(list: UserModel) {
                        response.onRefreshDataSuccess(list.matches)
                    }

                    override fun onError(e: Throwable) {
                        response.onNoInternet()
                    }
                })
        )
    }


    override fun getInitialUserListData(response: UserListContractor.IIntractor.OnResponse) {
        mDisposable.add(
            userServices.getUserList()
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(list: UserModel) {
                        response.onInitSuccess(list.matches)
                    }

                    override fun onError(e: Throwable) {
                        response.onNoInternet()
                    }
                })
        )
    }
}