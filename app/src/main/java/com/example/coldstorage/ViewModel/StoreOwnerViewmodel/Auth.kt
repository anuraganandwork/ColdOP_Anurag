package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.FarmerDataSave
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.DataLayer.Api.logInData
import com.example.coldstorage.DataLayer.Api.sendOtpReq
import com.example.coldstorage.DataLayer.Api.sendOtpResponse
import com.example.coldstorage.DataLayer.Api.verifyMobiledata
import com.example.coldstorage.DataLayer.Di.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val authIntercepter: AuthInterceptor,
    private val api: ColdOpApi
) : ViewModel() {


    var otpResponse by mutableStateOf<Response<sendOtpResponse>?>(null);

    private val _logInStatus = MutableStateFlow<String>("")
    val logInStatus: StateFlow<String> = _logInStatus.asStateFlow()

    private val _isGetOtpLoading = MutableStateFlow<Boolean>(false)
    val isGetOtpLoading = _isGetOtpLoading.asStateFlow()

    private val _isOtpSent = MutableStateFlow<Boolean>(false)
    val isOtpSent = _isOtpSent.asStateFlow()


    fun sendOtp(mobileNumber: String) {
        viewModelScope.launch {
            _isGetOtpLoading.value = true
            try {
                Log.d("Otp route" , sendOtpReq(mobileNumber= mobileNumber).toString() )

                val response = api.sendOtpToStoreOwner(sendOtpReq(mobileNumber= mobileNumber))
                if(response.isSuccessful){
                    _isOtpSent.value = response.body()?.status == "Success"
                }
            Log.d("Otp route" , response.toString() + mobileNumber)
                otpResponse = response
            } catch (e: Exception) {
                Log.d("Otp error response" , e.toString())
                _isGetOtpLoading.value = false
                _isOtpSent.value = false

                // Handle error, e.g., log or show error message
                e.printStackTrace()
            }
            finally {
                _isGetOtpLoading.value = false
            }
        }
    }
    private val _verificationResult = MutableStateFlow<Boolean?>(null)
    val verificationResult: StateFlow<Boolean?> = _verificationResult

    private val _isVerifyingOtp = MutableStateFlow(false)
    val isVerifyingOtp: StateFlow<Boolean> = _isVerifyingOtp


    @SuppressLint("SuspiciousIndentation")
    fun verifyMobile(mobileNumber: String, otp: String) {
        if (mobileNumber.isEmpty() || otp.isEmpty()) {
            _verificationResult.value = false
            return
        }

        _isVerifyingOtp.value = true

        viewModelScope.launch {
            try {
                // Create request with correct parameter names
                val verifyRequest = verifyMobiledata(mobileNumber, otp)

                Log.d("Verification_request", "Sending verification for $mobileNumber with OTP $otp")

                val response = api.verifyMobile(verifyRequest)

                if (response.isSuccessful) {
                    _verificationResult.value = true
                    Log.d("Verification_success", "Mobile verified successfully: ${response.body()}")
                } else {
                    _verificationResult.value = false
                    Log.d("Verification_failure", "Code: ${response.code()}, Message: ${response.message()}")

                    // Log error body for debugging
                    response.errorBody()?.string()?.let {
                        Log.d("Verification_error_body", it)
                    }
                }
            } catch (e: Exception) {
                _verificationResult.value = false
                Log.e("Verification_exception", "Exception during verification: ${e.message}")
                e.printStackTrace()
            } finally {
                _isVerifyingOtp.value = false
            }
        }
    }

    fun resetVerification() {
        _verificationResult.value = null
    }

    private val _storeOwnerRegistrationLoader = MutableStateFlow<Boolean>(false)
    val storeOwnerRegistrationLoader: StateFlow<Boolean>  = _storeOwnerRegistrationLoader.asStateFlow()

    private val _storeOwnerRegistrationResult = MutableStateFlow<Boolean>(false)
    val storeOwnerRegistrationResult : StateFlow<Boolean> = _storeOwnerRegistrationResult.asStateFlow()

    private  val _storeRegistrationMessage = MutableStateFlow<String>("")
    val storeRegistrationMessage : StateFlow<String> = _storeRegistrationMessage.asStateFlow()

    fun registerStoreOwner(storeOwnerData: StoreAdminFormData){
        Log.d("Finish setupp" , storeOwnerData.toString())

        _storeOwnerRegistrationLoader.value = true
        viewModelScope.launch {

            try{
                val response = api.registerStoreAdmin(storeOwnerData)
                Log.d("Finish setupp" , response.body()?.message.toString())

                if(response.isSuccessful){
                    _storeOwnerRegistrationLoader.value = false
                    val token = response.body()?.data?.token
                    val store_id = response.body()?.data?._id//
                    Log.d("SuccessfullLOG", "Store id , sLogin successful, store id is. "+store_id)
                    authIntercepter.clearStore_id()
                    if (token != null) {
                        authIntercepter.saveToken(token)
                        _isLoggedIn.value = true
                        Log.d("SuccessfullLOG", "Login successful, token saved.")
                    }
                    if(store_id!= null){
                        authIntercepter.saveStoreId(store_id);
                        Log.d("SuccessfullLOG", "Login successful, store_id saved!.")

                    }
                    _logInStatus.value = "Success"
                    Log.d("SuccessfullLOG", "Successfully logged in: ${_logInStatus.value}"+"fg${logInStatus.value}")

                    Log.d("SuccessfullLOG", "Successfully logged in: ${response.body()?.status}")
                    Log.d("SuccessfullLOG","Success Logged in"+response.body())

                    if (response.body()?.status == "Success"){
                        _storeOwnerRegistrationResult.value = true
                    }else{
                        _storeOwnerRegistrationResult.value = false

                    }

                }else{
                    _storeOwnerRegistrationLoader.value = false
                    _storeOwnerRegistrationResult.value = false
                     _storeRegistrationMessage.value = response.body()?.message.toString()

                }
//                response.body()?.token?.let { token ->
//                    authManager.saveAuthToken(token)
//                    // Handle successful login
//                }
               }catch(e : Exception){
                _storeOwnerRegistrationLoader.value = false
                _storeOwnerRegistrationResult.value = false
                _storeRegistrationMessage.value = e.message.toString()


                Log.d("Error in registering", "Error in registering")
            } finally {
                _storeOwnerRegistrationLoader.value = false
                _storeOwnerRegistrationResult.value = false

            }
        }
    }

  private val _loadingAddFarmer = MutableStateFlow<Boolean>(false)
   val loadingAddFarmer:StateFlow<Boolean> = _loadingAddFarmer.asStateFlow()

    private val _farmerAddStatus = MutableStateFlow<Boolean>(false)
    val farmerAddStatus = _farmerAddStatus.asStateFlow()
    @SuppressLint("SuspiciousIndentation")
    fun quickRegister(farmerData: FarmerDataSave){
        viewModelScope.launch {
            _loadingAddFarmer.value = true
            try{
                Log.d("SuccessfullLOG" , farmerData.toString())

            val resposne = api.quickRegister(farmerData)
                if(resposne.isSuccessful){
                    _farmerAddStatus.value = true
                Log.d("SuccessfullLOG","farmer added quikly"+resposne.body()?.status)}
                else{
                    val errorBody = resposne.errorBody()?.string()
                    Log.d("ErrorLOG", "Status code ${resposne.code()}, Else code Logged in: $errorBody")


                }
            } catch (e: Exception){
                Log.d("SuccessfullLOG"," NOOOOOOOO farmer added quikly"+e)

            }finally {

                _loadingAddFarmer.value = false
            }
        }



    }
    fun resetAddFarmerStatus(){
        _farmerAddStatus.value= false
    }

    private  val _loadingLogIn = MutableStateFlow<Boolean>(false)
    val loadingLogIn: StateFlow<Boolean> = _loadingLogIn.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
//    private val _logInStatusLoader = MutableStateFlow<Boolean>(false)
//    val logInStatusLoader: StateFlow<Boolean> = _logInStatusLoader.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn :StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    init {

        _isLoggedIn.value = authIntercepter.isLoggedIn()
    }

    @SuppressLint("SuspiciousIndentation")
    fun  logInStoreOwner(logInData: logInData){
    if (_loadingLogIn.value) return

    viewModelScope.launch {
            try {
                _loadingLogIn.value = true
                _errorMessage.value = null // Clear any previous errors

                val response=   api.logInStoreOwner(logInData)
                if(response.isSuccessful){
                   val token = response.body()?.data?.token
                    val store_id = response.body()?.data?._id//
                    Log.d("SuccessfullLOG", "Store id , sLogin successful, store id is. "+store_id)
                  authIntercepter.clearStore_id()
                    if (token != null) {
                        authIntercepter.saveToken(token)
                        _isLoggedIn.value = true
                       Log.d("SuccessfullLOG", "Login successful, token saved.")
                    }
                    if(store_id!= null){
                        authIntercepter.saveStoreId(store_id);
                        Log.d("SuccessfullLOG", "Login successful, store_id saved!.")

                    }
                     _logInStatus.value = "Success"
                    Log.d("SuccessfullLOG", "Successfully logged in: ${_logInStatus.value}"+"fg${logInStatus.value}")

                    Log.d("SuccessfullLOG", "Successfully logged in: ${response.body()?.status}")
                    Log.d("SuccessfullLOG","Success Logged in"+response.body())}
                else{
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = errorBody ?: "Login failed. Please try again."

                    Log.d("ErrorLOG", "Status code ${response.code()}, Else code Logged in: $errorBody")
                    _logInStatus.value = "Fail"
            }
            }
            catch (e:Exception){
                _errorMessage.value = "An error occurred: ${e.message}"

                Log.d("SuccessfullLOG", "falied in the viemodel" +e)
                _logInStatus.value = "Fail"

            } finally {

                _loadingLogIn.value = false
            }
        }

    }

   fun logOutStoreOwner(){
       authIntercepter.clearLogInSession()
       _isLoggedIn.value = false
   }
    fun dismissError() {
        _errorMessage.value = null
    }


}

//1132

