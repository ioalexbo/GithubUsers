package com.alexlepadatu.githubusers.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexlepadatu.githubusers.domain.models.User
import kotlinx.android.synthetic.main.cell_user.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val lblName: TextView = itemView.lblName

    fun bindData(user: User) {
        lblName.text = user.login
    }

    fun showLoading() {
        lblName.text = "Loading ..."        //TODO
    }
}