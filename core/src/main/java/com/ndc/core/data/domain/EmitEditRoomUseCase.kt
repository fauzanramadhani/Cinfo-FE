package com.ndc.core.data.domain

import com.ndc.core.data.repository.RoomRepository
import javax.inject.Inject

class EmitEditRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke(
        roomId: String,
        roomName: String,
        additional: String
    ) = roomRepository.emitEditRoom(roomId, roomName, additional)
}