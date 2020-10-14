package com.alexlepadatu.githubusers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.base.BaseFragment
import com.alexlepadatu.githubusers.domain.models.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment: BaseFragment() {
    companion object {
        const val EXTRA_ITEM = "EXTRA_ITEM"

        fun getInstance(item: User): UserDetailFragment {
            val fragment = UserDetailFragment()
            fragment.arguments = bundleOf(EXTRA_ITEM to item)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_user_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val user : User? = it.getParcelable(EXTRA_ITEM)

            user?.let {
                setUser(it)
            }
        }
    }

    private fun setUser(user: User) {
        Glide.with(requireContext())
            .load(user.avatarUrl)
            .into(imgAvatar)

        val setupValueLabel: (lbl: TextView, url: String) -> Unit = { lbl, url ->
            lbl.text = url
            lbl.setOnClickListener { openWebPage(url) }
        }

        lblLoginValue.text = user.login

        setupValueLabel(lblUrlValue, user.url)
        setupValueLabel(lblHtmlUrlValue, user.htmlUrl)
        setupValueLabel(lblFollowersUrlValue, user.followersUrl)
        setupValueLabel(lblOrganizationsUrlValue, user.organizationsUrl)
        setupValueLabel(lblReposUrlValue, user.reposUrl)
        setupValueLabel(lblEventsUrlValue, user.eventsUrl)
        setupValueLabel(lblReceivedEventsUrlValue, user.receivedEventsUrl)
    }
}