package com.ndc.core.data.domain

import com.ndc.core.data.datasource.remote.response.PostPrivateResponse
import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class SavePostPrivateCacheUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(postPrivateResponse: PostPrivateResponse) =
        postRepository.savePostPrivateCache(postPrivateResponse)
}