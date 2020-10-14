/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alexlepadatu.githubusers.users

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.domain.repository.NetworkState

class NetworkStateItemViewHolder(view: View,
                                 private val retryCallback: () -> Unit)
    : RecyclerView.ViewHolder(view) {
    private val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    private val retry = view.findViewById<Button>(R.id.btnRetry)
    private val errorMsg = view.findViewById<TextView>(R.id.lblErrorMsg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindData(networkState: NetworkState?) {
        progressBar.visibility = toVisibility(networkState is NetworkState.Loading)

        if (networkState is NetworkState.Failed) {
            retry.visibility = toVisibility(true)

            errorMsg.visibility = toVisibility(networkState.msg != null)
            errorMsg.text = networkState.msg
        }
        else {
            retry.visibility = toVisibility(false)
            errorMsg.visibility = toVisibility(false)
        }
    }

    companion object {

        fun toVisibility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
