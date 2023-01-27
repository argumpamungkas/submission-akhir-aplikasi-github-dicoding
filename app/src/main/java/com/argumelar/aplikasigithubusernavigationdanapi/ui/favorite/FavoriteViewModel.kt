package com.argumelar.aplikasigithubusernavigationdanapi.ui.favorite

import androidx.lifecycle.ViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.network.Repository
import org.koin.dsl.module

val moduleFavoriteViewModel = module {
    factory { FavoriteViewModel(get()) }
}

class FavoriteViewModel(
    private val repository: Repository
): ViewModel() {

    val favorite = repository.db.findAll()
}