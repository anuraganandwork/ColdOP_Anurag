package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coldstorage.DataLayer.Api.BagSize
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.FarmerInfo
import com.example.coldstorage.DataLayer.Api.IncomingOrderData
import com.example.coldstorage.DataLayer.Api.Location
import com.example.coldstorage.DataLayer.Api.OrderDetail
import com.example.coldstorage.DataLayer.Api.OutgoingData.BagUpdate
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.DataLayer.Api.PopulatedFarmer
import com.example.coldstorage.DataLayer.Api.Quantity
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.GetAllReciptResponse
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order
import com.example.coldstorage.DataLayer.Api.SearchFarmerData.SearchResultsData
import com.example.coldstorage.DataLayer.Di.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    response.body()?.farmerInfo?.let { Log.d("farmerInfo" , it._id) }

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


    private val _loading = MutableStateFlow<Boolean>(false)
    val loading : StateFlow<Boolean> =  _loading.asStateFlow()
  private val _orderResult = MutableStateFlow<Result<Unit>?>(null)
    val orderResult = _orderResult.asStateFlow()

    fun createIncomingOrderForUi(){
        viewModelScope.launch {
           val result = createIncomingOrder()
            _orderResult.value = result
        }
    }
    suspend fun createIncomingOrder(): Result<Unit> {
        return withContext(Dispatchers.IO) {  // Use withContext for suspend function
            try {
                _loading.value = true
                Log.d("farmerAcc", farmerAcc.value)
                val incomingOrderData = IncomingOrderData(
                    coldStorageId = authIntercepter.getStore_id("store")!!,
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
                                chamber = chamber.value,
                                floor = floor.value,
                                row = row.value
                            ),
                            variety = variety.value
                        )
                    ),
                    voucherNumber = currentRecieptNum.value
                )

                val response = api.createIncomingOrder(incomingOrderData = incomingOrderData)
                Log.d("Success data", incomingOrderData.toString())
                Log.d("Success", "Cold store id  " + authIntercepter.getStore_id("store"))

                if (response.isSuccessful) {
                    Log.d("Success", "Order created successfully")
                    Result.success(Unit)
                } else {
                    Log.d("ErrorOrder",
                        "Order not created successfully. Error: ${response.errorBody()?.string()} - ${response.message()}"
                    )
                    Result.failure(Exception("Failed to create order :("))
                }
            } catch (e: Exception) {
                Log.d("ErrorLog", "Order in the catch block: $e")
                Result.failure(e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun resetOrderResult() {
        _orderResult.value = null
    }

    //function for getting the recipts

    @SuppressLint("SuspiciousIndentation")
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

    fun clearSelectedCells(){
        authIntercepter.clearSelectedCell()
    }

    //
    var outgoingItemState: MutableState<OutgoingDataClassItem> = mutableStateOf(
        OutgoingDataClassItem(
            orderId = "",
            variety = "",
            bagUpdates = emptyList()
        )
    )

    val bagUpdateState : MutableState<BagUpdate> = mutableStateOf(
        BagUpdate("" , 0)
    )

    fun updateBagdata (newItem : BagUpdate){
        bagUpdateState.value = newItem
    }

    // Function to update the state of OutgoingDataClassItem
    fun updateOutgoingItem(newItem: OutgoingDataClassItem) {
        outgoingItemState.value = newItem
    }

    // Function to update bag updates within the outgoing item
    fun updateBagUpdates(newBagUpdates: List<BagUpdate>) {
        outgoingItemState.value = outgoingItemState.value.copy(bagUpdates = newBagUpdates)
    }
    fun confirmOutgoingOrder(farmerId : String  ){
        viewModelScope.launch {

            val bagUpdates = listOf(BagUpdate( "Goli", 10))
            val orderItems = listOf(OutgoingDataClassItem( "6713fb4f8082c69227ec72dc", "Pukhraj",bagUpdates))
           // val outgoingData = OutgoingDataClassBody(orderItems)
            Log.d("OutgoingSuccess" , orderItems.toString())
            try {
                val response = api.confirmOutgoingOrder(farmerId, orderItems)
                if (response.isSuccessful) {
                    response.body()?.message?.let { Log.d("OutgoingSuccess" , it) }
                } else {
                    // Handle failure (e.g., log or show error message)
                    Log.d("OutgoingSuccess" , "Errororrr "+ response.errorBody()?.string() + response.code() )
                }
            }
            catch (e : Exception){
                Log.d("OutgoingSuccess" , "In the catch block "+e.message)

            }
        }
    }



    // viewmodel function for searching farmers

    var searchResults by mutableStateOf<List<SearchResultsData>>(emptyList<SearchResultsData>())

    private var searchJob : Job? = null


    fun onSearchQuery( query :String){
        if(query.length > 3){
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(300)
                 searchFarmers(query)
            }
        } else{
            searchResults = emptyList<SearchResultsData>()
        }
    }

    private suspend fun searchFarmers(query:String){
     try{
        val response = api.searchFarmers(query)
         searchResults = response
     } catch (e : Exception){
          Log.d("Searching" , "Error is "+e)
          //


     }
    }


   private val _currentReciptNum = MutableStateFlow(0)
    val currentRecieptNum : StateFlow<Int>  =  _currentReciptNum.asStateFlow()
     fun getRecieptNumbers(){
        viewModelScope.launch {
            try {
                val response = api.getRecieptNum()
                if (response.isSuccessful){
                    Log.d("Response recipt" , " Recipet num is "+response.body()?.receiptNumber + " Status " +response.body()?.status)
                    _currentReciptNum.value = response.body()?.receiptNumber!!
                } else
                {
                    Log.d("Response recipt" , response.errorBody().toString())
                }
            } catch (e : Exception){
                Log.d("Response recipt" , "In the cathc block "+e.message)
            }
        }
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