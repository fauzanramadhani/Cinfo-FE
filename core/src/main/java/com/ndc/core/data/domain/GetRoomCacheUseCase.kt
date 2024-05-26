package com.ndc.core.data.domain

import com.ndc.core.data.repository.RoomRepository
import javax.inject.Inject

class GetRoomCacheUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke() = roomRepository.getRoomCache()
}