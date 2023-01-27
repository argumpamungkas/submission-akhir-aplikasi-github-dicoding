package com.argumelar.aplikasigithubusernavigationdanapi.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.argumelar.aplikasigithubusernavigationdanapi.R
import com.argumelar.aplikasigithubusernavigationdanapi.adapter.SectionPagerAdapter
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleDetailActivity = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewModel.detailUser.observe(this) { user ->
            viewModel.find(user)
            binding.btnFavorite.setOnClickListener {
                viewModel.favorite(user)

                if (viewModel.isFavorite.value == 0){
                    Toast.makeText(this, "Add Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Unfavorite", Toast.LENGTH_SHORT).show()
                }
            }

            supportActionBar?.title = user.username
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(user.avatar)
                    .into(photoProfile)

                if (user.name.isNullOrEmpty()) tvName.visibility = View.GONE
                if (user.bio.isNullOrEmpty()) tvBio.visibility = View.GONE
                if (user.company.isNullOrEmpty()) tvCompany.visibility = View.GONE
                if (user.email.isNullOrEmpty()) tvEmail.visibility = View.GONE
                if (user.location.isNullOrEmpty()) tvLocation.visibility = View.GONE

                tvName.text = user.name
                tvUsername.text = user.username
                tvRepository.text = user.repos.toString()
                tvFollowers.text = user.followers.toString()
                tvFollowing.text = user.following.toString()
                tvBio.text = user.bio
                tvCompany.text = user.company
                tvEmail.text = user.email
                tvLocation.text = user.location

            }

        }

        viewModel.isFavorite.observe(this){
            if (it == 0){
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_unfavorite)
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite)
            }
            return@observe
        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }


        val sectionPager = SectionPagerAdapter(this)
        binding.apply {
            viewPager.adapter = sectionPager
            TabLayoutMediator(tabsLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(loading: Boolean){
        if (loading){
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }
}