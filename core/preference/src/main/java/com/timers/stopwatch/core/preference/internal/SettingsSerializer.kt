package com.timers.stopwatch.core.preference.internal

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.timers.stopwatch.core.preference.pb.Settings
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 29.11.2022.
 */
object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings {
        try {
            return Settings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Settings,
        output: OutputStream,
    ) = t.writeTo(output)
}
