package com.kura.utl.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.kura.utl.ui.base.BaseActivity
import com.kura.utl.R
import com.kura.utl.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


    }
}