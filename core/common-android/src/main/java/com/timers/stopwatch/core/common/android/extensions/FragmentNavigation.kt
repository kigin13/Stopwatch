package com.timers.stopwatch.core.common.android.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.12.2022.
 */
fun Fragment.findParentNavController(): NavController {
    val navHostFragment = parentNavHostFragment()

    return NavHostFragment.findNavController(navHostFragment)
}

fun Fragment.parentNavHostFragment() = requireParentFragment().requireParentFragment()
