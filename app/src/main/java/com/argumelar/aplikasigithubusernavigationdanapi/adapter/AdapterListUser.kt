package com.argumelar.aplikasigithubusernavigationdanapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.aplikasigithubusernavigationdanapi.databinding.ListUserBinding
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.bumptech.glide.Glide

class AdapterListUser(
    private val listUser: ArrayList<ItemUsers>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<AdapterListUser.ViewHolder>() {

    class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvName.text = user.username
        holder.binding.tvId.text = user.id.toString()
        Glide.with(holder.itemView)
            .load(user.avatar_url)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            listener.onClick(user)
        }
    }

    override fun getItemCount() = listUser.size

    fun setData(newList: List<ItemUsers>) {
        clearData()
        listUser.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData() {
        listUser.clear()
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(listUser: ItemUsers)
    }

}