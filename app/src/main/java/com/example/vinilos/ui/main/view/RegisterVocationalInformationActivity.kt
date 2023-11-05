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
import com.example.vinilos.data.model.RegisterCandidateEducationInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.RegisterEducationViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterEducationViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityRegisterVocationalInformationBinding

class RegisterVocationalInformationActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityRegisterVocationalInformationBinding
    private lateinit var mViewModel: RegisterEducationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterVocationalInformationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.educationTypeEt.onFocusChangeListener = this
        mBinding.levelEt.onFocusChangeListener = this
        mBinding.titleEt.onFocusChangeListener = this
        mBinding.gradeEt.onFocusChangeListener = this
        mBinding.institutionEt.onFocusChangeListener = this
        mBinding.startDateEt.onFocusChangeListener = this
        mBinding.endDateEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, RegisterEducationViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(RegisterEducationViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            //username, email, password
            val formErrorKeys = arrayOf(
                "typeEducation",
                "level",
                "title",
                "grade",
                "institution",
                "startDate",
                "endDate"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "typeEducation" -> {
                            mBinding.educationTypeTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "level" -> {
                            mBinding.levelTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "title" -> {
                            mBinding.titleTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "grade" -> {
                            mBinding.gradeTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "institution" -> {
                            mBinding.institutionTil.apply {
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
        mViewModel.getRegisterEducationInformation().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, CandidatePortalActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateTypeEducation(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.educationTypeEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Education Type is required"
        }

        if (errorMessage != null) {
            mBinding.educationTypeTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateLevel(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.levelEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Level is required"
        }

        if (errorMessage != null) {
            mBinding.levelTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateTitle(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.titleEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Title is required"
        }

        if (errorMessage != null) {
            mBinding.titleTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateGrade(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.gradeEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Grade is required"
        }

        if (errorMessage != null) {
            mBinding.gradeTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateInstitution(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.institutionEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Institution is required"
        }

        if (errorMessage != null) {
            mBinding.institutionTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
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
            errorMessage = "Start Date is required"
        }

        if (errorMessage != null) {
            mBinding.startDateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
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
            errorMessage = "End Date is required"
        }

        if (errorMessage != null) {
            mBinding.endDateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterVocationalInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.saveInformationBtn) {
            when (view.id) {
                R.id.saveInformationBtn -> {
                    onSubmit()
                }
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.educationTypeEt -> {
                    if (hasFocus) {
                        if (mBinding.educationTypeTil.isErrorEnabled) {
                            mBinding.educationTypeTil.isErrorEnabled = false
                        }
                    } else {
                        validateTypeEducation()
                    }
                }
                R.id.levelEt -> {
                    if (hasFocus) {
                        if (mBinding.levelTil.isErrorEnabled) {
                            mBinding.levelTil.isErrorEnabled = false
                        }
                    } else {
                        validateLevel()
                    }
                }
                R.id.titleEt -> {
                    if (hasFocus) {
                        if (mBinding.titleTil.isErrorEnabled) {
                            mBinding.titleTil.isErrorEnabled = false
                        }
                    } else {
                        validateTitle()
                    }
                }
                R.id.gradeEt -> {
                    if (hasFocus) {
                        if (mBinding.gradeTil.isErrorEnabled) {
                            mBinding.gradeTil.isErrorEnabled = false
                        }
                    } else {
                        validateGrade()
                    }
                }
                R.id.institutionEt -> {
                    if (hasFocus) {
                        if (mBinding.institutionTil.isErrorEnabled) {
                            mBinding.institutionTil.isErrorEnabled = false
                        }
                    } else {
                        validateInstitution()
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
            mViewModel.registerEducationInformation(
                RegisterCandidateEducationInformationBody(
                    mBinding.educationTypeEt.text!!.toString(),
                    mBinding.levelEt.text!!.toString(),
                    mBinding.titleEt.text!!.toString(),
                    //mBinding.gradeEt.text!!.toString(),
                    grade = false,
                    mBinding.institutionEt.text!!.toString(),
                    mBinding.startDateEt.text!!.toString(),
                    mBinding.endDateEt.text!!.toString(),
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateTypeEducation(shouldVibrateView = false)) isValid = false
        if (!validateLevel(shouldVibrateView = false)) isValid = false
        if (!validateTitle(shouldVibrateView = false)) isValid = false
        if (!validateGrade(shouldVibrateView = false)) isValid = false
        if (!validateInstitution(shouldVibrateView = false)) isValid = false
        if (!validateStartDate(shouldVibrateView = false)) isValid = false
        if (!validateEndDate(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}