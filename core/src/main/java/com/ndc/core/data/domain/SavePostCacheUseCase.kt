package com.ndc.core.data.domain

import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class SavePostCacheUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(post: PostGlobalResponse) = postRepository.savePostCache(post)
}