package com.kura.utl.ui.registration

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.kura.utl.R
import com.kura.utl.databinding.FragmentSignUpBinding
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.datalayer.modal.User
import com.kura.utl.ui.login.LoginFragmentDirections


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_sign_up
    private lateinit var mBinding: FragmentSignUpBinding

    private lateinit var fAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = FirebaseAuth.getInstance()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
    }

    fun registration() {
        val email: String = mBinding.etEmail.text.toString().trim()
        val password: String = mBinding.etPassLogin.text.toString().trim()
        val phone: String = mBinding.etMobile.text.toString().trim()
        val fullName: String = mBinding.etFullName.text.toString().trim()
        val deviceID: String = mBinding.etDeviceId.text.toString().trim()



        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email)
            || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)
        ) {
            if (TextUtils.isEmpty(fullName)) {
                toast("please enter your name")
            }
            if (TextUtils.isEmpty(phone)) {
                toast("please enter your mobile number")
            }
            if (TextUtils.isEmpty(email)) {
                toast("please enter your email address")
            }
            if (TextUtils.isEmpty(password)) {
                toast("please enter password")
            }
        } else if (password.length < 6) {
            toast("Password Must be >= 6 Characters")
        } else {
            User(fullName, email, password, phone, deviceID, 0)
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        fAuth.currentUser?.uid?.let {
                            addInDB(
                                User(fullName, email, password, phone, deviceID, 0), it
                            )
                        }?:run{
                            navigateToLogin()
                        }


                    } else {
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                "Error ! " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }
    }

    private fun addInDB(user: User, userID: String?) {

    }

    private fun navigateToLogin() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}