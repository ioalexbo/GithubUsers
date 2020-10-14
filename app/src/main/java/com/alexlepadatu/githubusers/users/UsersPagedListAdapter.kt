package com.alexlepadatu.githubusers.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.domain.repository.NetworkState
import com.alexlepadatu.githubusers.domain.models.User

class UsersPagedListAdapter (
    private val retryCallback: () -> Unit,
    private val userSelectListener : (user: User) -> Unit
)
    : PagedListAdapter<User, RecyclerView.ViewHolder>(userDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.cell_user -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_user, parent, false)
                UserViewHolder(itemView, userSelectListener)
            }

            R.layout.cell_network_state -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_network_state, parent, false)
                NetworkStateItemViewHolder(itemView, retryCallback)
            }

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.cell_user -> {
                val user = getItem(position)
                holder as UserViewHolder

                if (user != null) {
                    holder.bindData(user)
                }
                else {
                    holder.showLoading()
                }
            }

            R.layout.cell_network_state -> (holder as NetworkStateItemViewHolder).bindData(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState !is NetworkState.Success

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.cell_network_state
        } else {
            R.layout.cell_user
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val PAYLOAD = Any()

        private val userDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: User, newItem: User): Any? {
                return if (areContentsTheSame(oldItem, newItem))
                    PAYLOAD
                else {
                    null
                }
            }
        }
    }
}