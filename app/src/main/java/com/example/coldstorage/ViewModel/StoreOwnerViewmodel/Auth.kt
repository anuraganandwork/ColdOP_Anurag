package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.FarmerData
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.DataLayer.Api.logInData
import com.example.coldstorage.DataLayer.Api.sendOtpResponse
import com.example.coldstorage.DataLayer.Auth.AuthManager
import com.example.coldstorage.DataLayer.Di.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val authIntercepter: AuthInterceptor,
    private val api: ColdOpApi
) : ViewModel() {


    var otpResponse by mutableStateOf<Response<sendOtpResponse>?>(null);
    private val _verificationResult = MutableLiveData<Boolean>()
    val verificationResult: LiveData<Boolean> = _verificationResult

    private val _logInStatus = mutableStateOf("");
    val logInStatus:MutableState<String> = _logInStatus
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
//                response.body()?.token?.let { token ->
//                    authManager.saveAuthToken(token)
//                    // Handle successful login
//                }
               }catch(e : Exception){
                Log.d("Error in registering", "Error in registering")
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun quickRegister(farmerData: FarmerData){
        viewModelScope.launch {
            try{
                 val resposne = api.quickRegister(farmerData)
                if(resposne.isSuccessful){
                Log.d("SuccessfullLOG","farmer added quikly"+resposne.body()?.status)}
                else{
                    val errorBody = resposne.errorBody()?.string()
                    Log.d("ErrorLOG", "Status code ${resposne.code()}, Else code Logged in: $errorBody")


                }
            } catch (e: Exception){
                Log.d("SuccessfullLOG"," NOOOOOOOO farmer added quikly"+e)

            }
        }



    }

    @SuppressLint("SuspiciousIndentation")
    fun  logInStoreOwner(logInData: logInData){
        viewModelScope.launch {
            try {
              val response=   api.logInStoreOwner(logInData)
                if(response.isSuccessful){
                   val token = response.body()?.data?.token
                    val store_id = response.body()?.data?._id//
                    Log.d("SuccessfullLOG", "Store id , sLogin successful, store id is. "+store_id)
                  authIntercepter.clearStore_id()
                    if (token != null) {
                        authIntercepter.saveToken(token)

                       Log.d("SuccessfullLOG", "Login successful, token saved.")
                    }
                    if(store_id!= null){
                        authIntercepter.saveStoreId(store_id);
                        Log.d("SuccessfullLOG", "Login successful, store_id saved!.")

                    }
                     _logInStatus.value = response.body()?.status!!;
                    Log.d("SuccessfullLOG", "Successfully logged in: ${_logInStatus.value}"+"fg${logInStatus.value}")

                    Log.d("SuccessfullLOG", "Successfully logged in: ${response.body()?.status}")
                    Log.d("SuccessfullLOG","Success Logged in"+response.body())}
                else{
                    val errorBody = response.errorBody()?.string()
                    Log.d("ErrorLOG", "Status code ${response.code()}, Else code Logged in: $errorBody")
            }
            }
            catch (e:Exception){
                Log.d("SuccessfullLOG", "falied in the viemodel")

            }
        }

    }


}

//1132

