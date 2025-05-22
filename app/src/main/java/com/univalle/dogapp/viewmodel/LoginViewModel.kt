package com.univalle.dogapp.viewmodel

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.core.content.ContextCompat

class LoginViewModel : ViewModel() {
    val biometricAuthResult = MutableLiveData<Boolean>() // se declara la variable para el resultado de la autenticación biométrica
    private lateinit var biometricPrompt: BiometricPrompt

    fun setupBiometricPrompt(context: Context, onAuthenticationSuccess: () -> Unit) {
        val executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(context as androidx.fragment.app.FragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    biometricAuthResult.postValue(true)
                    onAuthenticationSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    biometricAuthResult.postValue(false)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    biometricAuthResult.postValue(false)
                }
            })
    }

    fun canAuthenticateWithBiometrics(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticateWithBiometrics() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Inicia sesión usando tu huella digital o reconocimiento facial")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}