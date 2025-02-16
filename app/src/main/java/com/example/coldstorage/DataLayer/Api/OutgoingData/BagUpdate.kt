package com.example.coldstorage.DataLayer.Api.OutgoingData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class BagUpdate(
    val size: String ,
    val quantityToRemove: Int,
)