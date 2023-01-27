package com.argumelar.aplikasigithubusernavigationdanapi.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.argumelar.aplikasigithubusernavigationdanapi.adapter.AdapterFavorite
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.ActivityFavoriteBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleFavoriteActivity = module {
    factory { FavoriteActivity() }
}

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        val adapter = AdapterFavorite(arrayListOf(), object : AdapterFavorite.OnAdapterListener {
            override fun onClick(listUser: UserDetailResponse) {
                DetailViewModel.USERNAME = listUser.username
                startActivity(Intent(this@FavoriteActivity, DetailActivity::class.java))
            }
        })

        binding.rvListFav.adapter = adapter
        viewModel.favorite.observe(this){
            if (it == null) {
                binding.llNotFound.visibility = View.VISIBLE
            } else {
                adapter.clearData()
                adapter.setData(it as ArrayList<UserDetailResponse>)
            }
        }
    }
}