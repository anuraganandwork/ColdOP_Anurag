package com.example.coldstorage.DataLayer.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ColdOpApi {

    @POST("/api/store-admin/register")
  suspend  fun registerStoreAdmin( @Body FormData: StoreAdminFormData):Response<StoreOwnerRegisteredDetails>



  @POST("/api/store-admin/send-otp")
  suspend fun sendOtpToStoreOwner( @Body MobileNum: String) : Response<sendOtpResponse>

  @POST("/api/store-admin/verify-mobile?")
  suspend fun verifyMobile(@Body credentials:verifyMobile ) : Response<sendOtpResponse>


//  @POST("/api/store-admin/register")
//  suspend fun registerStoreOwner(@Body storeOwnerData:StoreAdminFormData)  : Response<StoreOwnerRegisteredDetails>

}