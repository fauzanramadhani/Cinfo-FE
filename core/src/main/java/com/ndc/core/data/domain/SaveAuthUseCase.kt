package com.ndc.core.data.domain

import com.ndc.core.data.repository.AuthRepository
import javax.inject.Inject

class SaveAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String) = authRepository.saveAuth(email)
}