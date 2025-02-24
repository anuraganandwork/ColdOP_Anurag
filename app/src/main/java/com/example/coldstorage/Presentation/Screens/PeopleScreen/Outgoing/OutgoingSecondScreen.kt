package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.OutgoingData.BagUpdate
import com.example.coldstorage.DataLayer.Api.OutgoingData.MainOutgoingOrderClass
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpTextField
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.finalConfirmation
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.forSecondOutgoingPage
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.mapDataForSecondOutgoingPage
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.primeRed
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutgoingSecondScreen(accNum: String, viewmodel: FunctionStoreOwner, navController: NavController) {
    var seedBags by remember { mutableStateOf("0") }
    var rationBags by remember { mutableStateOf("0") }
    var no12Bags by remember { mutableStateOf("0") }
    var cutTokBags by remember { mutableStateOf("0") }
    var remarks by remember { mutableStateOf("") }
    val mainOutgoingBody: MutableState<MainOutgoingOrderClass>  = remember {
        mutableStateOf(MainOutgoingOrderClass(remarks = "" , orders = emptyList() ))    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember {
      mutableStateOf(false)
    }
    val showAlertConfirmation = remember {
        mutableStateOf(false)
    }
    val OutgoingOrderLoader by viewmodel.outgoingOrderLoader.collectAsState()
    val OutgoingOrderStatus by viewmodel.outgoingOrderStatus.collectAsState()

    val onApiSuccess: () -> Unit = {
        Log.d("OnnApiSuccess" , showBottomSheet.value.toString())
        //showAlertConfirmation.value = true
       // navController.navigate(AllScreens.Dashboard.name)
        Log.d("OnnApiSuccessAfter" , showBottomSheet.value.toString())

    }
    val orderOutgoingResult by viewmodel.orderOutgoingResult.collectAsState()


    val totalSeedBags = mutableStateOf<String>("")
    val retrievedData by viewmodel.retrievedSelectedData.collectAsState()
    Log.d("SelectedCellData" , retrievedData.toString())
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(Unit){

        // viewmodel.getAllRecipts(accNum)
        viewmodel.retrieveSelectedCellData()

        Log.d("OutgoingSh" , viewmodel.getTheSelectedStock().toString())
        Log.d("OutgoingSh" , viewmodel.getTheSelectedIndex().toString())
//        selectedCellData?.let {
//            // Use the retrieved data
//            Log.d("SelectedDataaaaa", "Order ID: ${it.orderId}, Voucher Number: ${it.voucherNumber} , size: ${it.size}")
//        } ?: run {
//            Log.d("SelectedDataaaaa", "No selected data found.")
//        }

    }

    LaunchedEffect(OutgoingOrderStatus ){
        if(OutgoingOrderStatus){
            Log.d("Hello outgiggigigig" , OutgoingOrderStatus.toString())
            if (keyboardController != null) {
                keyboardController.hide()
            }
            navController.navigate(AllScreens.OutgoingScreenSuccess.name)
            viewmodel.resetOrderOtgoingResult()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Outgoing Stock") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewmodel.clearSelectedCells()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ){paddingValues ->
    Column(modifier = Modifier
        .padding(horizontal = 14.dp, vertical = 8.dp)
        .padding(paddingValues = paddingValues)) {
        Text("Select Quantities Required for :")


//        InputField("Seed Bags", seedBags) { seedBags = it }
//        InputField("Ration Bags", rationBags) { rationBags = it }
//        InputField("No. 12 bags", no12Bags) { no12Bags = it }
//        InputField("Cut & Tok Bags", cutTokBags) { cutTokBags = it }
//        InputField("Goli Bags", goliBags) { goliBags = it }

        if(retrievedData != null){






            Spacer(modifier = Modifier.padding(13.dp))
            Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween){
                Text(text="Bag Size",fontSize = 13.sp,fontWeight = FontWeight.Bold,modifier = Modifier
                    .weight(1f) , textAlign = TextAlign.Start)
                Text(text = "Address",fontSize = 13.sp,fontWeight = FontWeight.Bold,modifier = Modifier
                    .weight(.8f) , textAlign = TextAlign.Start)
                Text(text ="Curr Qty",fontSize = 13.sp,fontWeight = FontWeight.Bold,modifier = Modifier
                    .weight(.8f) , textAlign = TextAlign.Start)
                Text(text= "Removing",fontSize = 13.sp ,fontWeight = FontWeight.Bold, modifier = Modifier
                    .weight(.8f) , textAlign = TextAlign.Start)
            }

            retrievedData!!.forEach {
              it.bagUpdates.forEach {bags->
                  Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                      Text(text = bags.size,fontSize = 13.sp  ,textAlign = TextAlign.Start, modifier = Modifier
                          .weight(1f))
                      Text(text = it.address , fontSize = 13.sp,textAlign = TextAlign.Start,modifier = Modifier
                          .weight(.8f))
                      Text(text = it.currQty,fontSize = 13.sp ,textAlign = TextAlign.Start, modifier = Modifier
                          .weight(.8f))
                      Text(text = bags.quantityToRemove.toString(),fontSize = 13.sp ,textAlign = TextAlign.Start, modifier = Modifier
                          .weight(.8f))
                  }
              }
            }
            Spacer(modifier = Modifier.height(16.dp))

            ColdOpTextField(value = remarks, onValueChange = {
                remarks = it
            } ,  placeholder = "Describe any sort of exception to be handelled in\n" +
                    "the order , could be multiple address allocation." ,
                modifier = Modifier
                    .width(370.dp)
                    .height(125.dp)
                    .padding(bottom = 10.dp)
            )

            Button(
                onClick = {
                    Log.d("OutgoingSuccess" , "Pressed button")
                    // showBottomSheet.value = true
                    if(retrievedData!= null){
                    mainOutgoingBody.value = MainOutgoingOrderClass(
                        remarks = remarks,
                        orders = retrievedData!!
                    )}
                    try{
                        viewmodel.confirmOutgoingOrderForUi(accNum ,mainOutgoingBody.value)

                        viewmodel.clearSelectedCellData()
                        //viewmodel.clearSelectedCells()
                        Log.d("OutgoingInTry" , "Pressedsffd button")

                    }catch (e:Exception){
                        Log.d("innnnnn" , e.message.toString())
                    }

                    //   navController.navigate(AllScreens.OutgoingScreenSuccess.name)
                    //  openConfirmSheet(true)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .width(150.dp)
                    .height(40.dp), colors = ButtonDefaults.buttonColors(containerColor = primeGreen , contentColor = Color.White)
            ) {
                Box(
                    contentAlignment = Alignment.Center, // Centers the content within the Box
                    modifier = Modifier.fillMaxSize(),
                    // Ensures Box takes full size of Surface
                ) {
                    if (OutgoingOrderLoader) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp), // Small size for the indicator
                            color = Color.Black
                        )
                    } else {
                        Text(
                            text = "Continue",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            }
        
        }
       
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1.25f) // This makes the table take the remaining space
//        ) {
//            //StockTablee(accNum ,viewmodel , navController)
//        }
        Spacer(modifier = Modifier.height(16.dp))
       if(showAlertConfirmation.value){
//           AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ } ,
//               text = {Text("Outgoing success")
//               })
         Log.d("cxcxcxcxcxcx" , "cxcxcxcxcxcxcxcxcxcx")

       }
//        Button(
//            onClick = {
//                Log.d("OutgoingSuccess" , "Pressed button")
//               // showBottomSheet.value = true
//               //  viewmodel.confirmOutgoingOrder(accNum )
//                viewmodel.clearSelectedCellData()
//                viewmodel.clearSelectedCells()},
//            modifier = Modifier.align(Alignment.End) , colors = ButtonDefaults.buttonColors(containerColor = primeGreen , contentColor = Color.White)
//        ) {
//            Text("Proceed")
//        }
    }
        Log.d("sdsdsdsd123456789" , showBottomSheet.value.toString())
    if(showBottomSheet.value){

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )


        ){
            finalConfirmation {
                  navController.navigate(AllScreens.Dashboard.name)
            }
        }

    }

    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.CenterVertically) {
        Text(text = label)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(134.dp)
                .height(40.dp)
                .padding(vertical = 4.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                //.padding(horizontal = 8.dp, vertical = 12.dp), // Adjust padding to vertically center the text

            , textStyle = TextStyle(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            , maxLines = 1 ,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    innerTextField()
                }
            }
        )  
    }
    
}

