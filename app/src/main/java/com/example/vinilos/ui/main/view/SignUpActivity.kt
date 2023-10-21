package com.example.vinilos.ui.main.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.RegisterBody
import com.example.vinilos.data.ValidateEmailBody
import com.example.vinilos.data.api.RetrofitClient
import com.example.vinilos.data.repository.AuthRepository
import com.example.vinilos.ui.main.viewmodel.SignUpActivityViewModel
import com.example.vinilos.ui.main.viewmodel.SignUpActivityViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener, TextWatcher {

    private lateinit var mBinding: ActivitySignUpBinding
    private lateinit var mViewModel: SignUpActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.userNameEt.onFocusChangeListener = this
        mBinding.emailAddressEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.confirmPasswordEt.onFocusChangeListener = this
        mBinding.confirmPasswordEt.setOnKeyListener(this)
        mBinding.confirmPasswordEt.addTextChangedListener(this)
        mBinding.createUserBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(
            this,
            SignUpActivityViewModelFactory(AuthRepository(RetrofitClient.getService()), application)
        ).get(SignUpActivityViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getIsUniqueEmail().observe(this) {
            if (validateEmail(shouldUpdateView = false)) {
                if (it) {
                    mBinding.emailAddressTil.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                } /*else {
                    //mBinding.emailAddressTil.apply {
                      //  if (startIconDrawable != null) startIconDrawable = null
                        //isErrorEnabled = true
                        //error = "Email is already in use"
                    //}
                }*/
            }
        }

        mViewModel.getErrorMessage().observe(this) {
            //username, email, password
            val formErrorKeys = arrayOf("username", "email, 'password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "username" -> {
                            mBinding.userNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "email" -> {
                            mBinding.emailAddressTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "password" -> {
                            mBinding.passwordTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }
                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("INFORMATION")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }
        }
        mViewModel.getUser().observe(this) {
            if (it == null) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(applicationContext, "USUARIO CREADO", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateFullName(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.userNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Username is required"
        }

        if (errorMessage != null) {
            mBinding.userNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@SignUpActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validateEmail(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.emailAddressEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email address is invalid"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.emailAddressTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@SignUpActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }

    private fun validateConfirmPassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.confirmPasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
        } else if (value.length < 6) {
            errorMessage = "Confirm password must be 6 characters long"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.confirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val password = mBinding.passwordEt.text.toString()
        val confirmPassword = mBinding.confirmPasswordEt.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm password error"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.confirmPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }


    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.createUserBtn)
            onSubmit()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.userNameEt -> {
                    if (hasFocus) {
                        if (mBinding.userNameTil.isErrorEnabled) {
                            mBinding.userNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateFullName()
                    }
                }
                R.id.emailAddressEt -> {
                    if (hasFocus) {
                        if (mBinding.emailAddressTil.isErrorEnabled) {
                            mBinding.emailAddressTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateEmail()) {
                            mViewModel.validateEmailAddress(ValidateEmailBody(mBinding.emailAddressEt.text!!.toString()))
                        }
                    }
                }
                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && mBinding.confirmPasswordEt.text!!.isNotEmpty() && validateConfirmPassword() &&
                            validatePasswordAndConfirmPassword()
                        ) {
                            if (mBinding.confirmPasswordTil.isErrorEnabled) {
                                mBinding.confirmPasswordTil.isErrorEnabled = false
                            }
                            mBinding.confirmPasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
                R.id.confirmPasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.confirmPasswordTil.isErrorEnabled) {
                            mBinding.confirmPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.passwordTil.isErrorEnabled) {
                                mBinding.passwordTil.isErrorEnabled = false
                            }
                            mBinding.confirmPasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }

                }
            }
        }

    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP) {
            onSubmit()
        }
        return false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (validatePassword(shouldUpdateView = false) && validateConfirmPassword(shouldUpdateView = false) &&
            validatePasswordAndConfirmPassword(shouldUpdateView = false)
        ) {
            mBinding.confirmPasswordTil.apply {
                if (isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        } else {
            if (mBinding.confirmPasswordTil.startIconDrawable != null)
                mBinding.confirmPasswordTil.startIconDrawable = null
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    private fun onSubmit() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
            //Toast.makeText(applicationContext, "Usuario Creado", Toast.LENGTH_LONG).show()
        }
        if (validate()) {
            mViewModel.registerUser(
                RegisterBody(
                    mBinding.userNameEt.text!!.toString(),
                    mBinding.emailAddressEt.text!!.toString(),
                    mBinding.passwordEt.text!!.toString(),
                    type = "1"
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateFullName(shouldVibrateView = false)) isValid = false
        if (!validateEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false
        if (!validateConfirmPassword(shouldVibrateView = false)) isValid = false
        if (isValid && !validatePasswordAndConfirmPassword(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}