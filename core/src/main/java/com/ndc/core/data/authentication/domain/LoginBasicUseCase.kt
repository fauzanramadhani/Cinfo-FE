package com.ndc.core.data.authentication.domain

import com.ndc.core.data.authentication.repository.AuthRepository
import javax.inject.Inject

class LoginBasicUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) = authRepository.loginBasic(email, password)
}