package com.ndc.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class RoomResponse(

	@field:SerializedName("room_name")
	val roomName: String,

	@field:SerializedName("createdAt")
	val createdAt: Long,

	@field:SerializedName("post_id")
	val postId: List<String>,

	@field:SerializedName("additional")
	val additional: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("client_offset")
	val clientOffset: Int
)
