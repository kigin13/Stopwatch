package com.timers.stopwatch.core.log

import timber.log.Timber

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 28.12.2022.
 */
fun error(tag: String, error: Throwable) {
    Timber.tag(tag).e(error)
}

inline fun error(tag: String, logMessage: () -> String) {
    Timber.tag(tag).e(logMessage.invoke())
}

inline fun error(tag: String, error: Throwable, logMessage: () -> String) {
    Timber.tag(tag).e(error, logMessage.invoke())
}

inline fun info(tag: String, logMessage: () -> String) {
    Timber.tag(tag).i(logMessage.invoke())
}

inline fun verbose(tag: String, logMessage: () -> String) {
    Timber.tag(tag).v(logMessage.invoke())
}

inline fun warn(tag: String, logMessage: () -> String) {
    Timber.tag(tag).w(logMessage.invoke())
}

inline fun debug(tag: String, logMessage: () -> String) {
    Timber.tag(tag).d(logMessage.invoke())
}

inline fun wtf(tag: String, logMessage: () -> String) {
    Timber.tag(tag).wtf(logMessage.invoke())
}
