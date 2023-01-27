package com.argumelar.aplikasigithubusernavigationdanapi.ui.followers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.argumelar.aplikasigithubusernavigationdanapi.adapter.AdapterListUser
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.FragmentFollowersBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleFollowersFragment = module {
    factory { FollowersFragment() }
}

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val viewModel : FollowersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterFollowers =
            AdapterListUser(arrayListOf(), object : AdapterListUser.OnAdapterListener {
                override fun onClick(listUser: ItemUsers) {
                    DetailViewModel.USERNAME = listUser.username
                    startActivity(Intent(requireContext(), DetailActivity::class.java))
                }
            })

        binding.rvFollowers.adapter = adapterFollowers
        viewModel.followers.observe(viewLifecycleOwner) {
            adapterFollowers.setData(it as ArrayList<ItemUsers>)
        }

        viewModel.notFollowers.observe(viewLifecycleOwner) {
            isNotFollower(it)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            binding.tvNotFollowers.text = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun isNotFollower(notFollowers: Boolean) {
        if (notFollowers) binding.tvNotFollowers.visibility = View.VISIBLE else View.GONE
    }

    private fun showLoading(loading: Boolean) {
        binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

}