package com.kura.utl.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


abstract class BaseFragment<DataBinding : ViewDataBinding> : Fragment() {

    private lateinit var dataBinding: DataBinding
    private var mActivity: BaseActivity<*>? = null

    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View is created using layout Id
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return dataBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.mActivity = context

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel is set as Binding Variable
        dataBinding.apply {
            lifecycleOwner = this@BaseFragment
        }
    }

    fun getDataBinding() = dataBinding


//    open fun hideKeyboard() {
//        val view = activity?.currentFocus
//        mActivity?.hideKeyboard(view)
//    }
//
//    open fun showKeyboard() {
//        val view = activity?.currentFocus
//        mActivity?.showKeyboard(view)
//    }



    open fun handleBack() {
        findNavController().popBackStack()
    }
    open fun backTo(id: Int) {
        findNavController().popBackStack(id, false)
    }


    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }







}