package com.osamaaftab.filtering.contractor

import com.osamaaftab.filtering.ui.model.UserData

class UserListContractor {

    interface IPresenter {
        fun onFetchUserList()
        fun onResetFilter()
        fun onDistroy()
        fun onApplyFilter(
            hasPhoto: Boolean,
            inContact: Boolean,
            isFav: Boolean,
            scoreStart: String,
            scoreEnd: String,
            ageStart: String,
            ageEnd: String,
            heightStart: String,
            heightEnd: String,
            selectedBound: String
        )
        fun onAttemptFilterButton()
    }

    interface IView  {
        fun onsetUserListAdapter(list: ArrayList<UserData>)
        fun onShowNoInternetError()
        fun onViewDialog()
        fun onHideDialog()
        fun onCreateDialog()
        fun onShowProgress()
        fun onHideProgress()
        fun onUpdateUserListAdapter(list: ArrayList<UserData>)
    }

    interface IIntractor {
        fun getInitialUserListData(response: OnResponse)
        fun getResetUserListData(response: OnResponse)
        fun onDisposable()
        fun getFilteredListDat(
            response: OnResponse,
            hasPhoto: Boolean, inContact: Boolean, isFav: Boolean, scoreStart: String,
            scoreEnd: String,
            ageStart: String,
            ageEnd: String,
            heightStart: String,
            heightEnd: String,
            selectedBound: String
        )

        interface OnResponse {
            fun onInitSuccess(list: ArrayList<UserData>)
            fun onRefreshDataSuccess(list: ArrayList<UserData>)
            fun onNoInternet()
        }
    }
}