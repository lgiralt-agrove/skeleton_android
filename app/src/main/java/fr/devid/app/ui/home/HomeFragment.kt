package fr.devid.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.navGraphViewModels
import fr.devid.app.R
import fr.devid.app.base.BaseFragment
import fr.devid.app.databinding.FragmentHomeBinding
import fr.devid.app.ui.login.LoginViewModel
import timber.log.Timber

class HomeFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by navGraphViewModels(R.id.tab_nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btPro.isVisible = true
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
        profileViewModel.profile.observe(viewLifecycleOwner, {
            Timber.d("Profile received: $it")
            binding.isPro = it.isPro
        })
        binding.btCredits.text = getString(R.string.credits_disponibles_d, 10)
    }
}
