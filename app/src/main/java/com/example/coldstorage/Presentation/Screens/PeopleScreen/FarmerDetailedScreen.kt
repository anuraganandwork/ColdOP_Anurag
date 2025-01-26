package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary.StockSummary
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.AssignLocation
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ManageStocks
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.stringToImage
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FarmerApiState
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun farmerDetailedScreen(accNumber: String, navController: NavController , viewModel:FunctionStoreOwner= hiltViewModel()){

    LaunchedEffect(Unit ){
        viewModel.fetchSinglFarmerClick(accNumber)
    }
    LaunchedEffect(Unit ){
        viewModel.getDetailedStockSummary(accNumber)
    }
     val detailedSummary = viewModel.detailedSummary.collectAsState()
    val loadingSummary= viewModel.loadingDetailedSummary.collectAsState()
   Log.d("fghj",detailedSummary.value.toString())
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
                    Column(modifier = Modifier.padding(10.dp)) {
                        if (farmerInfoAtui != null) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Account no :  " , modifier = Modifier.weight(1f))
                                Text(text = "${farmerInfoAtui.farmerId}" , modifier = Modifier.weight(.7f), fontWeight = FontWeight.Bold , textAlign = TextAlign.Start )
                            }
                        }
                        if (farmerInfoAtui != null) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                Text(text = "Name : " , modifier = Modifier.weight(1f))
                                Text(text = "${farmerInfoAtui.name}" ,  fontWeight = FontWeight.Bold , modifier = Modifier.weight(.7f), textAlign = TextAlign.Start)
                            }
                        }
                        //Text(text = "S/O :")
                        if (farmerInfoAtui != null) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Address : " , modifier = Modifier.weight(1f))
                                Text(text = "${farmerInfoAtui.address}" ,  fontWeight = FontWeight.Bold ,  modifier = Modifier.weight(.7f),textAlign = TextAlign.Start)
                            }                        }
                        if (farmerInfoAtui != null) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Contact : " , modifier = Modifier.weight(1f))
                                Text(text = "${farmerInfoAtui.mobileNumber}" ,  fontWeight = FontWeight.Bold , modifier = Modifier.weight(.7f), textAlign = TextAlign.Start)
                            }                        }



                    }
                }

                else -> {
                    Text("Loading...")
                    Log.d("ErrorLog" , "errrrorororororororo")
                }
            }

            
            Text(text = "Choose Action" , fontSize = 24.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick =  {
                    when(farmerData){
                        is FarmerApiState.success ->{
                            val farmerInfoAtui = (farmerData as FarmerApiState.success)?.farmerInfo
                            val totalIncoming = detailedSummary.value?.let { getTotalIncomingCount(it).toString() } ?: "0"
                            val totalOutgoing = detailedSummary.value?.let { getTotalOutgoingCount(it).toString() } ?: "0"
                            Log.d("dfdfdfgggggg555" , totalIncoming+"/"+totalOutgoing +"/"+ farmerInfoAtui?._id)
                            var r = AllScreens.StoreOrRetrieve.name + "/${farmerInfoAtui?._id}" +"/${totalIncoming}" + "/${totalOutgoing}"
                            Log.d("343434fgfgf" , r)
                           navController.navigate(route = AllScreens.StoreOrRetrieve.name + "/${farmerInfoAtui?._id}" +"/${totalIncoming}" + "/${totalOutgoing}")
                            }
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
                            maxLines = 1, // Ensure text can wrap to two lines
                            overflow = TextOverflow.Ellipsis, // Handle overflow
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 10.dp) // Padding around the text
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


// add summary here

            Text("Stock Summary" , fontSize = 24.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp) )
            Column(modifier = Modifier.padding( start = 10.dp, top = 0.dp , end = 10.dp )) {
              if(detailedSummary.value.isNotEmpty()){
                  //add here
                 Row(){
                     Text(text = "Varieties",fontSize = 13.sp , fontWeight = FontWeight.Bold , modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.35f) , textAlign = TextAlign.Center)
//                     detailedSummary.value.forEach {
//
//                         Row {
//                             //it?.variety?.let { it1 -> Text(text = it1) }
////                          Text(text = it?.sizes?.get(0)?.currentQuantity.toString())
////                          Text(text = it?.sizes?.get(1)?.currentQuantity.toString())
////                          Text(text = it.sizes?.get(2)?.currentQuantity.toString())
////                          Text(text = it?.sizes?.get(3)?.currentQuantity.toString())
////                          Text(text = it?.sizes?.get(4)?.currentQuantity.toString())
//
//                             it.sizes?.get(0)?.let{ bags->
//                                 if(bags.size == "Number-12"){
//                                     Text(text = "No.12" , fontSize = 13.sp , modifier = Modifier.padding(end = 5.dp))
//
//                                 } else {
//                                 Text(text = bags.size.toString() , fontSize = 13.sp , modifier = Modifier.padding(end = 5.dp))
//
//
//                                 }
//                             }
//
//                         }
//                     }
                      Text(text = "Goli"  , fontSize = 13.sp , fontWeight = FontWeight.Bold, modifier = Modifier
                          .padding(end = 5.dp)
                          .weight(.2f) , textAlign = TextAlign.Center)
                      Text(text= "No12" , fontSize = 13.sp , fontWeight = FontWeight.Bold, modifier = Modifier
                          .padding(end = 5.dp)
                          .weight(.2f) , textAlign = TextAlign.Center)
                      Text(text = "Seed" , fontSize = 13.sp ,fontWeight = FontWeight.Bold, modifier = Modifier
                          .padding(end = 5.dp)
                          .weight(.2f) , textAlign = TextAlign.Center)
                      Text(text="Cut" , fontSize = 13.sp ,fontWeight = FontWeight.Bold, modifier = Modifier
                          .padding(end = 5.dp)
                          .weight(.19f) , textAlign = TextAlign.Center)
                      Text(text = "Ration" , fontSize = 13.sp ,fontWeight = FontWeight.Bold, modifier = Modifier
                          .padding(end = 5.dp)
                          .weight(.25f) , textAlign = TextAlign.Center)
                     Text(text = "Total" , fontSize = 13.sp ,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.2f) , textAlign = TextAlign.Center)

                 }
                  detailedSummary.value.forEach {

                      Row {
                          it?.variety?.let { it1 -> Text(text = it1 , fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.35f) , textAlign = TextAlign.Center) }
//                          Text(text = it?.sizes?.get(0)?.currentQuantity.toString())
//                          Text(text = it?.sizes?.get(1)?.currentQuantity.toString())
//                          Text(text = it.sizes?.get(2)?.currentQuantity.toString())
//                          Text(text = it?.sizes?.get(3)?.currentQuantity.toString())
//                          Text(text = it?.sizes?.get(4)?.currentQuantity.toString())
                          Text(text = it.sizes.find { bag -> bag.size == "Goli" }?.let { bag-> bag.currentQuantity.toString() }?:"0" ,fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.2f) , textAlign = TextAlign.Center)
                          Text(text = it.sizes.find { bag -> bag.size == "Number-12" }?.let { bag-> bag.currentQuantity.toString() }?:"0" ,fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.2f) , textAlign = TextAlign.Center)
                          Text(text = it.sizes.find { bag -> bag.size == "Seed" }?.let { bag-> bag.currentQuantity.toString() }?:"0",fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.2f) , textAlign = TextAlign.Center)
                          Text(text = it.sizes.find { bag -> bag.size == "Cut-tok" }?.let { bag-> bag.currentQuantity.toString() }?:"0" , fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.19f) , textAlign = TextAlign.Center)
                          Text(text = it.sizes.find { bag -> bag.size == "Ration" }?.let { bag-> bag.currentQuantity.toString() }?:"0" , fontSize = 13.sp , modifier = Modifier
                              .padding(end = 5.dp)
                              .weight(.25f) , textAlign = TextAlign.Center)
                          Text(
                              text = findSumOfSizesUnderSameVariety(
                                  detailedSummary.value,
                                  it.variety
                              ).toString(), fontSize = 13.sp, modifier = Modifier
                                  .padding(end = 5.dp)
                                  .weight(.2f) , textAlign = TextAlign.Center
                          )

