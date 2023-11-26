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
import com.example.vinilos.data.model.PerformanceEvaluationBody
import com.example.vinilos.data.model.RegisterResultTestBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.PerformanceEvaluationViewModel
import com.example.vinilos.ui.main.viewmodel.PerformanceEvaluationViewModelFactory
import com.example.vinilos.ui.main.viewmodel.RegisterResultTestViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterResultTestViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreatePerformanceEvaluationBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateResultTechnicalTestBinding
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CreateResultTechnicalTestActivity : AppCompatActivity() , View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityCreateResultTechnicalTestBinding
    private lateinit var mViewModel: RegisterResultTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateResultTechnicalTestBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.aspirantIdEt.onFocusChangeListener = this
        mBinding.testNameEt.onFocusChangeListener = this
        mBinding.testResultEt.onFocusChangeListener = this
        mBinding.testNotesEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, RegisterResultTestViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(RegisterResultTestViewModel::class.java)
        setupObservers()
    }
    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            //idAspirant, nameTest, date, result, notes
            val formErrorKeys = arrayOf(
                "idAspirant",
                "nameTest",
                "date",
                "result",
                "notes"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "idAspirant" -> {
                            mBinding.aspirantIdTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "nameTest" -> {
                            mBinding.testNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "result" -> {
                            mBinding.testResultTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "notes" -> {
                            mBinding.testNotesTil.apply {
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
        mViewModel.getRegisterResultTest().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, TestModuleActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateAspirantId(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.aspirantIdEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Aspirant ID is required"
        }

        if (errorMessage != null) {
            mBinding.aspirantIdTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateResultTechnicalTestActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateNameTest(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.testNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Test name is required"
        }

        if (errorMessage != null) {
            mBinding.testNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateResultTechnicalTestActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateResultTest(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.testResultEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Result Test is required"
        }

        if (errorMessage != null) {
            mBinding.testResultTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateResultTechnicalTestActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateTestNotes(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.testNotesEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Test Notes is required"
        }

        if (errorMessage != null) {
            mBinding.testNotesTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreateResultTechnicalTestActivity, this
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
                R.id.aspirantIdEt -> {
                    if (hasFocus) {
                        if (mBinding.aspirantIdTil.isErrorEnabled) {
                            mBinding.aspirantIdTil.isErrorEnabled = false
                        }
                    } else {
                        validateAspirantId()
                    }
                }
                R.id.testNameEt -> {
                    if (hasFocus) {
                        if (mBinding.testNameTil.isErrorEnabled) {
                            mBinding.testNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateNameTest()
                    }
                }
                R.id.testResultEt -> {
                    if (hasFocus) {
                        if (mBinding.testResultTil.isErrorEnabled) {
                            mBinding.testResultTil.isErrorEnabled = false
                        }
                    } else {
                        validateNameTest()
                    }
                }
                R.id.testNotesEt -> {
                    if (hasFocus) {
                        if (mBinding.testNotesTil.isErrorEnabled) {
                            mBinding.testNotesTil.isErrorEnabled = false
                        }
                    } else {
                        validateNameTest()
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
            mViewModel.registerResultTest(
                RegisterResultTestBody(
                    mBinding.aspirantIdEt.text!!.toString(),
                    mBinding.testNameEt.text!!.toString(),
                    //mBinding.dateEvaluationEt.text!!.toString(),
                    date = "12/12/2012 11:20",
                    mBinding.testResultEt.text!!.toString(),
                    mBinding.testNotesEt.text!!.toString()
                )
            )
        }
    }
    private fun validate(): Boolean {
        var isValid = true

        if (!validateAspirantId(shouldVibrateView = false)) isValid = false
        if (!validateNameTest(shouldVibrateView = false)) isValid = false
        if (!validateResultTest(shouldVibrateView = false)) isValid = false
        if (!validateTestNotes(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}