package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import androidx.compose.runtime.MutableState
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.BagSize
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Location
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order


data class  forSecondOutgoingPage(
    val dateOfSubmission : String,
    val voucherNum : Int ,
    val address : Location ,
    val bagsizeData : List<BagSize>
)
fun mapDataForSecondOutgoingPage(recipts: List<Order>) : MutableList<forSecondOutgoingPage> {

    val rows = mutableListOf<forSecondOutgoingPage>()

    recipts.forEach{order->
        order.orderDetails.forEach {
            rows.add(
                forSecondOutgoingPage(
                    dateOfSubmission = order.dateOfSubmission ,
                    voucherNum = order.voucher.voucherNumber,
                    address = it.location,
                    bagsizeData = it.bagSizes ))


        }


    }

    return rows
}