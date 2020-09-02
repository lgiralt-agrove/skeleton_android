package fr.devid.app.base

import android.content.pm.ActivityInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    open val orientation: Int
        get() = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT

    override fun onStart() {
        super.onStart()
        activity?.requestedOrientation = orientation
    }
}

fun Fragment.onBackPressedCallBackNavControllerOrParent(): OnBackPressedCallback =
    object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!findNavController().popBackStack()) {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
    }
