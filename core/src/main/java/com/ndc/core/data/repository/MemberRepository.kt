package com.ndc.core.data.repository

import com.google.gson.Gson
import com.ndc.core.data.constant.Event
import com.ndc.core.data.constant.Options
import com.ndc.core.data.datasource.remote.body.AddMemberBody
import com.ndc.core.data.datasource.remote.body.DeleteMemberBody
import com.ndc.core.data.datasource.remote.response.MemberResponse
import com.ndc.core.utils.SocketHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val mSocketHandler: SocketHandler,
) {
    init {
        mSocketHandler.establishConnection()
    }

    fun observeMember(roomId: String): Flow<MemberResponse> {
        return mSocketHandler.observe(roomId + Event.MEMBER)
    }


    fun emitMember(
        roomId: String,
        email: String
    ): Flow<Unit> {
        val body = Gson().toJson(AddMemberBody(roomId, email))
        return mSocketHandler.emit(Event.ADD_MEMBER, body)
    }

    fun emitDeleteMember(
        memberId: String,
        roomId: String
    ): Flow<Unit> {
        val body = Gson().toJson(DeleteMemberBody(memberId, roomId))
        return mSocketHandler.emit(Event.DELETE_MEMBER, body)
    }

    fun observeDeleteMember(
        roomId: String
    ): Flow<String> =
        mSocketHandler.observe<String>(roomId + Event.ON_DELETE_MEMBER)

    fun updateMemberOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(Options.MEMBER_OFFSET, offset)
    }
}