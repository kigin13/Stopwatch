package com.timers.stopwatch.core.common.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timers.stopwatch.core.common.android.navigation.NavigationCommand

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
abstract class StopwatchViewModel : ViewModel() {

    private val _navigationCommands = MutableLiveData<Event<NavigationCommand>>()
    internal val navigationCommands: LiveData<Event<NavigationCommand>>
        get() = _navigationCommands

    fun navigate(navigationCommand: NavigationCommand) {
        _navigationCommands.postValue(Event(navigationCommand))
    }
}
