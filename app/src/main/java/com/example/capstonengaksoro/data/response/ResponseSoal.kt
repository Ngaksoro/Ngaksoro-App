package com.example.capstonengaksoro.data.response

import com.google.gson.annotations.SerializedName

data class ResponseSoalItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("opsi")
	val opsi: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jawaban")
	val jawaban: String
)
