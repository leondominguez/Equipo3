package com.univalle.dogapp.view.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.univalle.dogapp.R
import com.univalle.dogapp.databinding.FragmentLoginBinding
import com.univalle.dogapp.viewmodel.LoginViewModel
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        if (loginViewModel.canAuthenticateWithBiometrics(requireContext())) {
            loginViewModel.setupBiometricPrompt(requireContext()) {
                stopFingerprintAnimation(binding.animationView)
                navigateToHome()
            }


            binding.animationView.setOnClickListener {
                startFingerprintAnimation(binding.animationView)
                loginViewModel.authenticateWithBiometrics()
            }
        } else {
            Toast.makeText(requireContext(), "Autenticación biométrica no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startFingerprintAnimation(animationView: LottieAnimationView) {
        animationView.playAnimation()
    }

    private fun stopFingerprintAnimation(animationView: LottieAnimationView) {
        animationView.cancelAnimation()
    }

    private fun navigateToHome() {
        Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_homeAdminCitasFragment2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}