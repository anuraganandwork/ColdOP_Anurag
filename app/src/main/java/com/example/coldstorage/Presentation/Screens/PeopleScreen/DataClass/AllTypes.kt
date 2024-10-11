package com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass

data class AddressDetails(
    val floor:String,
    val row:String,

    val chamber :String,
)


data class StockDetails(
    val Ration:String,
    val Goli:String,
    val cutTok:String,
    val no12:String,
    val seed:String,
    val total:String
)

data class OtherDetails(
    val Variety:String,
    val Lotno:String,
    val marka:String
)
