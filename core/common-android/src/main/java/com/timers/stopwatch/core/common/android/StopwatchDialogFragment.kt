package com.timers.stopwatch.core.common.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.timers.stopwatch.core.common.android.extensions.runNavigationCommand
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 15.1.2023.
 */
abstract class StopwatchDialogFragment<VB : ViewBinding, VM : StopwatchViewModel>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VB,
) : DialogFragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding: VB
        get() = checkNotNull(_binding)

    val isBindingNull
        get() = _binding == null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationCommands.observe(viewLifecycleOwner) { event ->
            // the navigationCommand should be handled only once
            val navigationCommand = event.getContentIfNotHandled() ?: return@observe

            handleNavigationCommands(navigationCommand)
        }
    }

    open fun handleNavigationCommands(navigationCommand: NavigationCommand) {
        findNavController().runNavigationCommand(navigationCommand)
    }

    open fun findNavController(): NavController {
        return NavHostFragment.findNavController(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
