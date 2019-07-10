package fr.devid.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.databinding.FragmentHomeBinding
import fr.devid.app.ui.login.LoginViewModel
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = findNavController()
        loginViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)
        profileViewModel = ViewModelProvider(navController.getViewModelStoreOwner(R.id.tab_nav_graph), viewModelFactory).get(ProfileViewModel::class.java)
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        bindUi(binding)
        subscribeUi(binding)

        return binding.root
    }

    private fun bindUi(binding: FragmentHomeBinding) {
        binding.ibLogout.setOnClickListener {
            loginViewModel.logout()
        }
    }

    private fun subscribeUi(binding: FragmentHomeBinding) {
        profileViewModel.profile.observe(viewLifecycleOwner, Observer {
            Timber.d("Profile received: $it")
            binding.isPro = it.isPro
        })
        binding.btCredits.text = getString(R.string.credits_disponibles_d, 10)
    }
}