//                          it.sizes?.forEach { bags->
//                              if(bags !== null){
//                                  Text(text = bags.currentQuantity.toString() , fontSize = 13.sp , modifier = Modifier.padding(end = 5.dp) )
//
//                              } else {
//                                  Text(text = "0" , fontSize = 13.sp , modifier = Modifier.padding(end = 5.dp) )
//
//                              }
//
//                          }

                      }


                      //
                  }

                 Row(modifier = Modifier.background(lightGrayBorder , RoundedCornerShape(2.dp))) {
                     Text(text = "BagTotal", fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.35f) , textAlign = TextAlign.Center)
                     Text(text = findSumOfEachBagsize( detailedSummary.value, "Goli").toString()  ,  fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.2f) , textAlign = TextAlign.Center)
                     Text(text = findSumOfEachBagsize( detailedSummary.value, "Number-12").toString() , fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.2f) , textAlign = TextAlign.Center)
                     Text(text = findSumOfEachBagsize( detailedSummary.value, "Seed").toString() , fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.2f) , textAlign = TextAlign.Center)
                     Text(text = findSumOfEachBagsize( detailedSummary.value, "Cut-tok").toString() , fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.19f) , textAlign = TextAlign.Center)
                     Text(text = findSumOfEachBagsize( detailedSummary.value, "Ration").toString() , fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.25f) , textAlign = TextAlign.Center)
                     Text(text = grandTotal(detailedSummary.value).toString() ,fontSize = 13.sp,fontWeight = FontWeight.Bold, modifier = Modifier
                         .padding(end = 5.dp)
                         .weight(.2f) , textAlign = TextAlign.Center)

                 }

              } else {
                  Text(text = "No bags stored!")
              }




                
            }

