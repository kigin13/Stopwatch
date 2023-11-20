package com.timers.stopwatch.core.preference.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.timers.stopwatch.core.preference.internal.SettingsSerializer
import com.timers.stopwatch.core.preference.pb.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Patrice Mulindi email(mulindipatrice00@gmail.com) on 28.11.2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesSettingsDataStore(@ApplicationContext context: Context) = context.dataStoreSettings
}

private val Context.dataStoreSettings: DataStore<Settings> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer,
)
