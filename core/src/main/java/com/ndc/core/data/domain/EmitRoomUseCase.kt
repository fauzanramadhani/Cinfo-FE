package com.ndc.core.data.domain

import com.ndc.core.data.repository.RoomRepository
import javax.inject.Inject

class EmitRoomUseCase @Inject constructor(
   private val roomRepository: RoomRepository
) {
    operator fun invoke(
        roomName: String,
        additional: String,
    ) = roomRepository.emitRoom(
        roomName,
        additional
    )
}