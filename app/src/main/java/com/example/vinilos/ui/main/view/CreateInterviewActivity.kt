package com.example.vinilos.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.RetrofitClient
import com.example.vinilos.data.model.CreateInterviewBody
import com.example.vinilos.data.model.CreateProjectBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.CreateInterviewViewModel
import com.example.vinilos.ui.main.viewmodel.CreateInterviewViewModelFactory
import com.example.vinilos.ui.main.viewmodel.CreateProjectViewModel
import com.example.vinilos.ui.main.viewmodel.CreateProjectViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateInterviewBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateProjectBinding

class CreateInterviewActivity : AppCompatActivity() , View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityCreateInterviewBinding
    private lateinit var mViewModel: CreateInterviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCreateInterviewBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.projectNameEt.onFocusChangeListener = this
        mBinding.startDateEt.onFocusChangeListener = this
        mBinding.endDateEt.onFocusChangeListener = this
        mBinding.descriptionEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, CreateInterviewViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(CreateInterviewViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            //username, email, password
            val formErrorKeys = arrayOf(
                "nameProject",
                "startDate",
                "endDate",
                "description",
                "aspirants"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "nameProject" -> {
                            mBinding.projectNameTil.apply {
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
                        "description" -> {
                            mBinding.descriptionTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "aspirants" -> {
                            mBinding.aspirantTil.apply {
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
        mViewModel.getCreateInterview().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, InterviewModuleActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateProjectName(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.projectNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Project Name is required"
        }

        if (errorMessage != null) {
            mBinding.projectNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
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
                    this@CreateInterviewActivity, this
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
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateDescription(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.descriptionEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Project Description is required"
        }

        if (errorMessage != null) {
            mBinding.descriptionTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateAspirants(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.aspirantEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Aspirant is required"
        }

        if (errorMessage != null) {
            mBinding.aspirantTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
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
                R.id.projectNameEt -> {
                    if (hasFocus) {
                        if (mBinding.projectNameTil.isErrorEnabled) {
                            mBinding.projectNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateProjectName()
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
                R.id.descriptionEt -> {
                    if (hasFocus) {
                        if (mBinding.descriptionTil.isErrorEnabled) {
                            mBinding.descriptionTil.isErrorEnabled = false
                        }
                    } else {
                        validateDescription()
                    }
                }
                R.id.aspirantEt -> {
                    if (hasFocus) {
                        if (mBinding.aspirantTil.isErrorEnabled) {
                            mBinding.aspirantTil.isErrorEnabled = false
                        }
                    } else {
                        validateAspirants()
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
            mViewModel.createInterview(
                CreateInterviewBody(
                    mBinding.projectNameEt.text!!.toString(),
                    //projectName = "Default",
                    mBinding.startDateEt.text!!.toString(),
                    mBinding.endDateEt.text!!.toString(),
                    mBinding.descriptionEt.text!!.toString(),
                    mBinding.aspirantEt.inputType,
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateProjectName(shouldVibrateView = false)) isValid = false
        if (!validateStartDate(shouldVibrateView = false)) isValid = false
        if (!validateEndDate(shouldVibrateView = false)) isValid = false
        if (!validateDescription(shouldVibrateView = false)) isValid = false
        if (!validateAspirants(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}