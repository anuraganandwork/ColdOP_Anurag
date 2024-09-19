package com.example.coldstorage.DataLayer.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

}

//1149