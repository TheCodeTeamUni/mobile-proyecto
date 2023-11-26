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
import com.example.vinilos.data.model.CreateProjectBody
import com.example.vinilos.data.model.PerformanceEvaluationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.CreateProjectViewModel
import com.example.vinilos.ui.main.viewmodel.CreateProjectViewModelFactory
import com.example.vinilos.ui.main.viewmodel.PerformanceEvaluationViewModel
import com.example.vinilos.ui.main.viewmodel.PerformanceEvaluationViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreatePerformanceEvaluationBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateProjectBinding

class CreatePerformanceEvaluationActivity : AppCompatActivity() , View.OnClickListener,
    View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityCreatePerformanceEvaluationBinding
    private lateinit var mViewModel: PerformanceEvaluationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreatePerformanceEvaluationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.employeeIdEt.onFocusChangeListener = this
        mBinding.performanceEvaluationEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(
            this, PerformanceEvaluationViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()), application
            )
        ).get(PerformanceEvaluationViewModel::class.java)
        setupObservers()
    }
    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            //employeeName, performance
            val formErrorKeys = arrayOf(
                "employeeID",
                "performance"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "employeeID" -> {
                            mBinding.employeeIdTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "performance" -> {
                            mBinding.performanceEvaluationTil.apply {
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
        mViewModel.getPerformanceEvaluation().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, TestModuleActivity::class.java))
                Toast.makeText(applicationContext, "INFORMATION REGISTERED", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateEmployeeId(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.employeeIdEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Employee ID is required"
        }

        if (errorMessage != null) {
            mBinding.employeeIdTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreatePerformanceEvaluationActivity, this
                )
            }
        }

        return errorMessage == null
    }

    private fun validatePerformanceRating(
        shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.performanceEvaluationEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Performance Rating is required"
        }

        if (errorMessage != null) {
            mBinding.performanceEvaluationTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@CreatePerformanceEvaluationActivity, this
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
                R.id.employeeIdEt -> {
                    if (hasFocus) {
                        if (mBinding.employeeIdTil.isErrorEnabled) {
                            mBinding.employeeIdTil.isErrorEnabled = false
                        }
                    } else {
                        validateEmployeeId()
                    }
                }
                R.id.performanceEvaluationEt -> {
                    if (hasFocus) {
                        if (mBinding.performanceEvaluationTil.isErrorEnabled) {
                            mBinding.performanceEvaluationTil.isErrorEnabled = false
                        }
                    } else {
                        validatePerformanceRating()
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
            mViewModel.performanceEvaluation(
                PerformanceEvaluationBody(
                    mBinding.performanceEvaluationEt.text!!.toString()
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateEmployeeId(shouldVibrateView = false)) isValid = false
        if (!validatePerformanceRating(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}