package com.example.coldstorage.DataLayer.Api

import com.example.coldstorage.DataLayer.Api.GetRecieptNumberdata.RecieptNumData
import com.example.coldstorage.DataLayer.Api.OutgoingData.MainOutgoingOrderClass
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.ApiResponseDayBook
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.ApiResponseSingleOrder
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.ResponseAllFarmerIds
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.UpdateOrderRequest
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.GetAllReciptResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.IncomingOrderResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.OutgoingApiCallResponse.OutgoingOrderApiResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.ResponseVariety.ResponseVariety
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary.ResponseStockSummary
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary.StockSummaryDetailedResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StoreSummaryResponse.StockSummaryResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.VarietiesResponse
import com.example.coldstorage.DataLayer.Api.SearchFarmerData.SearchResultsData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ColdOpApi {

    @POST("/api/store-admin/register")
  suspend  fun registerStoreAdmin( @Body FormData: StoreAdminFormData):Response<StoreAdminResponse>



  @POST("/api/store-admin/send-otp")
  suspend fun sendOtpToStoreOwner( @Body sendOtpReq: sendOtpReq) : Response<sendOtpResponse>

  @POST("/api/store-admin/verify-mobile")
  suspend fun verifyMobile(@Body credentials: verifyMobiledata) : Response<sendOtpResponse>


//  @POST("/api/store-admin/register")
//  suspend fun registerStoreOwner(@Body storeOwnerData:StoreAdminFormData)  : Response<StoreOwnerRegisteredDetails>


  @POST("/api/store-admin/quick-register?=")
  suspend fun quickRegister(@Body farmerData: FarmerDataSave) :Response<sendOtpResponse>


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

  @GET("api/store-admin/varities")
  suspend fun getAllVarities():Response<VarietiesResponse>

 @POST("api/store-admin/farmers/{id}/outgoing") //create new outgoing order
 suspend fun confirmOutgoingOrder(@Path("id") farmerId: String ,  @Body requestBody :  MainOutgoingOrderClass) : Response<OutgoingOrderApiResponse>

 @PUT("api/store-admin/incoming-orders/{id}")
  suspend fun  editIncomingOrder(@Path("id") orderId:String , @Body requestBody : UpdateOrderRequest  ) : Response<Any>

  @GET("api/store-admin/orders/{id}/incoming")
  suspend fun  getSingleOrder(@Path("id") orderId: String) : Response <ApiResponseSingleOrder>
  @GET("api/store-admin/{storeId}/farmers/search")
  suspend fun searchFarmers(
      @Path("storeId") storeId :String,
      @Query("query") query: String): List<SearchResultsData>

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


  @GET("api/store-admin/farmerid/check")
  suspend fun getAllFarmerIds():Response<ResponseAllFarmerIds>


  @GET("api/store-admin/farmers/{id}/stock-summary")
  suspend fun getStockSummary(@Path("id") farmerId: String):Response<ResponseStockSummary>

  @GET("api/store-admin/farmers/{id}/outgoing/varities")
  suspend fun getVarietyList(@Path("id") farmerId: String) : Response<ResponseVariety>

  @GET("api/store-admin/farmers/{id}/stock-summary")
  suspend fun getDetailedStockSummary(@Path("id") farmerId: String):Response<StockSummaryDetailedResponse>


  @GET("api/store-admin/cold-storage-summary")
  suspend fun getColdStorageCapacitySummary(): Response<StockSummaryResponse>
}

//1149