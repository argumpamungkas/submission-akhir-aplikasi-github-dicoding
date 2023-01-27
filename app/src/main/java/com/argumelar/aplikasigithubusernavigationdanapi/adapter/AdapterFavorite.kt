package com.argumelar.aplikasigithubusernavigationdanapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.ListUserBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import com.bumptech.glide.Glide

class AdapterFavorite(
    private val userFav: ArrayList<UserDetailResponse>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

    class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userFav[position]
        holder.binding.tvName.text = user.username
        holder.binding.tvId.text = user.name
        Glide.with(holder.itemView)
            .load(user.avatar)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            listener.onClick(user)
        }

    }

    override fun getItemCount() = userFav.size

    fun setData(newList: List<UserDetailResponse>){
        userFav.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData(){
        userFav.clear()
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(listUser: UserDetailResponse)
    }
}