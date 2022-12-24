package com.takeshi.gouda.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.takeshi.gouda.model.User
import com.takeshi.gouda.databinding.ItemListUserBinding

class UserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(55, 55))
            .into(holder.binding.imgUser)

        holder.binding.tvLogin.text = user.login
        holder.binding.tvName.text = user.html_url

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(var binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}