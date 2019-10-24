package com.osamaaftab.filtering.repository.remote

import com.osamaaftab.filtering.ui.model.FilterModel
import com.osamaaftab.filtering.ui.model.UserModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserServices {

    @GET("/api/v1/getAllUsers")
    abstract fun getUserList(): Single<UserModel>

    @POST("/api/v1/getFilteredUsers")
    abstract fun getUserFilterList(@Body filterModel: FilterModel): Single<UserModel>

}