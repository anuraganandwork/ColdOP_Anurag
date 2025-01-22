package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.util.Log
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.BagSize
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Location
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order


data class  SelectedCellData(
    val orderId: String,
    val voucherNumber: Int,
    val variety: String,
    val size: String?,
    val address: String,
    val dateOfSubmission: String,
    val currentQuantity: String)

data class ReceiptRow(
    val orderId: String,
    val voucherNumber: Int,
    val variety: String,
    val size:  List<BagSize>,
    val address: String,
    val dateOfSubmission: String
//    val currentQuantity: Int ,
//    val initialQuantity: Int
)
fun mapReceiptsToRows(receipts: List<Order>): List<ReceiptRow> {
    val rows = mutableListOf<ReceiptRow>()
Log.d("OutgoingRR" , receipts.toString())
    receipts.forEach { receipt ->
        receipt.orderDetails.forEach { orderDetail ->

            rows.add(
                ReceiptRow(
                    orderId =receipt._id ,
                    voucherNumber = receipt.voucher.voucherNumber ?: 0,
                    variety = orderDetail.variety ?: "Unknown",
                    size = orderDetail.bagSizes,
                    address = orderDetail.location ,
                    dateOfSubmission =receipt.dateOfSubmission,


//                    currentQuantity = bagSize.quantity.currentQuantity ,
//                    initialQuantity = bagSize.quantity.initialQuantity
                )
            )
        }
    }

    return rows
}
