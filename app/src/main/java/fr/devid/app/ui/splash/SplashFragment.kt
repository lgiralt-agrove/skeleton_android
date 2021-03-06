package fr.devid.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.ui.login.LoginViewModel

class SplashFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = findNavController()

        subscribeUi(navController)

        return view
    }

    private fun subscribeUi(navController: NavController) {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, {
            when (it) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(
                    SplashFragmentDirections.actionSplashFragmentToMainFragment()
                )
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginGraph()
                )
                else -> {
                } // Nothing to do
            }
        })
    }
}
