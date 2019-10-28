package com.osamaaftab.filtering.presenter

import com.osamaaftab.filtering.contractor.UserListContractor
import com.osamaaftab.filtering.ui.model.UserData
import javax.inject.Inject


class UserListPresenter @Inject constructor(
    var iView: UserListContractor.IView?,
    var interactor: UserListContractor.IIntractor
) : UserListContractor.IPresenter, UserListContractor.IIntractor.OnResponse {

    override fun onDistroy() {
        iView = null
        interactor.onDisposable()
    }

    override fun onAttemptFilterButton() {
        iView!!.onViewDialog()
    }

    override fun onApplyFilter(
        hasPhoto: Boolean, inContact: Boolean, isFav: Boolean, scoreStart: String,
        scoreEnd: String,
        ageStart: String,
        ageEnd: String,
        heightStart: String,
        heightEnd: String,
        selectedBound: String
    ) {
        iView!!.onHideDialog()
        iView!!.onShowProgress()
        interactor.getFilteredListDat(
            this,
            hasPhoto,
            inContact,
            isFav,
            scoreStart,
            scoreEnd,
            ageStart,
            ageEnd,
            heightStart,
            heightEnd,
            selectedBound
        )
    }

    override fun onResetFilter() {
        iView!!.onCreateDialog()
        iView!!.onHideDialog()
        iView!!.onShowProgress()
        interactor.getResetUserListData(this)
    }

    override fun onRefreshDataSuccess(list: ArrayList<UserData>) {
        iView!!.onHideProgress()
        iView!!.onUpdateUserListAdapter(list)
    }

    override fun onNoInternet() {
        iView!!.onHideProgress()
        iView!!.onShowNoInternetError()
    }

    override fun onInitSuccess(list: ArrayList<UserData>) {
        iView!!.onHideProgress()
        iView!!.onsetUserListAdapter(list)
    }

    override fun onFetchUserList() {
        iView!!.onCreateDialog()
        iView!!.onShowProgress()
        interactor.getInitialUserListData(this)
    }
}