@SuppressLint("SuspiciousIndentation", "LongLogTag")
@Composable
fun StockTablee(accNum: String, viewmodel: FunctionStoreOwner ,navController: NavController) {
    val transactionHistory = viewmodel.transactionHistory.collectAsState()
    val outgoingItem by viewmodel.outgoingItemState

    LaunchedEffect(Unit){

       // viewmodel.getAllRecipts(accNum)
        viewmodel.retrieveSelectedCellData()

        Log.d("OutgoingSh" , viewmodel.getTheSelectedStock().toString())
        Log.d("OutgoingSh" , viewmodel.getTheSelectedIndex().toString())
//        selectedCellData?.let {
//            // Use the retrieved data
//            Log.d("SelectedDataaaaa", "Order ID: ${it.orderId}, Voucher Number: ${it.voucherNumber} , size: ${it.size}")
//        } ?: run {
//            Log.d("SelectedDataaaaa", "No selected data found.")
//        }

    }


     val orderOutgoingResult by viewmodel.orderOutgoingResult.collectAsState()

//    LaunchedEffect(orderOutgoingResult) {
//        //1230
//        orderOutgoingResult?.let { result ->
//            if (result.isSuccess) {
//                val data = result.getOrNull() // Extract the successful result
//               // navController.navigate(AllScreens.Dashboard.name)
//                Log.d("1x1x1x1xx1x1",data.toString())
//                viewmodel.resetOrderOtgoingResult()
//                // Handle success, e.g., navigate to another page
//            } else if (result.isFailure) {
//                val error = result.exceptionOrNull() // Extract the exception
//              Log.d("123456789" ,error.toString())
//                viewmodel.resetOrderOtgoingResult()
//               // Handle failure, e.g., show an error message
//            }
//        }
//    }


//    LaunchedEffect(Unit) {
//        viewmodel.outgoingOrderLoader.collect { isLoading ->
//            Log.d("LoaderDebug", "Loading: $isLoading")
//        }
//    }





    val retrievedData by viewmodel.retrievedSelectedData.collectAsState()
    Log.d("SelectedCellData" , retrievedData.toString())
    val SelectedVoucherNumber = viewmodel.getTheSelectedStock()
    Log.d("SelectedVoucher" ,SelectedVoucherNumber.toString())
    val selectedColumns = viewmodel.getTheSelectedIndex()
    var rows = mutableListOf<forSecondOutgoingPage>()
//    val uniqueVoucherSizePairs = rows.flatMap { row ->
//        row.bagsizeData.map { bagSize ->
//            Pair(row.voucherNum, bagSize.size)
//        }
//    }.distinct() // Ensure we have unique pairs

    // Step 2: Initialize the mutable state for each unique pair
//    var quantityValues by remember {
//        mutableStateOf(uniqueVoucherSizePairs.map { "" })
//    }
    var mutableStateArray = remember { mutableStateListOf<MutableState<String>>() }

   val outgoingResponseBody = remember {
       mutableListOf<OutgoingDataClassItem>()
   }
    LaunchedEffect(retrievedData) {
        mutableStateArray.clear()

        retrievedData?.forEach { data ->
            mutableStateArray.add(mutableStateOf(""))
        }
    }
    var showDialog by remember { mutableStateOf(false) }

    // Update the dialog state when OutgoingOrderStatus changes
//    LaunchedEffect(OutgoingOrderStatus) {
//        if (OutgoingOrderStatus) {
//            Log.d("wertyu","123456789")
//            showDialog = true
//        }
//    }

//   LaunchedEffect(OutgoingOrderStatus ){
//       Log.d("gfdgdfgdfg" , OutgoingOrderStatus.toString())
//       if(OutgoingOrderStatus == true){
//           openConfirmSheet(true)
//       }   }

    when(val state = transactionHistory.value){
        is getAllReciptsResponse.success ->{
            Log.d("StateRecipt" , state.reciptData.toString())
            val filteredData = state.reciptData.filter { recipt ->
                Log.d("VoucherNumber", recipt.voucher.voucherNumber.toString())
                SelectedVoucherNumber.any { it.toInt() == recipt.voucher.voucherNumber }
            }
            Log.d("SelectedVoucherFiltered" , filteredData.toString())
            rows = mapDataForSecondOutgoingPage(filteredData)
            Log.d("roowa" , rows.toString())
        }
        else ->{
            Log.d("OutgoingsecondFiltered " , "XXXXXX")
        }
    }
    val voucherBagSizePairs = rows.flatMap { row ->
        row.bagsizeData.map { bagSize ->
            VoucherBagSizePair(
                orderId = row.orderId,
                voucherNum = row.voucherNum,
                bagSize = bagSize.size,
                date = row.dateOfSubmission,
                address = row.address,
                currentQuantity = bagSize.quantity.currentQuantity

            )
        }
    }.distinctBy { it.voucherNum to it.bagSize }

    // State for storing input values
    var quantityValues by remember {
        mutableStateOf(voucherBagSizePairs.associate {
            (it.voucherNum to it.bagSize) to ""
        })
    }


    val headers = listOf("V No.", "Address", "Bag Type", "Curr bags", "Quantity Req.")
    val data = listOf(
        listOf("1/05/23", "A-3-21", "Seed", "50", "0"),
        listOf("3/05/23", "A-5-89", "Seed", "50", "0"),
        listOf("7/09/23", "B-2-34", "No. 12", "50", "0"),
        listOf("13/09/23", "D-0-12", "Goli", "50", "0")
    )
    ///val editableValues = remember { mutableStateListOf<String>() }
    // Initialize the list with empty strings



    //add rows here instead of data
    //var quantityValues by remember { mutableStateOf(data.map { it[4] }) }

//    LaunchedEffect(data) {
//        editableValues.clear()
//        editableValues.addAll(List(data.size) { "" })
//    }
    val keyboardController = LocalSoftwareKeyboardController.current


//    LaunchedEffect(OutgoingOrderStatus ){
//        if(OutgoingOrderStatus){
//            Log.d("Hello outgiggigigig" , OutgoingOrderStatus.toString())
//            if (keyboardController != null) {
//                keyboardController.hide()
//            }
//            navController.navigate(AllScreens.OutgoingScreenSuccess.name)
//            viewmodel.resetOrderOtgoingResult()
//        }
//    }





    val field  = remember{
        mutableStateOf("")
    }
    val context = LocalContext.current
    val remarks = remember {mutableStateOf("")}
     val mainOutgoingBody: MutableState<MainOutgoingOrderClass>  = remember {
                       mutableStateOf(MainOutgoingOrderClass(remarks = "" , orders = emptyList() ))    }
    Column() {

        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEach { header ->
                Text(
                    text = header,

                    fontWeight = FontWeight.Bold,

                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 5.dp)

                )
            }
        }
