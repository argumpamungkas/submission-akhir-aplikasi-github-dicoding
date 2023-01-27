package com.argumelar.aplikasigithubusernavigationdanapi

import android.app.Application
import com.argumelar.aplikasigithubusernavigationdanapi.network.moduleApiConfig
import com.argumelar.aplikasigithubusernavigationdanapi.network.moduleRepository
import com.argumelar.aplikasigithubusernavigationdanapi.persistence.moduleDatabase
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.moduleDetailActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.moduleDetailViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.ui.favorite.moduleFavoriteActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.favorite.moduleFavoriteViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.ui.followers.moduleFollowersFragment
import com.argumelar.aplikasigithubusernavigationdanapi.ui.followers.moduleFollowersViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.ui.following.moduleFollowingFragment
import com.argumelar.aplikasigithubusernavigationdanapi.ui.following.moduleFollowingViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.ui.home.moduleMainActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.home.moduleMainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AplikasiGithub: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AplikasiGithub)
            modules(
                moduleApiConfig,
                moduleDatabase,
                moduleRepository,
                moduleMainActivity,
                moduleMainViewModel,
                moduleDetailActivity,
                moduleDetailViewModel,
                moduleFollowersFragment,
                moduleFollowersViewModel,
                moduleFollowingFragment,
                moduleFollowingViewModel,
                moduleFavoriteActivity,
                moduleFavoriteViewModel
            )
        }
    }
}