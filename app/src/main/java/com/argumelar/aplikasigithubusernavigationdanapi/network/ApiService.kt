package com.argumelar.aplikasigithubusernavigationdanapi.network

import com.argumelar.aplikasigithubusernavigationdanapi.BuildConfig
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import com.argumelar.aplikasigithubusernavigationdanapi.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token " + BuildConfig.API_KEY)
    suspend fun searchUser(
        @Query("q") q: String
    ): UsersResponse

    @GET("users/{username}")
    @Headers("Authorization: token" + BuildConfig.API_KEY)
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: token " + BuildConfig.API_KEY)
    suspend fun followers(
        @Path("username") username: String
    ): List<ItemUsers>

    @GET("users/{username}/following")
    @Headers("Authorization: token " + BuildConfig.API_KEY)
    suspend fun following(
        @Path("username") username: String
    ): List<ItemUsers>
}