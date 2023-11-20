package com.timers.stopwatch.core.common.android.navigation

import androidx.navigation.NavDirections

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean) : NavigationCommand()
}
