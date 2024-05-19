package com.ndc.core.data.domain

import com.ndc.core.data.repository.RoomRepository
import javax.inject.Inject

class UpdateRoomOffsetUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke(offset: String) = roomRepository.updateRoomOffset(offset)
}