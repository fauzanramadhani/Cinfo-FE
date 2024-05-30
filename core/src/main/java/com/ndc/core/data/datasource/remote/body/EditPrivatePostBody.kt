package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class EditPrivatePostBody(

	@field:SerializedName("post_id")
	val postId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String
)
