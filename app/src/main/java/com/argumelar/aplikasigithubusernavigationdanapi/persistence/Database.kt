package com.argumelar.aplikasigithubusernavigationdanapi.persistence

import android.app.Application
import androidx.room.Room
import com.argumelar.aplikasigithubusernavigationdanapi.network.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val moduleDatabase = module {
    single { database(androidApplication()) }
    single { userDB(get()) }
}

fun database(application: Application): DatabaseClient {
    return Room.databaseBuilder(
        application,
        DatabaseClient::class.java,
        "user.db"
    ).fallbackToDestructiveMigration()
        .build()
}

fun userDB(databaseClient: DatabaseClient): UserDao {
    return databaseClient.userDao
}