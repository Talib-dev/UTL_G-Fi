package com.kura.utl.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_login
    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var fAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = FirebaseAuth.getInstance()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        initUI()

    }

    private fun initUI() {
        mBinding.tvSignUp.setOnClickListener {
            navigateToSignup()
        }

        mBinding.btLogin.setOnClickListener {
            login()
        }
    }

    fun login() {
        val email = mBinding.etEmailLogin.text.toString().trim()
        val password = mBinding.etPassLogin.text.toString().trim()
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                toast("Email is Required")
                return
            }
            if (TextUtils.isEmpty(password)) {
                toast("Password is Required.")
                return
            }
        } else if (password.length < 6) {
            toast("Password Must be >= 6 Characters")
            return
        } else {
            // authenticate the user
            fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        navigateToDashboard()
                    } else {
                        Log.d("LoginFragment", "onFailure: " + task.exception!!.message)
                        Toast.makeText(
                            context, "Error: " + task.exception?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }).addOnFailureListener(OnFailureListener { e ->
                    Log.d("LoginFragment", "onFailure: " + e.message)
                })
        }
    }

private fun navigateToSignup() {
    val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
    findNavController().navigate(action)
}

    private fun navigateToDashboard() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(action)
    }
}
