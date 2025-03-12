package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard

data class ApiResponseDayBook(
    val status: String,
    val message: String? = null,
    val data: List<OrderDaybook>
)

data class OrderDaybook(
    val voucher: VoucherDaybook,
    val _id: String,
    val coldStorageId: String,
    val farmerId: FarmerInfo,
    val dateOfSubmission: String? = null,
    val dateOfExtraction: String? = null,
    val remarks:String? = null,
    val currentStockAtThatTime:Int? = null,
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
    val location: String? = null, // Nullable
    val incomingOrder: IncomingOrderSnapOutgoing? = null, // Nullable
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
    val quantity: QuantityDaybook? = null, // Nullable
    val quantityRemoved: Int? = null
)

data class QuantityDaybook(
    val initialQuantity: Int,
    val currentQuantity: Int
)



data class IncomingOrderSnapOutgoing(
    val location: String,
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


data class ResponseAllFarmerIds(
    val status :String,
    val data : DataID?
)
data class DataID(
    val registeredFarmers: List<String>?
)