package fr.devid.app.ui.about

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import fr.devid.app.base.BaseFragment
import fr.devid.app.databinding.FragmentAboutBinding
import fr.devid.app.ui.login.LoginViewModel

class AboutFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
