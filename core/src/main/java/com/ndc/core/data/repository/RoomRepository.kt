package com.ndc.core.data.repository

import com.ndc.core.data.constant.Event
import com.ndc.core.data.constant.Options
import com.ndc.core.data.datasource.remote.response.RoomResponse
import com.ndc.core.utils.SocketHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val mSocketHandler: SocketHandler
) {
    init {
        mSocketHandler.establishConnection()
    }

    fun observeRoom(): Flow<RoomResponse> =
        mSocketHandler.observe(Event.ROOM)

    fun updateRoomOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(Options.ROOM_OFFSET, offset)
    }
}