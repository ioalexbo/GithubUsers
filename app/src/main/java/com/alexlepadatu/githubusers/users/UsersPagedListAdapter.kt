package com.alexlepadatu.githubusers.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.domain.models.User

class UsersPagedListAdapter : PagedListAdapter<User, UserViewHolder>(userDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        if (user != null) {
            holder.bindData(user)
        }
        else {
            holder.showLoading()
        }
    }

    //TODO show loading

    companion object {
        private val userDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}