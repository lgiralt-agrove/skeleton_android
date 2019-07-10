package fr.devid.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.ui.login.LoginViewModel
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = findNavController()

        subscribeUi(navController)

        return view
    }

    private fun subscribeUi(navController: NavController) {
        loginViewModel.authenticationSate.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToLoginGraph())
            }
        })
    }
}