//        LazyColumn {
//            items(uniqueVoucherSizePairs.size) { index  ->
//                val row = rows[index]
//                Log.d("Rrroro" , row.toString())
//                selectedColumns.forEachIndexed { index, s ->
//
//                Row(modifier = Modifier.fillMaxWidth()) {
////                    row.forEachIndexed  { Cellindex ,  cell ->
////
////                        when {
////                            Cellindex == 4 ->{
////                                //on change methods
////                                BasicTextField(
////                                    value = quantityValues[index] ,
////                                    onValueChange = { newValue ->
////                                        quantityValues = quantityValues.toMutableList().also { it[index] = newValue }
////                                    },
////                                    modifier = Modifier
////                                        .width(110.dp)
////                                        .height(40.dp)
////                                        .padding(vertical = 4.dp)
////                                        .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
////                                    //.padding(horizontal = 8.dp, vertical = 12.dp), // Adjust padding to vertically center the text
////
////                                    , textStyle = TextStyle(textAlign = TextAlign.Center),
////                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
////                                    , maxLines = 1 ,
////                                    decorationBox = { innerTextField ->
////                                        Box(
////                                            contentAlignment = Alignment.Center,
////                                            modifier = Modifier.fillMaxSize()
////                                        ) {
////                                            innerTextField()
////                                        }
////                                    }
////                                )
////                            } else ->  Text(
////                            text = cell,
////                            modifier = Modifier.weight(1f)
////                        )
////                        }
////
////                    }
//
//
//
//                        Text(
//                            text = row.dateOfSubmission,
//                            modifier = Modifier.weight(1f)
//                        )
//
//
//                        Text(
//                            text = row.address.chamber + "-" + row.address.floor + "-" + row.address.row,
//                            modifier = Modifier.weight(1f)
//                        )
//                        val variety = if(s == "2") "Seed"
//                        else if (s == "3") "Number-12"
//                        else if(s == "4") "Ration"
//                        else if (s =="5") "Goli"
//                        else "Cut-Tok"
//                    val matchingBagSize = row.bagsizeData.find { it.size == variety }
//                    Log.d("Ouut" ,matchingBagSize.toString() + row.bagsizeData.toString() )
//                        Text(
//                            text = matchingBagSize?.size ?: "",
//                            modifier = Modifier.weight(1f)
//                        )
//                    Text(
//                        text = matchingBagSize?.quantity?.currentQuantity?.toString() ?: "0", // Display currentQuantity or 0 if no match
//                        modifier = Modifier.weight(1f)
//                    )
//                    BasicTextField(
//                            value = quantityValues[index],
//                            onValueChange = { newValue ->
//                                quantityValues =
//                                    quantityValues.toMutableList().also { it[index] = newValue }
//                            },
//                            modifier = Modifier
//                                .width(110.dp)
//                                .height(40.dp)
//                                .padding(vertical = 4.dp)
//                                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
//                            //.padding(horizontal = 8.dp, vertical = 12.dp), // Adjust padding to vertically center the text
//
//                            ,
//                            textStyle = TextStyle(textAlign = TextAlign.Center),
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                            maxLines = 1,
//                            decorationBox = { innerTextField ->
//                                Box(
//                                    contentAlignment = Alignment.Center,
//                                    modifier = Modifier.fillMaxSize()
//                                ) {
//                                    innerTextField()
//                                }
//                            }
//                        )
//
//                    }
//
//
//                }
//            }
//        }
        if(retrievedData != null){

            Spacer(modifier = Modifier.padding(27.dp))

            // add remarks here
            Text(text = retrievedData.toString(),  fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.padding(6.dp))

            ColdOpTextField(value = remarks.value, onValueChange = {
                                                                   remarks.value = it
            } ,  placeholder = "Describe any sort of exception to be handelled in\n" +
                    "the order , could be multiple address allocation." ,
                modifier = Modifier
                    .width(370.dp)
                    .height(125.dp)
                    .padding(bottom = 10.dp)
            )



            Button(
                onClick = {
                    Log.d("OutgoingSuccess" , "Pressed button")
                    Log.d("Outtttt" , outgoingResponseBody.toString())
                    // showBottomSheet.value = true
                    mainOutgoingBody.value = MainOutgoingOrderClass(
                        remarks = remarks.value,
                        orders = outgoingResponseBody
                    )
                    try{viewmodel.confirmOutgoingOrderForUi(accNum ,mainOutgoingBody.value)

                       viewmodel.clearSelectedCellData()
                        //viewmodel.clearSelectedCells()
                        Log.d("OutgoingInTry" , "Pressedsffd button")

                    }catch (e:Exception){
                            Log.d("innnnnn" , e.message.toString())
                        }

                 //   navController.navigate(AllScreens.OutgoingScreenSuccess.name)
                   //  openConfirmSheet(true)
                          },
                modifier = Modifier
                    .align(Alignment.End)
                    .width(150.dp)
                    .height(40.dp), colors = ButtonDefaults.buttonColors(containerColor = primeGreen , contentColor = Color.White)
            ) {
//                Box(
//                    contentAlignment = Alignment.Center, // Centers the content within the Box
//                    modifier = Modifier.fillMaxSize(),
//                    // Ensures Box takes full size of Surface
//                ) {
//                    if (OutgoingOrderLoader) {
//                        CircularProgressIndicator(
//                            modifier = Modifier.size(20.dp), // Small size for the indicator
//                            color = Color.Black
//                        )
//                    } else {
//                        Text(
//                            text = "Continue",
//                            color = Color.White,
//                            modifier = Modifier.padding(horizontal = 10.dp)
//                        )
//                   }
//                }
            }
}

        Spacer(modifier = Modifier.height(350.dp))

    }}

data class VoucherBagSizePair(
    val orderId : String,
    val voucherNum: Int,
    val bagSize: String,
    val date: String,
    val address: String,
    val currentQuantity: Int
)