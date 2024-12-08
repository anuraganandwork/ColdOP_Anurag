package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.ResponseVariety

data class ResponseVariety(
    val status : String,
    val varieties:List<String>,
    val message :String? = null
)