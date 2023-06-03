package com.example.capstonengaksoro.data.response

import com.google.gson.annotations.SerializedName

data class ResponseNgaksoro(

    @field:SerializedName("images")
    val images: List<ImagesItem>
)

data class ImagesItem(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("text")
    val text: String
)
