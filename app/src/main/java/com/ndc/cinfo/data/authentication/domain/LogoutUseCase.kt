package com.ndc.cinfo.data.authentication.domain

import com.ndc.cinfo.data.authentication.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.logout()
}