package com.ndc.core.data.domain

import com.ndc.core.data.post.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.repository.PostRepository
import javax.inject.Inject

class ObservePostGlobalUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(
        onSuccess: (data: PostGlobalResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) = postRepository.obServePostGlobal(onSuccess, onFailure)
}