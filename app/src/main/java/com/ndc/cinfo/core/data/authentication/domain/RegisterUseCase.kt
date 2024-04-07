package com.ndc.cinfo.core.data.authentication.domain

import com.ndc.cinfo.core.data.authentication.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) = authRepository.register(email, password)
}