package com.ndc.core.data.domain

import com.ndc.core.data.datasource.remote.response.RoomResponse
import com.ndc.core.data.repository.RoomRepository
import javax.inject.Inject

class SaveRoomCacheUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke(roomResponse: RoomResponse) = roomRepository.saveRoomCache(roomResponse)
}