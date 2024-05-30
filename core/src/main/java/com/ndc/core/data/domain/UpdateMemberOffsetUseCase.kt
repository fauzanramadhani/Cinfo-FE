package com.ndc.core.data.domain

import com.ndc.core.data.repository.MemberRepository
import javax.inject.Inject

class UpdateMemberOffsetUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(offset: String) = memberRepository.updateMemberOffset(offset)
}