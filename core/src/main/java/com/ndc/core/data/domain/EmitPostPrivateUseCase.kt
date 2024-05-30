package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class EmitPostPrivateUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(
        roomId: String,
        title: String,
        description: String
    ) = postRepository.emitCreatePostPrivate(roomId, title, description)
}