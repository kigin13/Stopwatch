package com.timers.stopwatch.core.common.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.timers.stopwatch.core.common.android.extensions.runNavigationCommand
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
abstract class StopwatchFragment<VB : ViewBinding, VM : StopwatchViewModel>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VB,
) : Fragment() {
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

    open fun onRestart() {
    }

    fun repeatOnLifecycleState(state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                block()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
