package com.alexlepadatu.githubusers.base

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.alexlepadatu.githubusers.R

open class BaseFragment: Fragment() {

    protected fun addFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.nav_enter_right_to_left_anim,
                R.anim.nav_exit_right_to_left_anim,
                R.anim.nav_enter_left_to_right_anim,
                R.anim.nav_exit_left_to_right_anim)
            .replace(R.id.container, fragment)
            .addToBackStack(fragment::class.java.getSimpleName())
            .commit()
    }

    protected fun openWebPage(url: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val chooser = Intent.createChooser(browserIntent,null)

        if (browserIntent.resolveActivity(requireContext().packageManager) != null) {      // TODO show an error message
            startActivity(chooser)
        }
    }
}