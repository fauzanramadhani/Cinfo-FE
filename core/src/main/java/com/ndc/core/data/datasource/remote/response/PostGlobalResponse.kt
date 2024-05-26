package com.ndc.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class PostGlobalResponse(

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("client_offset")
	val clientOffset: Int
)
