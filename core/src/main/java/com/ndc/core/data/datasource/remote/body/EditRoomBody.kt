package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class EditRoomBody(

	@field:SerializedName("room_id")
	val roomId: String,

	@field:SerializedName("room_name")
	val roomName: String,

	@field:SerializedName("additional")
	val additional: String
)
