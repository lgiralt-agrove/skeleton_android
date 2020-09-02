package fr.devid.app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.devid.app.BuildConfig
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.base.onBackPressedCallBackNavControllerOrParent
import fr.devid.app.databinding.FragmentLoginBinding
import fr.devid.app.viewmodels.EventObserver

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        val navController = findNavController()
        bindUi(binding, navController)
        subscribeUi(binding, navController)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallBackNavControllerOrParent())
    }

    private fun bindUi(binding: FragmentLoginBinding, navController: NavController) {
        if (BuildConfig.DEBUG) {
            binding.etEmail.setText("bob@test.com")
            binding.etPassword.setText("Aa1234567!")
        }
        binding.btLogin.setOnClickListener {
            loginViewModel.login(binding.etEmail.text?.toString(), binding.etPassword.text?.toString())
        }
        binding.btRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment(false))
        }
        binding.mbForgotPassword.setOnClickListener {
        }
    }

    private fun subscribeUi(binding: FragmentLoginBinding, navController: NavController) {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, {
            if (it == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                navController.popBackStack()
            }
        })
        loginViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.isLoading = it
        })
        loginViewModel.loginState.observe(viewLifecycleOwner, EventObserver {
            val errorResource = when (it) {
                LoginViewModel.LoginState.FILL_FIELDS -> R.string.fill_all_fields
                LoginViewModel.LoginState.NO_INTERNET -> R.string.check_internet
                LoginViewModel.LoginState.NOT_ACTIVATED -> R.string.not_activated
                LoginViewModel.LoginState.WRONG_CREDENTIALS -> R.string.wrong_credentials
            }
            Toast.makeText(context, errorResource, Toast.LENGTH_LONG).show()
        })
    }
}
