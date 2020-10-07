package com.alexlepadatu.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alexlepadatu.githubusers.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainFragment : Fragment? = supportFragmentManager.findFragmentById(R.id.container)

        if (mainFragment == null) {
            mainFragment = MainFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }
}