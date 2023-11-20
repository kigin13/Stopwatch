package com.newage.feature.pomodoro.useCase

object TimeManipulationUseCase {

    private const val MINUTES_OFFSET = 1

    fun increaseTime(currentHour: Int, currentMinute: Int): Pair<Int, Int> {
        var newHour = currentHour
        var newMinute = currentMinute + MINUTES_OFFSET

        if (newMinute >= 60) {
            newHour += newMinute / 60
            newMinute %= 60
        }

        newHour = if(newHour > 24) 23 else newHour

        return Pair(newHour, newMinute)
    }

    fun decreaseTime(currentHour: Int, currentMinute: Int): Pair<Int, Int> {
        var newHour = currentHour
        var newMinute = currentMinute - MINUTES_OFFSET

        if (newMinute >= 60) {
            newHour += newMinute / 60
            newMinute %= 60
        }

        while (newMinute < 0) {
            newHour--
            newMinute += 60
        }

        return Pair(newHour, newMinute)
    }
}