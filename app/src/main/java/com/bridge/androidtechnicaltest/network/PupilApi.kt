package com.bridge.androidtechnicaltest.network

import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.db.PupilPostBody
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PupilApi {
    @GET("pupils")
    fun getPupils(@Query("page") page: Int = 1): Single<PupilList>

    @POST("pupils")
    fun addPupil(@Body pupil: PupilPostBody): Completable
}