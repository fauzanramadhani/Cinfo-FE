package com.ndc.core.data.domain

import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class GetPostTypeCacheUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke() = postRepository.getPostType()
}