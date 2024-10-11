package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.BagSize
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Quantity


data class ReceiptRow(
    val voucherNumber: Int,
    val variety : String,
    val size :  List<BagSize> ,
//    val currentQuantity: Int ,
//    val initialQuantity: Int
)
fun mapReceiptsToRows(receipts: List<Order>): List<ReceiptRow> {
    val rows = mutableListOf<ReceiptRow>()

    receipts.forEach { receipt ->
        receipt.orderDetails.forEach { orderDetail ->
            rows.add(
                ReceiptRow(
                    voucherNumber = receipt.voucher.voucherNumber ?: 0,
                    variety = orderDetail.variety ?: "Unknown",
                    size = orderDetail.bagSizes,
//                    currentQuantity = bagSize.quantity.currentQuantity ,
//                    initialQuantity = bagSize.quantity.initialQuantity
                )
            )
        }
    }

    return rows
}