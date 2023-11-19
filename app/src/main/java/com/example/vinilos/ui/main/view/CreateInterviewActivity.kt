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
import com.example.vinilos.data.model.CreateInterviewBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.CreateInterviewViewModel
import com.example.vinilos.ui.main.viewmodel.CreateInterviewViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateInterviewBinding

class CreateInterviewActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityCreateInterviewBinding
    private lateinit var mViewModel: CreateInterviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCreateInterviewBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.companyNameEt.onFocusChangeListener = this
        mBinding.idCandidateEt.onFocusChangeListener = this
        mBinding.candidateNameEt.onFocusChangeListener = this
        mBinding.lastNameCandidateInterviewEt.onFocusChangeListener = this
        mBinding.rolEt.onFocusChangeListener = this
        mBinding.dateInterviewEt.onFocusChangeListener = this
        mBinding.notesInterviewEt.onFocusChangeListener = this
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
            //nameCompany, idAspirant, nameAspirant, lastNameAspirant, role, date, notes
            val formErrorKeys = arrayOf(
                "nameCompany",
                "idAspirant",
                "nameAspirant",
                "lastNameAspirant",
                "role",
                "date",
                "notes"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "nameCompany" -> {
                            mBinding.companyNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "idAspirant" -> {
                            mBinding.idCandidateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "nameAspirant" -> {
                            mBinding.candidateNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "lastNameAspirant" -> {
                            mBinding.lastNameCandidateInterviewTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "rol" -> {
                            mBinding.rolTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "date" -> {
                            mBinding.dateInterviewTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "notes" -> {
                            mBinding.notesInterviewTil.apply {
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

    private fun validateCompanyName(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.companyNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Company Name is required"
        }

        if (errorMessage != null) {
            mBinding.companyNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateIdCandidate(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.idCandidateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Id Candidate is required"
        }

        if (errorMessage != null) {
            mBinding.idCandidateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateCandidateName(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.candidateNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Candidate Name is required"
        }

        if (errorMessage != null) {
            mBinding.candidateNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateLastNameCandidate(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.lastNameCandidateInterviewEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Last Name Candidate is required"
        }

        if (errorMessage != null) {
            mBinding.lastNameCandidateInterviewTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateRol(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.rolEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Rol is required"
        }

        if (errorMessage != null) {
            mBinding.rolTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateInterviewDate(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.dateInterviewEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Date Interview is required"
        }

        if (errorMessage != null) {
            mBinding.dateInterviewTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateInterviewActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateNotes(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.notesInterviewEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Notes is required"
        }

        if (errorMessage != null) {
            mBinding.notesInterviewTil.apply {
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
                R.id.companyNameEt -> {
                    if (hasFocus) {
                        if (mBinding.companyNameTil.isErrorEnabled) {
                            mBinding.companyNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateCompanyName()
                    }
                }
                R.id.idCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.idCandidateTil.isErrorEnabled) {
                            mBinding.idCandidateTil.isErrorEnabled = false
                        }
                    } else {
                        validateIdCandidate()
                    }
                }
                R.id.nameCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.candidateNameTil.isErrorEnabled) {
                            mBinding.candidateNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateCandidateName()
                    }
                }
                R.id.lastNameCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.lastNameCandidateInterviewTil.isErrorEnabled) {
                            mBinding.lastNameCandidateInterviewTil.isErrorEnabled = false
                        }
                    } else {
                        validateLastNameCandidate()
                    }
                }
                R.id.rolEt -> {
                    if (hasFocus) {
                        if (mBinding.rolTil.isErrorEnabled) {
                            mBinding.rolTil.isErrorEnabled = false
                        }
                    } else {
                        validateRol()
                    }
                }
                R.id.dateInterviewEt -> {
                    if (hasFocus) {
                        if (mBinding.dateInterviewTil.isErrorEnabled) {
                            mBinding.dateInterviewTil.isErrorEnabled = false
                        }
                    } else {
                        validateInterviewDate()
                    }
                }
                R.id.notesEt -> {
                    if (hasFocus) {
                        if (mBinding.notesInterviewTil.isErrorEnabled) {
                            mBinding.notesInterviewTil.isErrorEnabled = false
                        }
                    } else {
                        validateNotes()
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
                    mBinding.companyNameEt.text!!.toString(),
                    mBinding.idCandidateEt.text!!.toString(),
                    mBinding.candidateNameEt.text!!.toString(),
                    mBinding.lastNameCandidateInterviewEt.text!!.toString(),
                    mBinding.rolEt.text!!.toString(),
                    mBinding.dateInterviewEt.text!!.toString(),
                    mBinding.notesInterviewEt.text!!.toString(),
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateCompanyName(shouldVibrateView = false)) isValid = false
        if (!validateIdCandidate(shouldVibrateView = false)) isValid = false
        if (!validateCandidateName(shouldVibrateView = false)) isValid = false
        if (!validateLastNameCandidate(shouldVibrateView = false)) isValid = false
        if (!validateRol(shouldVibrateView = false)) isValid = false
        if (!validateInterviewDate(shouldVibrateView = false)) isValid = false
        if (!validateNotes(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}