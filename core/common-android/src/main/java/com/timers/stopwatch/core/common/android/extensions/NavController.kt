package com.timers.stopwatch.core.common.android.extensions

import androidx.navigation.NavController
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand
import com.timers.stopwatch.core.log.error

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
fun NavController.runNavigationCommand(navigationCommand: NavigationCommand) {
    when (navigationCommand) {
        NavigationCommand.Back -> popBackStack()
        is NavigationCommand.BackTo -> popBackStack(
            navigationCommand.destinationId,
            navigationCommand.inclusive,
        )
        is NavigationCommand.To -> {
            try {
                navigateTo(navigationCommand)
            } catch (e: IllegalArgumentException) {
                error("NavController", e)
                return
            }
        }
    }
}

private fun NavController.navigateTo(navigationCommand: NavigationCommand.To) {
    val action = currentDestination?.getAction(navigationCommand.directions.actionId)
    val nextDestination = action?.destinationId
    val currentDestination = currentDestination?.id

    // Action doesn't exist in current destination
    if (nextDestination == null) {
        return
    }

    if (currentDestination != nextDestination) {
        navigate(navigationCommand.directions)
    }
}
