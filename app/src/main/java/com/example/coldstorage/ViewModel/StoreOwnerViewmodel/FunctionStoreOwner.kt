package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstorage.DataLayer.Api.BagSize
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.FarmerInfo
import com.example.coldstorage.DataLayer.Api.IncomingOrderData
import com.example.coldstorage.DataLayer.Api.Location
import com.example.coldstorage.DataLayer.Api.OrderDetail
import com.example.coldstorage.DataLayer.Api.PopulatedFarmer
import com.example.coldstorage.DataLayer.Api.Quantity
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.GetAllReciptResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order
import com.example.coldstorage.DataLayer.Di.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FunctionStoreOwner @Inject constructor(
    private val api:ColdOpApi,
    private val authIntercepter: AuthInterceptor,

    ) :ViewModel() {

    private val _listOfFarmers = mutableStateOf<List<PopulatedFarmer>>(emptyList())
    val listOfFarmers: List<PopulatedFarmer> get() = _listOfFarmers.value
// need to deep dive


    private val _farmerdata = MutableStateFlow<FarmerApiState>(FarmerApiState.idle);
    val farmerData :StateFlow<FarmerApiState> = _farmerdata.asStateFlow()


    //stateflows for incoming order
    private val _variety = MutableStateFlow<String>("")
    val variety :StateFlow<String> = _variety.asStateFlow()

    private val _lotSize = MutableStateFlow<String>("")
    val lotsize :StateFlow<String> = _lotSize.asStateFlow()

    private val _Ration = MutableStateFlow<String>("")
    val Ration:StateFlow<String> = _Ration.asStateFlow()

    private val _seedBags = MutableStateFlow<String>("")
    val seedBags :StateFlow<String> = _seedBags.asStateFlow()

    private val _goli  = MutableStateFlow<String>("")
    val goli :StateFlow<String> = _goli.asStateFlow()

    private val _twelveNumber = MutableStateFlow<String>("")
    val twelveNumber :StateFlow<String> = _twelveNumber.asStateFlow()

    private val _cuttok = MutableStateFlow<String>("")
    val cuttok :StateFlow<String> = _cuttok.asStateFlow()

    private val _chamber =  MutableStateFlow<String>("")
    val chamber :StateFlow<String> = _chamber.asStateFlow()

    private val _floor = MutableStateFlow<String>("")
    val floor:StateFlow<String> = _floor.asStateFlow()

    private val _row = MutableStateFlow<String>("")
    val row : StateFlow<String> =  _row.asStateFlow()

    private val _farmerAcc = MutableStateFlow<String>("")
    val farmerAcc :StateFlow<String> = _farmerAcc.asStateFlow()

    //get transaction history data

    private val _transactionHistory = MutableStateFlow<getAllReciptsResponse>(getAllReciptsResponse.idle)
    val transactionHistory :StateFlow<getAllReciptsResponse> = _transactionHistory.asStateFlow()

    fun updateVariety(value: String) { _variety.value = value }
    fun updateLotSize(value: String) { _lotSize.value = value }
    fun updateRation(value: String) { _Ration.value = value }
    fun updateSeedBags(value: String) { _seedBags.value = value }
    fun updateTwelveNumber(value: String) { _twelveNumber.value = value }
    fun updateGoli(value: String) { _goli.value = value }
    fun updateCutAndTok(value: String) { _cuttok.value = value }

    fun updateFloor(value: String){ _floor.value = value}
    fun updateChamber(value: String) {_chamber.value = value }

    fun updateRow(value: String){_row.value = value}

    fun updateFarmerAcc(value: String){ _farmerAcc.value = value}

    fun fetchFarmersList(){
        viewModelScope.launch {

            try{
                val response = api.getAllFarmers()


                if (response.isSuccessful){
                    response.body()?.let {

                            farmers -> if(!farmers.populatedFarmers.isEmpty()){ _listOfFarmers.value = farmers.populatedFarmers
                    }
                        else{
                        Log.d("ErrorLog", "Error i fetching the list in the viewmodel")

                    }
                    }
                    Log.d("List of farmers", "Farmers inside viewmodel "+listOfFarmers)
                }

            } catch (e:Exception){
                Log.d("ErrorLog", "App is crashing on the people screen")
            }


        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun fetchSinglFarmerClick(farmerId:String){
        viewModelScope.launch {

            try {
                val response = api.getSingleFarmer(farmerId)
                if(response.isSuccessful){
                    _farmerdata.value = if (response.isSuccessful) {
                        FarmerApiState.success(response.body()?.farmerInfo) //to learn
                    } else {
                        FarmerApiState.error("Error fetching farmer data")
                    }
                        Log.d("SuccessLog", "Single farmer fetched "+response.body()?.farmerInfo)


                }
                else{
                    Log.d("ErrorLog", "Error in fetching single farmer  "+response.errorBody())

                }
            }
            catch(e:Exception){
                Log.d("ErrorLog", "Exception in in fetching single farmer  "+e)

            }
        }
    }




    fun createIncomingOrder(){
        viewModelScope.launch {
            val incomingOrderData = IncomingOrderData(
                coldStorageId = authIntercepter.getStore_id("store")!!,
                //coldStorageId = "64f94fbb789b9f26bc3a1a20",
                dateOfSubmission = "24.09.2024",
                farmerId = farmerAcc.value,
                orderDetails = listOf(
                    OrderDetail(
                        bagSizes = listOf(
                            BagSize(
                                quantity = Quantity(
                                    currentQuantity = seedBags.value.toInt(),
                                    initialQuantity = seedBags.value.toInt()
                                ),
                                size = "Seed"
                            ),
                            BagSize(
                                quantity = Quantity(
                                    currentQuantity = goli.value.toInt(),
                                    initialQuantity = goli.value.toInt()
                                ),
                                size = "Goli"
                            ),
                            BagSize(
                                quantity = Quantity(
                                    currentQuantity = Ration.value.toInt(),
                                    initialQuantity = Ration.value.toInt()
                                ),
                                size = "Ration"
                            ),
                            BagSize(
                                quantity = Quantity(
                                    currentQuantity = cuttok.value.toInt(),
                                    initialQuantity = cuttok.value.toInt()
                                ),
                                size = "CutTok"
                            ),
                            BagSize(
                                quantity = Quantity(
                                    currentQuantity = twelveNumber.value.toInt(),
                                    initialQuantity = twelveNumber.value.toInt()
                                ),
                                size = "12No."
                            )
                        ),
                        location = Location(
                            chamber =  chamber.value,
                            floor = floor.value,
                            row = row.value
                        ),
                        variety = variety.value
                    )
                ),
                voucherNumber = 123322 // Replace with actual voucher number
            )
            try {
                val response = api.createIncomingOrder(incomingOrderData = incomingOrderData)
                Log.d("Success", "Cold store id  "+authIntercepter.getStore_id("store"))

                if (response.isSuccessful){
                    Log.d("Success", "order created successfully")
                }
                else{
                    Log.d("ErrorOrder", "order not created successfully error: "+response)
                }
            } catch (e: Exception){
                Log.d("ErrorLog", " Order in the catch block  "+e)
            }
        }
    }



    //function for getting the recipts

    fun getAllRecipts(farmerId: String){
        viewModelScope.launch {
_transactionHistory.value = getAllReciptsResponse.loading

             try {
                 val response = api.getOldReciepts(farmerId)
                 if(response.isSuccessful){
                     Log.d("transactionSucessssOut" , _transactionHistory.value.toString())

                     response.body()?.let {
                         _transactionHistory.value = getAllReciptsResponse.success(it.data)
                          Log.d("transactionSuces" , _transactionHistory.value.toString())
                     // Directly update state with data
                     } ?: run {
                         // Handle null response body, consider this as failure
                        // _transactionHistory.value = getAllReciptsResponse.failure("Response body is null")
                     }                 }
                 else {
                 Log.d("ErrorLog" , "inside the else block")
                 }
             } catch (e:Exception){
                 Log.d("ErrorLog" , "Inside the catch block in transaction history "+e.message)
             }

        }
    }


    fun proceedToNextOutgoing(voucherNumber: List<String> , Index : List<String>){

        authIntercepter.saveSelectedOrder(voucherNumber ,Index)
    }

    fun getTheSelectedStock() : List<String>{
       return authIntercepter.getSelectedOrder()
    }

    fun getTheSelectedIndex() : List<String>{
      return  authIntercepter.getSelectedOrderIndex()
    }

}

//learnt new thing
sealed class FarmerApiState{
    object  idle : FarmerApiState();
    object loading:FarmerApiState();
    data class success(val farmerInfo: FarmerInfo?):FarmerApiState();

    data class error(val message:String):FarmerApiState();
}


sealed class getAllReciptsResponse{
    object idle : getAllReciptsResponse();

    object loading : getAllReciptsResponse();

    data class success(val reciptData: List<Order>) : getAllReciptsResponse()
}