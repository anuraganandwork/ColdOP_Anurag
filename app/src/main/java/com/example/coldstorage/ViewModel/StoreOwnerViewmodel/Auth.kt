package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.DataLayer.Api.sendOtpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val api: ColdOpApi
) : ViewModel() {


    var otpResponse by mutableStateOf<Response<sendOtpResponse>?>(null);
    private val _verificationResult = MutableLiveData<Boolean>()
    val verificationResult: LiveData<Boolean> = _verificationResult
    fun sendOtp(mobileNumber: String) {
        viewModelScope.launch {
            try {
                val response = api.sendOtpToStoreOwner(mobileNumber)
                otpResponse = response
            } catch (e: Exception) {
                // Handle error, e.g., log or show error message
                e.printStackTrace()
            }
        }
    }

    fun verifyMobile(mobileNumber: String, otp:String){
        viewModelScope.launch {
            try {
              val response=   api.verifyMobile(com.example.coldstorage.DataLayer.Api.verifyMobile(mobileNumber, otp))
               if(response.isSuccessful){
                    val responseBody = response.body()
                   if(responseBody?.status=="Success"){
                       _verificationResult.postValue(true)
                   }
                   else{
                       _verificationResult.postValue(false)
                   }
               } else{
                   _verificationResult.postValue(false)
               }
            } catch (e:Exception){
                _verificationResult.postValue(false)
            }
        }
    }


    fun registerStoreOwner(storeOwnerData: StoreAdminFormData){

        viewModelScope.launch {
            try{
                val response = api.registerStoreAdmin(storeOwnerData)
            }catch(e : Exception){
                Log.d("Error in registering", "Error in registering")
            }
        }
    }


}

//1132

