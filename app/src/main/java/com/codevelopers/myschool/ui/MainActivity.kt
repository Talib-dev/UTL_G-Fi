package com.codevelopers.myschool.ui

import android.os.Bundle
import com.codevelopers.myschool.ui.base.BaseActivity
import com.codevelopers.myschool.R
import com.codevelopers.myschool.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}