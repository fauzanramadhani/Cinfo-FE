package com.ndc.core.data.domain

import com.ndc.core.data.repository.MemberRepository
import javax.inject.Inject

class ObserveMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun  invoke(roomId: String) = memberRepository.observeMember(roomId)
}