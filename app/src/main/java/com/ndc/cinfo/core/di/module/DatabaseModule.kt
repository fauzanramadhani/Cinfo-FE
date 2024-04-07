package com.ndc.cinfo.core.di.module

import android.content.Context
import com.ndc.cinfo.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context,
    ) = SharedPreferencesManager(context)
}