package com.argumelar.aplikasigithubusernavigationdanapi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import com.argumelar.aplikasigithubusernavigationdanapi.network.UserDao

@Database(
    entities = [UserDetailResponse::class],
    version = 1,
    exportSchema = false
)

abstract class DatabaseClient: RoomDatabase() {
    abstract val userDao: UserDao
}