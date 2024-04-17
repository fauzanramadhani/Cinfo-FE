package com.ndc.core.data.authentication.domain

import android.content.Intent
import com.ndc.core.data.authentication.repository.AuthRepository
import javax.inject.Inject

class HandleLoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(intent: Intent) = authRepository.handleSignInWithGoogle(intent)
}