package com.kura.utl.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>() : AppCompatActivity() {

    lateinit var viewDataBinding: T

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()

    }

    fun getDataBinding() = viewDataBinding

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)

        viewDataBinding.executePendingBindings()
    }
}