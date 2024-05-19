package com.ndc.core.data.domain

import com.ndc.core.data.repository.ServerConfigRepository
import javax.inject.Inject

class UpdateServerAddressUseCase @Inject constructor(
    private val serverConfigRepository: ServerConfigRepository
) {
    operator fun invoke(address: String) = serverConfigRepository.updateServerAddress(address)
}