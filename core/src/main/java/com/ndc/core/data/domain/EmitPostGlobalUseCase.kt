package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class EmitPostGlobalUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(
        title: String,
        description: String
        ) = postRepository.emitUpdatePostGlobal(title, description)
}