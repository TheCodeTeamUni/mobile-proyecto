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
import com.example.vinilos.data.model.AssignedCandidateBody
import com.example.vinilos.data.model.RegisterCandidateSkillInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.AssignedCandidateViewModel
import com.example.vinilos.ui.main.viewmodel.AssignedCandidateViewModelFactory
import com.example.vinilos.ui.main.viewmodel.RegisterSkillViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterSkillViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityAssignedCandidateBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityRegisterSkillInformationBinding

class AssignedCandidateActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityAssignedCandidateBinding
    private lateinit var mViewModel: AssignedCandidateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAssignedCandidateBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.idCandidateEt.onFocusChangeListener = this
        mBinding.nameCandidateEt.onFocusChangeListener = this
        mBinding.lastNameCandidateEt.onFocusChangeListener = this
        mBinding.rolCandidateEt.onFocusChangeListener = this
        mBinding.notesEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, AssignedCandidateViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(AssignedCandidateViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            val formErrorKeys = arrayOf("idUser", "name", "lastName", "role", "notes")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "idUser" -> {
                            mBinding.idCandidateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "name" -> {
                            mBinding.nameCandidateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "lastName" -> {
                            mBinding.lastNameCandidateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "role" -> {
                            mBinding.rolCandidateTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "notes" -> {
                            mBinding.notesTil.apply {
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
        mViewModel.getAssignedCandidate().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, ProjectModuleActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateCandidateId(
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
                    this@AssignedCandidateActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateCandidateName(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.nameCandidateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Candidate Name is required"
        }

        if (errorMessage != null) {
            mBinding.nameCandidateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@AssignedCandidateActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateCandidateLastName(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.lastNameCandidateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Candidate Last Name is required"
        }

        if (errorMessage != null) {
            mBinding.lastNameCandidateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@AssignedCandidateActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateCandidateRol(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.rolCandidateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Candidate rol is required"
        }

        if (errorMessage != null) {
            mBinding.rolCandidateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@AssignedCandidateActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateNotes(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.notesEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Notes is required"
        }

        if (errorMessage != null) {
            mBinding.notesTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@AssignedCandidateActivity, this
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
                R.id.idCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.idCandidateTil.isErrorEnabled) {
                            mBinding.idCandidateTil.isErrorEnabled = false
                        }
                    } else {
                        validateCandidateId()
                    }
                }
                R.id.nameCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.nameCandidateTil.isErrorEnabled) {
                            mBinding.nameCandidateTil.isErrorEnabled = false
                        }
                    } else {
                        validateCandidateName()
                    }
                }
                R.id.lastNameCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.lastNameCandidateTil.isErrorEnabled) {
                            mBinding.lastNameCandidateTil.isErrorEnabled = false
                        }
                    } else {
                        validateCandidateLastName()
                    }
                }
                R.id.rolCandidateEt -> {
                    if (hasFocus) {
                        if (mBinding.rolCandidateTil.isErrorEnabled) {
                            mBinding.rolCandidateTil.isErrorEnabled = false
                        }
                    } else {
                        validateCandidateRol()
                    }
                }
                R.id.notesEt -> {
                    if (hasFocus) {
                        if (mBinding.notesTil.isErrorEnabled) {
                            mBinding.notesTil.isErrorEnabled = false
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
        try {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }
            if (validate()) {
                mViewModel.assignedCandidate(
                    AssignedCandidateBody(
                        mBinding.idCandidateEt.text!!.toString(),
                        mBinding.nameCandidateEt.text!!.toString(),
                        mBinding.lastNameCandidateEt.text!!.toString(),
                        mBinding.rolCandidateEt.text!!.toString(),
                        mBinding.notesEt.text!!.toString()
                    )
                )
            }
        } catch (ex: Exception){
            Toast.makeText(applicationContext, "User already register", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateCandidateId(shouldVibrateView = false)) isValid = false
        if (!validateCandidateName(shouldVibrateView = false)) isValid = false
        if (!validateCandidateLastName(shouldVibrateView = false)) isValid = false
        if (!validateCandidateRol(shouldVibrateView = false)) isValid = false
        if (!validateNotes(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}