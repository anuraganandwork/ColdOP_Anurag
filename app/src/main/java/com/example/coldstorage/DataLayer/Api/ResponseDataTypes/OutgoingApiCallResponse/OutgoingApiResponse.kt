package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.OutgoingApiCallResponse


data class OutgoingOrderApiResponse(
    val message: String,
    val outgoingOrder: OutgoingOrderApi
)

data class OutgoingOrderApi(
    val coldStorageId: String,
    val farmerId: String,
    val voucher: VoucherOutgoingOrderApi,
    val dateOfExtraction: String,
    val orderDetails: List<OrderDetailOutgoingOrderApi>,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class VoucherOutgoingOrderApi(
    val type: String,
    val voucherNumber: Int
)

data class OrderDetailOutgoingOrderApi(
    val variety: String,
    val incomingOrder: IncomingOrderOutgoingOrderApi,
    val bagSizes: List<BagSizeOutgoingOrderApi>
)

data class IncomingOrderOutgoingOrderApi(
    val _id: String,
    val location: LocationOutgoingOrderApi,
    val voucher: VoucherOutgoingOrderApi,
    val incomingBagSizes: List<IncomingBagSizeOutgoingOrderApi>
)

data class LocationOutgoingOrderApi(
    val floor: String,
    val row: String,
    val chamber: String
)

data class IncomingBagSizeOutgoingOrderApi(
    val size: String,
    val currentQuantity: Int,
    val initialQuantity: Int,
    val _id: String
)

data class BagSizeOutgoingOrderApi(
    val size: String,
    val quantityRemoved: Int
)
