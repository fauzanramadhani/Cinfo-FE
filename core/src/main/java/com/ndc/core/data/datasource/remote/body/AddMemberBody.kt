package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class AddMemberBody(

	@field:SerializedName("room_id")
	val roomId: String,

	@field:SerializedName("email")
	val email: String
)
