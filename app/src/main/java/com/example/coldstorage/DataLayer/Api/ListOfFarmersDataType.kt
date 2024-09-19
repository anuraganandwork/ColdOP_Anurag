package com.example.coldstorage.DataLayer.Api

data class ListOfFarmersDataType(
    val populatedFarmers: List<PopulatedFarmer>,
    val status: String
)