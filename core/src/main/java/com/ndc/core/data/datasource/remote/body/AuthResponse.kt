package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class AuthResponse(
	@field:SerializedName("user_id")
	val userId: String
)
