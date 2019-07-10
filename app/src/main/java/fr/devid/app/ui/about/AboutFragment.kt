package fr.devid.app.ui.about

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import fr.devid.app.base.BaseFragment
import fr.devid.app.databinding.FragmentAboutBinding
import fr.devid.app.ui.login.LoginViewModel
import javax.inject.Inject

class AboutFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)

        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        bindUi(binding)

        return binding.root
    }

    private fun bindUi(binding: FragmentAboutBinding) {
        val context = requireContext()
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        binding.versionNumber = info.versionName

        binding.btLogout.setOnClickListener {
            loginViewModel.logout()
        }
    }
}
