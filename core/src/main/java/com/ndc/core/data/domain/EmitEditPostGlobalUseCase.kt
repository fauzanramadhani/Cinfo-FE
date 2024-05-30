package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class EmitEditPostGlobalUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(
        postId: String,
        title: String,
        description: String
    ) = postRepository.emitEditPostGlobal(postId, title, description)
}