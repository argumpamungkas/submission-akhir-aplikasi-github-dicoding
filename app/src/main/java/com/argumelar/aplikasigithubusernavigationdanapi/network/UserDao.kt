package com.argumelar.aplikasigithubusernavigationdanapi.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse

@Dao
interface UserDao {

    @Query("SELECT * FROM user_favorite")
    fun findAll(): LiveData<List<UserDetailResponse>>

    @Query("SELECT COUNT(*) FROM user_favorite WHERE username=:username")
    suspend fun find(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: UserDetailResponse)

    @Delete
    suspend fun remove(user: UserDetailResponse)
}