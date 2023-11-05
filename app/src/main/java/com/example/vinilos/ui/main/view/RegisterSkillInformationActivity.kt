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
import com.example.vinilos.data.model.RegisterCandidateSkillInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.RegisterSkillViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterSkillViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityRegisterSkillInformationBinding

class RegisterSkillInformationActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityRegisterSkillInformationBinding
    private lateinit var mViewModel: RegisterSkillViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterSkillInformationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.skillEt.onFocusChangeListener = this
        mBinding.levelEt.onFocusChangeListener = this
        mBinding.experienceEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, RegisterSkillViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(RegisterSkillViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            val formErrorKeys = arrayOf("skill", "level", "experience")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "skill" -> {
                            mBinding.skillTil.apply {
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
                        "experience" -> {
                            mBinding.experienceTil.apply {
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
        mViewModel.getRegisterSkillInformation().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, CandidatePortalActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateSkill(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.skillEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Skill is required"
        }

        if (errorMessage != null) {
            mBinding.skillTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterSkillInformationActivity, this
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
                    this@RegisterSkillInformationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateExperience(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.experienceEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Experience is required"
        }

        if (errorMessage != null) {
            mBinding.experienceTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterSkillInformationActivity, this
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
                R.id.skillEt -> {
                    if (hasFocus) {
                        if (mBinding.skillTil.isErrorEnabled) {
                            mBinding.skillTil.isErrorEnabled = false
                        }
                    } else {
                        validateSkill()
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
                R.id.experienceEt -> {
                    if (hasFocus) {
                        if (mBinding.experienceTil.isErrorEnabled) {
                            mBinding.experienceTil.isErrorEnabled = false
                        }
                    } else {
                        validateExperience()
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
            mViewModel.registerSkillInformation(
                RegisterCandidateSkillInformationBody(
                    mBinding.skillEt.text!!.toString(),
                    mBinding.levelEt.inputType,
                    mBinding.experienceEt.text!!.toString()
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateSkill(shouldVibrateView = false)) isValid = false
        if (!validateLevel(shouldVibrateView = false)) isValid = false
        if (!validateExperience(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}