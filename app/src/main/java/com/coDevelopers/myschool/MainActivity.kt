package com.coDevelopers.myschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coDevelopers.myschool.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataBinding().testText.text="testing app"
    }
}