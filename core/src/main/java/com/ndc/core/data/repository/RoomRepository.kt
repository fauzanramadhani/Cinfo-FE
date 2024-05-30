package com.ndc.core.data.repository

import com.google.gson.Gson
import com.ndc.core.data.constant.Event
import com.ndc.core.data.constant.Options
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.datasource.remote.body.EditRoomBody
import com.ndc.core.data.datasource.remote.body.RoomBody
import com.ndc.core.data.datasource.remote.response.RoomResponse
import com.ndc.core.utils.SharedPreferencesManager
import com.ndc.core.utils.SocketHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val mSocketHandler: SocketHandler,
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    init {
        mSocketHandler.establishConnection()
    }

    fun emitRoom(
        roomName: String,
        additional: String,
    ): Flow<Unit> {
        val body = Gson().toJson(RoomBody(roomName, additional))
        return mSocketHandler.emit(Event.CREATE_ROOM, body)
    }

    fun observeRoom(): Flow<RoomResponse> =
        mSocketHandler.observe(Event.ROOM)

    fun updateRoomOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(Options.ROOM_OFFSET, offset)
    }

    fun emitEditRoom(
        roomId: String,
        roomName: String,
        additional: String
    ): Flow<Unit> {
        val body = Gson().toJson(EditRoomBody(roomId, roomName, additional))
        return mSocketHandler.emit(Event.EDIT_ROOM, body)
    }

    fun emitDeleteRoom(
        roomId: String
    ): Flow<Unit> {
        val body = Gson().toJson(mapOf("room_id" to roomId))
        return mSocketHandler.emit(Event.DELETE_ROOM, body)
    }

    fun observeDeleteRoom() =
        mSocketHandler.observe<String>(Event.ON_DELETE_ROOM)

    fun saveRoomCache(roomResponse: RoomResponse) {
        sharedPreferencesManager.apply {
            saveString(SharedPref.ROOM_ID, roomResponse.id)
            saveString(SharedPref.ROOM_NAME, roomResponse.roomName)
            saveString(SharedPref.ROOM_ADDITIONAL, roomResponse.additional)
            saveLong(SharedPref.ROOM_CREATED_AT, roomResponse.createdAt)
            saveInt(SharedPref.ROOM_BACKGROUND_ID, roomResponse.backgroundId)
            saveInt(SharedPref.ROOM_CLIENT_OFFSET, roomResponse.clientOffset)
        }
    }

    fun getRoomCache() = RoomResponse(
        id = sharedPreferencesManager.getString(SharedPref.ROOM_ID),
        roomName = sharedPreferencesManager.getString(SharedPref.ROOM_NAME),
        createdAt = sharedPreferencesManager.getLong(SharedPref.ROOM_CREATED_AT),
        additional = sharedPreferencesManager.getString(SharedPref.ROOM_ADDITIONAL),
        backgroundId = sharedPreferencesManager.getInt(SharedPref.ROOM_BACKGROUND_ID),
        clientOffset = sharedPreferencesManager.getInt(SharedPref.ROOM_CLIENT_OFFSET)
    )

}