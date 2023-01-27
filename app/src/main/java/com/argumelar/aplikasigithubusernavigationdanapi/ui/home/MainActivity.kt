package com.argumelar.aplikasigithubusernavigationdanapi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.argumelar.aplikasigithubusernavigationdanapi.R
import com.argumelar.aplikasigithubusernavigationdanapi.adapter.AdapterListUser
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.ActivityMainBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import com.argumelar.aplikasigithubusernavigationdanapi.ui.favorite.FavoriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleMainActivity = module {
    factory { MainActivity() }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.checkTheme()
        viewModel.tema.observe(this){
            if (it == 0) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        val adapter = AdapterListUser(arrayListOf(), object : AdapterListUser.OnAdapterListener {
            override fun onClick(listUser: ItemUsers) {
                DetailViewModel.USERNAME = listUser.username
                startActivity(Intent(this@MainActivity, DetailActivity::class.java))
            }
        })

        binding.rvListUser.adapter = adapter
        viewModel.listUser.observe(this) {
            if (it != null) {
                adapter.setData(it.items)
            }
        }

        viewModel.notUsers.observe(this) {
            if (it == true) {
                binding.llNotFound.visibility = View.VISIBLE
                adapter.clearData()
            } else {
                binding.llNotFound.visibility = View.GONE
            }
        }

        viewModel.message.observe(this) {
            binding.tvMessage.text = it
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    viewModel.searchUser(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val iconTheme = menu?.findItem(R.id.action_theme)
        iconTheme?.setOnMenuItemClickListener {
            viewModel.optionTheme()
            true
        }

        viewModel.tema.observe(this) {
            if (it == 0){
                iconTheme?.setIcon(R.drawable.ic_dark_theme)
            } else {
                iconTheme?.setIcon(R.drawable.ic_light_theme)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(loading: Boolean) {
        binding.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }
}