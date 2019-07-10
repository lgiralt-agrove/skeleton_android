package fr.devid.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.devid.app.api.RegisterDto
import fr.devid.app.base.BaseFragment
import fr.devid.app.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var registerViewModel: RegisterViewModel

    private val args: RegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        bindUi(binding)
        subscribeUi(binding)

        return binding.root
    }

    private fun bindUi(binding: FragmentRegisterBinding) {
        binding.isProfessional = args.isProfessional
        binding.btRegister.setOnClickListener {
            registerViewModel.register(
                RegisterDto(binding.etEmail.text?.toString() ?: "",
                    binding.etPassword.text?.toString() ?: "",
                    binding.etPhone.text?.toString() ?: "",
                    binding.cbCgu.isChecked,
                    binding.etCompany.text?.toString() ?: ""),
                binding.etPasswordConfirm.text?.toString() ?: ""
            )
        }
    }

    private fun subscribeUi(binding: FragmentRegisterBinding) {
        registerViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            binding.isLoading = state == RegisterViewModel.RegisterViewModelState.LOADING
            if (state == RegisterViewModel.RegisterViewModelState.SUCCESS) {
                findNavController().popBackStack()
            }
        })
    }
}
