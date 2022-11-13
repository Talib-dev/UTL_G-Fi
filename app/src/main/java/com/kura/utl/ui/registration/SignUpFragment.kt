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
import com.google.firebase.database.FirebaseDatabase
import com.kura.utl.R
import com.kura.utl.Utils.Utils
import com.kura.utl.databinding.FragmentSignUpBinding
import com.kura.utl.datalayer.modal.User
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_sign_up
    private lateinit var mBinding: FragmentSignUpBinding

    private lateinit var fAuth: FirebaseAuth
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("UTL_G-Fi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = FirebaseAuth.getInstance()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.btLogin.setOnClickListener {
            navigateToLogin()
        }
        mBinding.btSignUp.setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        val email: String = mBinding.etEmail.text.toString().trim()
        val password: String = mBinding.etPassword.text.toString().trim()
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
            mBinding.pdSignup.visibility=View.VISIBLE
            val user=User(fullName, email, password, phone, deviceID, 0)
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        fAuth.currentUser?.uid?.let {
                            addInDB(user , it)
                        } ?: run {
                            navigateToLogin()
                        }


                    } else {
                        mBinding.pdSignup.visibility=View.GONE

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

    private fun addInDB(user: User, userID: String) {
        myRef.child(userID).child(Utils.userInfo).setValue(user)
            .addOnSuccessListener {
                mBinding.pdSignup.visibility=View.GONE
                navigateToDeviceConfiguration(user.deviceID)
            }
            .addOnFailureListener {
                toast(it.message.toString())
               mBinding.pdSignup.visibility=View.GONE
            }

    }

    private fun navigateToLogin() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDeviceConfiguration(deviceID: String) {
        val action = SignUpFragmentDirections.actionSignUpFragmentToGetWiFiFragment(deviceID)
        findNavController().navigate(action)
    }

}