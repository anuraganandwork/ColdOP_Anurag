package com.example.coldstorage.DataLayer.Api.OutgoingData

data class OutgoingDataClassItem(
    val orderId: String,
    val variety: String,
    val bagUpdates: List<BagUpdate?>,

    )


data class MainOutgoingOrderClass(
    val remarks :String? = "No Remarks",
    val  orders: List<OutgoingDataClassItem>
)