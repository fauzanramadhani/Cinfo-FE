package com.ndc.cinfo.core.di.module

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ndc.cinfo.BuildConfig
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.datasource.remote.contract.ApiService
import com.ndc.core.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideInterceptor(
        sharedPreferencesManager: SharedPreferencesManager
    ): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                "Bearer ${sharedPreferencesManager.getString(SharedPreferencesManager.USER_TOKEN)}"
            )
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    fun provideApiService(
        client: OkHttpClient,
        sharedPreferencesManager: SharedPreferencesManager
    ): ApiService {
        val getBaseUrl = sharedPreferencesManager.getString(SharedPref.SERVER_ADDRESS)
        val baseUrl = if (getBaseUrl.isNotEmpty()) "http://$getBaseUrl" else "http://192.168.2.24:3000"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideSignInGoogleClient(
        @ApplicationContext context: Context,
    ): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.DEFAULT_WEB_CLIENT_ID)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}