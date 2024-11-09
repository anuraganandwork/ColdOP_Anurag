package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.AssignLocation
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ManageStocks
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FarmerApiState
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun farmerDetailedScreen(accNumber: String, navController: NavController , viewModel:FunctionStoreOwner= hiltViewModel()){

    LaunchedEffect(Unit ){
        viewModel.fetchSinglFarmerClick(accNumber)
    }

    val farmerData by viewModel.farmerData.collectAsState();
    //val farmerInfoAtui = (farmerData as FarmerApiState.success)?.farmerInfo

    //

    Scaffold(topBar = {
        TopAppBar(title = { Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Text(text = "Back", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.clickable { navController.popBackStack()  })
            Spacer(modifier = Modifier.padding(horizontal = 30.dp))
            Text(text = "Account" , fontSize = 24.sp, fontWeight = FontWeight.Bold)
        } })
    }) {
        Column(modifier = Modifier.padding(it)){

            when(farmerData){
                is FarmerApiState.success -> {
                    val farmerInfoAtui = (farmerData as FarmerApiState.success)?.farmerInfo
                    Column(modifier = Modifier.padding(12.dp)) {
                        if (farmerInfoAtui != null) {
                            Text(text = "Account no :  ${farmerInfoAtui.farmerId}")
                        }
                        if (farmerInfoAtui != null) {
                            Text(text = "Name : ${farmerInfoAtui.name}")
                        }
                        //Text(text = "S/O :")
                        if (farmerInfoAtui != null) {
                            Text(text = "Address : ${farmerInfoAtui.address}")
                        }
                        if (farmerInfoAtui != null) {
                            Text(text = "Contact : ${farmerInfoAtui.mobileNumber}")
                        }



                    }
                }

                else -> {
                    Text("Loading...")
                    Log.d("ErrorLog" , "errrrorororororororo")
                }
            }

            
            Text(text = "Choose Action" , fontSize = 24.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick =  {
                    when(farmerData){
                        is FarmerApiState.success ->{
                            val farmerInfoAtui = (farmerData as FarmerApiState.success)?.farmerInfo

                            navController.navigate(route = AllScreens.StoreOrRetrieve.name + "/${farmerInfoAtui?._id}")}
                        else ->{
                            Log.d("Errrr","erererere")
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()

//                    .clickable { when(farmerData){
//                        is FarmerApiState.success ->{
//                            val farmerInfoAtui = (farmerData as FarmerApiState.success)?.farmerInfo
//
//                            navController.navigate(route = AllScreens.StoreOrRetrieve.name + "/${farmerInfoAtui?._id}")}
//                        else ->{
//                            Log.d("Errrr","erererere")
//                        }
//                    } }
                    ,
                    shape = RoundedCornerShape(13.dp) ,
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White , containerColor = primeGreen)) {

                        Text(
                            text = "Manage stocks",
                            color = Color.Black, // Text color
                            maxLines = 2, // Ensure text can wrap to two lines
                            overflow = TextOverflow.Ellipsis, // Handle overflow
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 15.dp) // Padding around the text
                        )
                                   }
//                Surface(modifier = Modifier
//                    .width(160.dp)
//
//                    .clickable { } ,
//                    shape = RoundedCornerShape(13.dp)) {
//                    Text(text = "Manage payments", modifier = Modifier
//                        .background(Color.Green)
//                        .padding(horizontal = 15.dp, vertical = 15.dp))
//                }
            }








        }
}}