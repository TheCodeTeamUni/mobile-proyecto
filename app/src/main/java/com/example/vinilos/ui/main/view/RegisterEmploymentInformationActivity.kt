package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.RetrofitClient
import com.example.vinilos.data.model.RegisterCandidateWorkExperienceInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.RegisterWorkExperienceViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterWorkExperienceViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityRegisterEmploymentInformationBinding

class RegisterEmploymentInformationActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityRegisterEmploymentInformationBinding
    private lateinit var mViewModel: RegisterWorkExperienceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterEmploymentInformationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.companyEt.onFocusChangeListener = this
        mBinding.positionEt.onFocusChangeListener = this
        mBinding.actualJobEt.onFocusChangeListener = this
        mBinding.startDateEt.onFocusChangeListener = this
        mBinding.endDateEt.onFocusChangeListener = this
        mBinding.saveWorkInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, RegisterWorkExperienceViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(RegisterWorkExperienceViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            //username, email, password
            val formErrorKeys = arrayOf("company", "position", "actualJob", "startDate", "endDate")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "company" -> {
                            mBinding.companyTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "position" -> {
                            mBinding.positionTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "actualJob" -> {
                            mBinding.actualJobTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "startDate" -> {
                            mBinding.startDateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "endDate" -> {
                            mBinding.endDateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }
                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this).setIcon(R.drawable.info_24).setTitle("INFORMATION")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }.show()
                }
            }
        }
        mViewModel.getRegisterWorkExperience().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, CandidatePortalActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateCompany(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.companyEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Company is required"
        }

        if (errorMessage != null) {
            mBinding.companyTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterEmploymentInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validatePosition(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.positionEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Position is required"
        }

        if (errorMessage != null) {
            mBinding.positionTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterEmploymentInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateActualJob(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.actualJobEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Actual Job is required"
        }

        if (errorMessage != null) {
            mBinding.actualJobTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterEmploymentInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateStartDate(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.startDateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Start Day is required"
        }

        if (errorMessage != null) {
            mBinding.startDateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterEmploymentInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateEndDate(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.endDateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "End Day is required"
        }

        if (errorMessage != null) {
            mBinding.endDateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterEmploymentInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.saveWorkInformationBtn) {
            when (view.id) {
                R.id.saveWorkInformationBtn -> {
                    onSubmit()
                }
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.companyEt -> {
                    if (hasFocus) {
                        if (mBinding.companyTil.isErrorEnabled) {
                            mBinding.companyTil.isErrorEnabled = false
                        }
                    } else {
                        validateCompany()
                    }
                }
                R.id.positionEt -> {
                    if (hasFocus) {
                        if (mBinding.positionTil.isErrorEnabled) {
                            mBinding.positionTil.isErrorEnabled = false
                        }
                    } else {
                        validatePosition()
                    }
                }
                R.id.actualJobEt -> {
                    if (hasFocus) {
                        if (mBinding.actualJobTil.isErrorEnabled) {
                            mBinding.actualJobTil.isErrorEnabled = false
                        }
                    } else {
                        validateActualJob()
                    }
                }
                R.id.startDateEt -> {
                    if (hasFocus) {
                        if (mBinding.startDateTil.isErrorEnabled) {
                            mBinding.startDateTil.isErrorEnabled = false
                        }
                    } else {
                        validateStartDate()
                    }
                }
                R.id.endDateEt -> {
                    if (hasFocus) {
                        if (mBinding.endDateTil.isErrorEnabled) {
                            mBinding.endDateTil.isErrorEnabled = false
                        }
                    } else {
                        validateEndDate()
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

    private fun onSubmit() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }
        if (validate()) {
            mViewModel.registerWorkExperience(
                RegisterCandidateWorkExperienceInformationBody(
                    mBinding.companyEt.text!!.toString(),
                    mBinding.positionEt.text!!.toString(),
                    mBinding.actualJobEt.text!!.toString(),
                    mBinding.startDateEt.text!!.toString(),
                    mBinding.endDateEt.text!!.toString(),
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateCompany(shouldVibrateView = false)) isValid = false
        if (!validatePosition(shouldVibrateView = false)) isValid = false
        if (!validateActualJob(shouldVibrateView = false)) isValid = false
        if (!validateStartDate(shouldVibrateView = false)) isValid = false
        if (!validateEndDate(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}