//            val bitmap = stringToImage(inputText)
//
//            // Display the image
//            Image(
//                bitmap = bitmap.asImageBitmap(),
//                contentDescription = "Generated Image",
//                modifier = Modifier.fillMaxWidth()
//            )



        }
}}

fun findSumOfSizesUnderSameVariety(stockSummary : List<StockSummary> , variety: String) : Int{


  val foundstock=   stockSummary.find { varietyIn-> varietyIn.variety == variety }
    var totalSum = 0;
    foundstock?.sizes?.forEach {
        totalSum += it.currentQuantity
    }

    return totalSum;

}


fun findSumOfEachBagsize(stockSummary: List<StockSummary> , size:String) : Int{
    var totalBags = 0;
//   var sizess=  stockSummary.sizes.find { bag -> bag.size  == size}
//    if (sizess != null) {
//        totalBags = totalBags + sizess.currentQuantity
//    }

    stockSummary.forEach {
       val bag =  it.sizes.find { bagsize -> bagsize.size == size  }
        totalBags += bag?.currentQuantity ?:  0
    }

    return totalBags

}

fun grandTotal(stockSummary: List<StockSummary>):Int{
    var grand = 0;
    stockSummary.forEach {
        grand +=   findSumOfSizesUnderSameVariety(stockSummary , it.variety)
    }
    return grand
}


fun getTotalIncomingCount(detailedSummary:List<StockSummary>?):Int{
    var total =0 ;
    if (detailedSummary != null) {
        detailedSummary.forEach {
            it.sizes.forEach {
                total += it.initialQuantity
            }

        }
    }


    return total;
}


fun getTotalOutgoingCount(detailedSummary:List<StockSummary>?):Int{
    var total = 0;

    if (detailedSummary != null) {
        detailedSummary.forEach {
            it.sizes.forEach {
                total += it.quantityRemoved
            }
        }
    }


    return total
}