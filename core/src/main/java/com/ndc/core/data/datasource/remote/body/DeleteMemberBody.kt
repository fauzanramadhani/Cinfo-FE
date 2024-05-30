package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class DeleteMemberBody(

	@field:SerializedName("member_id")
	val memberId: String,

	@field:SerializedName("room_id")
	val roomId: String
)
