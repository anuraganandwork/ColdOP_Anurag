package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard

data class ApiResponseDayBook(
    val status: String,
    val data: List<OrderDaybook>
)

data class OrderDaybook(
    val voucher: VoucherDaybook,
    val _id: String,
    val coldStorageId: String,
    val farmerId: FarmerInfo,
    val dateOfSubmission: String? = null, // Nullable as some objects may use dateOfExtraction
    val dateOfExtraction: String? = null,
    val orderDetails: List<OrderDetailDaybook>
)

data class VoucherDaybook(
    val type: String,
    val voucherNumber: Int
)

data class FarmerInfo(
     val _id : String,
    val name :String

)

data class OrderDetailDaybook(
    val location: LocationDaybook? = null, // Nullable as some orders may not have location details
    val incomingOrder: IncomingOrderSnapOutgoing? = null, // Nullable as not all orders have this field
    val variety: String,
    val bagSizes: List<BagSizeDaybook>
)

data class LocationDaybook(
    val floor: String,
    val row: String,
    val chamber: String
)

data class BagSizeDaybook(
    val size: String,
    val quantity: QuantityDaybook? = null, // Nullable as some orders use quantityRemoved instead
    val quantityRemoved: Int? = null
)

data class QuantityDaybook(
    val initialQuantity: Int,
    val currentQuantity: Int
)

//data class IncomingOrderResponse(
//    val incomingOrder: IncomingOrder
//)

data class IncomingOrderSnapOutgoing(
    val location: LocationIncomingDaybook,
    val voucher: VoucherIncomingDaybook,
    val _id: String,
    val incomingBagSizes: List<IncomingBagSizeDaybook>
)

data class LocationIncomingDaybook(
    val floor: String,
    val row: String,
    val chamber: String
)

data class VoucherIncomingDaybook(
    val type: String,
    val voucherNumber: Int
)

data class IncomingBagSizeDaybook(
    val size: String,
    val currentQuantity: Int,
    val initialQuantity: Int,
    val _id: String
)
