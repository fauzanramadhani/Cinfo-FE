package com.ndc.cinfoadmin.ui.feature.eachroom

import com.ndc.core.data.datasource.remote.response.MemberResponse
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse

sealed interface EachRoomAction {
    data class OnScreenChange (
        val screen: Int
    ) : EachRoomAction
    data class OnPostPrivateClicked(
        val postPrivateResponse: PostPrivateResponse
    ) : EachRoomAction
    data object OnDeleteRoom : EachRoomAction
    data object OnReloadRoomCache : EachRoomAction
    data class OnMemberClicked(
        val member: MemberResponse
    ) : EachRoomAction
    data class OnShowSheet(
        val sheetType: SheetType
    ) : EachRoomAction
    data object OnEmitMember : EachRoomAction
    data object OnDeleteMember : EachRoomAction
    data class OnEmailEmitMemberValueChange(
        val value :String
    ) : EachRoomAction
}