package com.frand.crud_dasar.model

import com.google.gson.annotations.SerializedName

data class ResponseMock(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("complete")
	val complete: Boolean? = null,

	@field:SerializedName("content")
	val content: String? = null
)
