package com.ndc.core.data.domain

import com.ndc.core.data.repository.MemberRepository
import javax.inject.Inject

class EmitMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(
        roomId: String,
        email: String
    ) = memberRepository.emitMember(roomId, email)
}