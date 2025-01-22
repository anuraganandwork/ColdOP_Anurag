package com.example.coldstorage.DataLayer.Api

import com.example.coldstorage.DataLayer.Api.GetRecieptNumberdata.RecieptNumData
import com.example.coldstorage.DataLayer.Api.OutgoingData.MainOutgoingOrderClass
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.ApiResponseDayBook
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.GetAllReciptResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.IncomingOrderResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.OutgoingApiCallResponse.OutgoingOrderApiResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.ResponseVariety.ResponseVariety
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary.ResponseStockSummary
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary.StockSummaryDetailedResponse
import com.example.coldstorage.DataLayer.Api.SearchFarmerData.SearchResultsData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ColdOpApi {

    @POST("/api/store-admin/register")
  suspend  fun registerStoreAdmin( @Body FormData: StoreAdminFormData):Response<StoreOwnerRegisteredDetails>



  @POST("/api/store-admin/send-otp")
  suspend fun sendOtpToStoreOwner( @Body MobileNum: String) : Response<sendOtpResponse>

  @POST("/api/store-admin/verify-mobile?")
  suspend fun verifyMobile(@Body credentials:verifyMobile ) : Response<sendOtpResponse>


//  @POST("/api/store-admin/register")
//  suspend fun registerStoreOwner(@Body storeOwnerData:StoreAdminFormData)  : Response<StoreOwnerRegisteredDetails>


  @POST("/api/store-admin/quick-register?=")
  suspend fun quickRegister(@Body farmerData:FarmerData) :Response<sendOtpResponse>


  @POST("api/store-admin/login?")
  suspend fun logInStoreOwner(@Body logInData: logInData):Response<StoreAdminResponse>


  @GET("api/store-admin/farmers")
  suspend fun getAllFarmers():Response<ListOfFarmersDataType>

  @GET("api/store-admin/farmers/{id}")
  suspend fun getSingleFarmer(@Path("id") farmerId:String):Response<singleFarmerClickData>



  @POST("api/store-admin/orders")
  suspend fun createIncomingOrder(@Body incomingOrderData: IncomingOrderData):Response<IncomingOrderResponse>

  @GET("api/store-admin/farmers/{id}/orders/incoming")
  suspend fun getOldReciepts(@Path("id") farmerId: String):Response<GetAllReciptResponse>



 @POST("api/store-admin/farmers/{id}/outgoing") //create new outgoing order
 suspend fun confirmOutgoingOrder(@Path("id") farmerId: String ,  @Body requestBody :  MainOutgoingOrderClass) : Response<OutgoingOrderApiResponse>


  @GET("api/store-admin/66e1f22d782bbd67d3446805/farmers/search")
  suspend fun searchFarmers(@Query("query") query: String): List<SearchResultsData>

  @GET("api/store-admin/receipt-number")
  suspend fun getRecieptNum():Response<RecieptNumData>

@GET("api/store-admin/daybook/orders")
  suspend fun getOrdersDayBook(
      @Query("type") type :String,
      @Query("sortBy") sortBy: String,
      @Query("page") page: Int,
      @Query("limit") limit: Int
  ):Response<ApiResponseDayBook>

@GET("api/store-admin/farmers/{id}/orders")
  suspend fun getSingleFarmerTransaction(@Path("id") farmerId: String):Response<ApiResponseDayBook>



  @GET("api/store-admin/farmers/{id}/stock-summary")
  suspend fun getStockSummary(@Path("id") farmerId: String):Response<ResponseStockSummary>

  @GET("api/store-admin/farmers/{id}/outgoing/varities")
  suspend fun getVarietyList(@Path("id") farmerId: String) : Response<ResponseVariety>

  @GET("api/store-admin/farmers/{id}/stock-summary")
  suspend fun getDetailedStockSummary(@Path("id") farmerId: String):Response<StockSummaryDetailedResponse>

}

//1149