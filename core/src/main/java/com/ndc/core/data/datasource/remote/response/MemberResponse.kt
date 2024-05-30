package com.ndc.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class MemberResponse(

	@field:SerializedName("room_id")
	val roomId: String,

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("client_offset")
	val clientOffset: Int,

	@field:SerializedName("email")
	val email: String
)
