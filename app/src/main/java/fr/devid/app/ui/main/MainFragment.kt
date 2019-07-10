package fr.devid.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.ui.login.LoginViewModel
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)

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
        loginViewModel.authenticationSate.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.popBackStack()
                LoginViewModel.AuthenticationState.AUTHENTICATED -> Timber.d("User is authenticated")
            }
        })
    }
}
