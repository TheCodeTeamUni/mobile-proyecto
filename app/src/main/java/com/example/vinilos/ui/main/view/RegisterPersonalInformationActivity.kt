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
import com.example.vinilos.data.model.RegisterCandidatePersonalInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.ui.main.viewmodel.RegisterPersonalInformationViewModel
import com.example.vinilos.ui.main.viewmodel.RegisterPersonalInformationViewModelFactory
import com.example.vinilos.utils.VibrateView
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityRegisterPersonalInformationBinding

class RegisterPersonalInformationActivity : AppCompatActivity(), View.OnClickListener,
    View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var mBinding: ActivityRegisterPersonalInformationBinding
    private lateinit var mViewModel: RegisterPersonalInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterPersonalInformationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.userNameEt.onFocusChangeListener = this
        mBinding.lastNameEt.onFocusChangeListener = this
        mBinding.documentTypeEt.onFocusChangeListener = this
        mBinding.documentEt.onFocusChangeListener = this
        mBinding.genderEt.onFocusChangeListener = this
        mBinding.telephoneEt.onFocusChangeListener = this
        mBinding.countryEt.onFocusChangeListener = this
        mBinding.birthDateEt.onFocusChangeListener = this
        mBinding.descriptionEt.onFocusChangeListener = this
        mBinding.photoEt.onFocusChangeListener = this
        mBinding.saveInformationBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(
            this,
            RegisterPersonalInformationViewModelFactory(
                RegisterInformationRepository(RetrofitClient.getService()),
                application
            )
        ).get(RegisterPersonalInformationViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this) {
            val formErrorKeys = arrayOf(
                "name",
                "lastName",
                "typeDocument",
                "document",
                "gender",
                "telephone",
                "country",
                "address",
                "birthdate",
                "description",
                "photo"
            )
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "name" -> {
                            mBinding.userNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "lastName" -> {
                            mBinding.lastNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "typeDocument" -> {
                            mBinding.documentTypeTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }


                        "document" -> {
                            mBinding.documentTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "gender" -> {
                            mBinding.genderTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "telephone" -> {
                            mBinding.telephoneTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "country" -> {
                            mBinding.countryTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "address" -> {
                            mBinding.addressTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "birthdate" -> {
                            mBinding.birthDateTil.apply {
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
                        "photo" -> {
                            mBinding.photoTil.apply {
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
        mViewModel.getRegisterPersonalInformation().observe(this) {
            if (it != null) {
                println("Esto imprime esto; " + it)
                startActivity(Intent(this, CandidatePortalActivity::class.java))
                Toast.makeText(applicationContext, "REGISTERED INFORMATION", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun validateName(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.userNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Name user is required"
        }

        if (errorMessage != null) {
            mBinding.userNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateLastName(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.lastNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Lastname user is required"
        }

        if (errorMessage != null) {
            mBinding.lastNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateTypeDocument(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.documentTypeEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Document type is required"
        }

        if (errorMessage != null) {
            mBinding.documentTypeTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateDocument(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.documentEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Document is required"
        }

        if (errorMessage != null) {
            mBinding.documentTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateGender(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.genderEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Gender is required"
        }

        if (errorMessage != null) {
            mBinding.genderTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }


    private fun validateTelephone(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.telephoneEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Telephone is required"
        }

        if (errorMessage != null) {
            mBinding.telephoneTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateCountry(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.countryEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Country is required"
        }

        if (errorMessage != null) {
            mBinding.countryTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateAddress(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.addressEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Address is required"
        }

        if (errorMessage != null) {
            mBinding.addressTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateBirthdate(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.birthDateEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Birthday is required"
        }

        if (errorMessage != null) {
            mBinding.birthDateTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validateDescription(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.descriptionEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Description is required"
        }

        if (errorMessage != null) {
            mBinding.descriptionTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
                )
            }
        }

        return errorMessage == null
    }

    private fun validatePhoto(
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.photoEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Photo is required"
        }

        if (errorMessage != null) {
            mBinding.photoTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(
                    this@RegisterPersonalInformationActivity,
                    this
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
                R.id.userNameEt -> {
                    if (hasFocus) {
                        if (mBinding.userNameTil.isErrorEnabled) {
                            mBinding.userNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateName()
                    }
                }
                R.id.lastNameEt -> {
                    if (hasFocus) {
                        if (mBinding.lastNameTil.isErrorEnabled) {
                            mBinding.lastNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateLastName()
                    }
                }
                R.id.documentTypeEt -> {
                    if (hasFocus) {
                        if (mBinding.documentTypeTil.isErrorEnabled) {
                            mBinding.documentTypeTil.isErrorEnabled = false
                        }
                    } else {
                        validateTypeDocument()
                    }
                }
                R.id.documentEt -> {
                    if (hasFocus) {
                        if (mBinding.documentTil.isErrorEnabled) {
                            mBinding.documentTil.isErrorEnabled = false
                        }
                    } else {
                        validateDocument()
                    }
                }
                R.id.genderEt -> {
                    if (hasFocus) {
                        if (mBinding.genderTil.isErrorEnabled) {
                            mBinding.genderTil.isErrorEnabled = false
                        }
                    } else {
                        validateGender()
                    }
                }
                R.id.telephoneEt -> {
                    if (hasFocus) {
                        if (mBinding.telephoneTil.isErrorEnabled) {
                            mBinding.telephoneTil.isErrorEnabled = false
                        }
                    } else {
                        validateTelephone()
                    }
                }
                R.id.countryEt -> {
                    if (hasFocus) {
                        if (mBinding.countryTil.isErrorEnabled) {
                            mBinding.countryTil.isErrorEnabled = false
                        }
                    } else {
                        validateCountry()
                    }
                }
                R.id.addressEt -> {
                    if (hasFocus) {
                        if (mBinding.addressTil.isErrorEnabled) {
                            mBinding.addressTil.isErrorEnabled = false
                        }
                    } else {
                        validateAddress()
                    }
                }
                R.id.birthDateEt -> {
                    if (hasFocus) {
                        if (mBinding.birthDateTil.isErrorEnabled) {
                            mBinding.birthDateTil.isErrorEnabled = false
                        }
                    } else {
                        validateBirthdate()
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
                R.id.photoEt -> {
                    if (hasFocus) {
                        if (mBinding.photoTil.isErrorEnabled) {
                            mBinding.photoTil.isErrorEnabled = false
                        }
                    } else {
                        validatePhoto()
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
            mViewModel.personalInformationRegister(
                RegisterCandidatePersonalInformationBody(
                    mBinding.userNameEt.text!!.toString(),
                    mBinding.lastNameEt.text!!.toString(),
                    mBinding.documentTypeEt.text!!.toString(),
                    mBinding.documentEt.text!!.toString(),
                    mBinding.genderEt.text!!.toString(),
                    mBinding.telephoneEt.text!!.toString(),
                    mBinding.countryEt.text!!.toString(),
                    mBinding.addressEt.text!!.toString(),
                    mBinding.birthDateEt.text!!.toString(),
                    mBinding.descriptionEt.text!!.toString(),
                    mBinding.photoEt.text!!.toString()
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateName(shouldVibrateView = false)) isValid = false
        if (!validateLastName(shouldVibrateView = false)) isValid = false
        if (!validateTypeDocument(shouldVibrateView = false)) isValid = false
        if (!validateDocument(shouldVibrateView = false)) isValid = false
        if (!validateGender(shouldVibrateView = false)) isValid = false
        if (!validateTelephone(shouldVibrateView = false)) isValid = false
        if (!validateCountry(shouldVibrateView = false)) isValid = false
        if (!validateAddress(shouldVibrateView = false)) isValid = false
        if (!validateBirthdate(shouldVibrateView = false)) isValid = false
        if (!validateDescription(shouldVibrateView = false)) isValid = false
        if (!validatePhoto(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}