package com.example.coldstorage.DataLayer.Api.OutgoingData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class OutgoingDataClassItem(
    val orderId: String,
    val variety: String,
    val bagUpdates: List<BagUpdate>,

    )

data class MainOutgoingOrderClass(
    val remarks :String,
    val  orders: List<OutgoingDataClassItem>
)