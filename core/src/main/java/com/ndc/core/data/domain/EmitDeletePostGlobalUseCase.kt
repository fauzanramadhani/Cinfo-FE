package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class EmitDeletePostGlobalUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(
        id: String
    ) = postRepository.emitDeletePostGlobal(id)
}