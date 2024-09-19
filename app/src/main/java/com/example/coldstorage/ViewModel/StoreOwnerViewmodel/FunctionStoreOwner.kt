package com.example.coldstorage.ViewModel.StoreOwnerViewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.FarmerInfo
import com.example.coldstorage.DataLayer.Api.PopulatedFarmer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FunctionStoreOwner @Inject constructor(
    private val api:ColdOpApi
) :ViewModel() {

    private val _listOfFarmers = mutableStateOf<List<PopulatedFarmer>>(emptyList())
    val listOfFarmers: List<PopulatedFarmer> get() = _listOfFarmers.value
// need to deep dive


    private val _farmerdata = MutableStateFlow<FarmerApiState>(FarmerApiState.idle);
    val farmerData :StateFlow<FarmerApiState> = _farmerdata.asStateFlow()
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
                        FarmerApiState.success(response.body()?.farmerInfo)
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
}

//learnt new thing
sealed class FarmerApiState{
    object  idle : FarmerApiState();
    object loading:FarmerApiState();
    data class success(val farmerInfo: FarmerInfo?):FarmerApiState();

    data class error(val message:String):FarmerApiState();
}