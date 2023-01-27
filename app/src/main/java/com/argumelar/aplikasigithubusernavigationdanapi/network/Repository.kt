package com.argumelar.aplikasigithubusernavigationdanapi.network

import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import com.argumelar.aplikasigithubusernavigationdanapi.model.UsersResponse
import org.koin.dsl.module

val moduleRepository = module {
    factory { Repository(get(), get()) }
}

class Repository(
    private val api: ApiService,
    val db: UserDao
) {

    suspend fun fetchSearchUser(q: String): UsersResponse {
        return api.searchUser(q)
    }

    suspend fun fetchDetailUser(username: String): UserDetailResponse {
        return api.detailUser(username)
    }

    suspend fun fetchFollowers(username: String): List<ItemUsers> {
        return api.followers(username)
    }

    suspend fun fetchFollowing(username: String): List<ItemUsers> {
        return api.following(username)
    }

    suspend fun find(user: UserDetailResponse) = db.find(user.username)

    suspend fun save(user: UserDetailResponse) {
        db.save(user)
    }

    suspend fun remove(user: UserDetailResponse) {
        db.remove(user)
    }

}