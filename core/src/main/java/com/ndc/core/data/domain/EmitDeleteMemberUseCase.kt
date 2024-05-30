package com.ndc.core.data.domain

import com.ndc.core.data.repository.MemberRepository
import javax.inject.Inject

class EmitDeleteMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(
        memberId: String,
        roomId: String
    ) = memberRepository.emitDeleteMember(memberId, roomId)
}