package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class UpdateServerAddressUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(address: String) = postRepository.updateServerAddress(address)
}