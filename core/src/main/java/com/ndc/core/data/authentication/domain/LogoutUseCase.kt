package com.ndc.core.data.authentication.domain

import com.ndc.core.data.authentication.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.logout()
}