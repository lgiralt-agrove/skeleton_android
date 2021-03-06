package fr.devid.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.ui.login.LoginViewModel
import timber.log.Timber

class MainFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navView = view.findViewById<View>(R.id.tab_nav_host_fragment)
        val tabNavController = Navigation.findNavController(navView)
        bottomNavigationView.setupWithNavController(tabNavController)

        val navController = findNavController()

        subscribeUi(navController)

        return view
    }

    private fun subscribeUi(navController: NavController) {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, {
            when (it) {
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.popBackStack()
                LoginViewModel.AuthenticationState.AUTHENTICATED -> Timber.d("User is authenticated")
                else -> {
                } // Nothing to do
            }
        })
    }
}
