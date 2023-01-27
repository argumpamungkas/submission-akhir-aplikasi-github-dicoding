package com.argumelar.aplikasigithubusernavigationdanapi.ui.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.argumelar.aplikasigithubusernavigationdanapi.adapter.AdapterListUser
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.FragmentFollowingBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailActivity
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleFollowingFragment = module {
    factory { FollowingFragment() }
}

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val viewModel : FollowingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterFollowing =
            AdapterListUser(arrayListOf(), object : AdapterListUser.OnAdapterListener {
                override fun onClick(listUser: ItemUsers) {
                    DetailViewModel.USERNAME = listUser.username
                    startActivity(Intent(requireContext(), DetailActivity::class.java))
                }
            })

        binding.rvFollowing.adapter = adapterFollowing
        viewModel.following.observe(viewLifecycleOwner) {
            adapterFollowing.setData(it as ArrayList<ItemUsers>)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.notFollowing.observe(viewLifecycleOwner) {
            isNotFollowing(it)
        }

        viewModel.messageFollowing.observe(viewLifecycleOwner) {
            binding.tvNotFollowing.text = it.toString()
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isNotFollowing(notFollowing: Boolean) {
        if (notFollowing) binding.tvNotFollowing.visibility = View.VISIBLE else View.GONE
    }

}