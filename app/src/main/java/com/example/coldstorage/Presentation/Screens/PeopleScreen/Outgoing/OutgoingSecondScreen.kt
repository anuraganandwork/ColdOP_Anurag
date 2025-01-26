package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Location
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
fun OutgoingSecondScreen(accNum : String , viewmodel: FunctionStoreOwner , navController: NavController) {
    var seedBags by remember { mutableStateOf("0") }
    var rationBags by remember { mutableStateOf("0") }
    var no12Bags by remember { mutableStateOf("0") }
    var cutTokBags by remember { mutableStateOf("0") }
    var goliBags by remember { mutableStateOf("0") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember {
      mutableStateOf(false)
    }
    val showAlertConfirmation = remember {
        mutableStateOf(false)
    }
    val onApiSuccess: () -> Unit = {
        Log.d("OnnApiSuccess" , showBottomSheet.value.toString())
        //showAlertConfirmation.value = true
       // navController.navigate(AllScreens.Dashboard.name)
        Log.d("OnnApiSuccessAfter" , showBottomSheet.value.toString())

    }
    val orderOutgoingResult by viewmodel.orderOutgoingResult.collectAsState()


    val totalSeedBags = mutableStateOf<String>("")



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

        Spacer(modifier = Modifier.height(16.dp))

//        InputField("Seed Bags", seedBags) { seedBags = it }
//        InputField("Ration Bags", rationBags) { rationBags = it }
//        InputField("No. 12 bags", no12Bags) { no12Bags = it }
//        InputField("Cut & Tok Bags", cutTokBags) { cutTokBags = it }
//        InputField("Goli Bags", goliBags) { goliBags = it }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.25f) // This makes the table take the remaining space
        ) {
            StockTablee(accNum ,viewmodel , navController)
        }
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun StockTablee(accNum: String, viewmodel: FunctionStoreOwner ,navController: NavController) {
    val transactionHistory = viewmodel.transactionHistory.collectAsState()
    val outgoingItem by viewmodel.outgoingItemState

    LaunchedEffect(Unit){

        viewmodel.getAllRecipts(accNum)
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

    val OutgoingOrderLoader by viewmodel.outgoingOrderLoader.collectAsState()
    val OutgoingOrderStatus by viewmodel.outgoingOrderStatus.collectAsState()
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
        LazyColumn {
            items(retrievedData!!) { pair ->
                Log.d("Rowwww" ,voucherBagSizePairs.toString())

                val keyboardController = LocalSoftwareKeyboardController.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = pair.voucherNumber.toString(),
                        modifier = Modifier.width(35.dp)
                        // .background(primeRed)
                        ,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center

                    )
                    Text(
                        text = "${pair.address}",
                        //text = pair.address,
                        modifier = Modifier.width(60.dp)
                        //.background(primeGreen)
                        ,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center

                    )
                    pair.size?.let {
                        Text(
                            text = it,
                            modifier = Modifier.width(90.dp)
                            //.background(primeRed)
                            ,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = pair.currentQuantity.toString(),
                        modifier = Modifier.width(50.dp)
                        //  .background(primeGreen)
                        ,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center

                    )
//                    BasicTextField(
//                        value = quantityValues[pair to pair.bagSize] ?: "",
//                        onValueChange = { newValue ->
//                            quantityValues = quantityValues.toMutableMap().apply {
//                                put(pair.voucherNum to pair.bagSize, newValue)
//                            }
//                        },
//                        modifier = Modifier
//                            .width(80.dp)
//                            .height(40.dp)
//                            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
//                        textStyle = TextStyle(
//                            textAlign = TextAlign.Center,
//                            fontSize = 12.sp
//                        ),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        maxLines = 1,
//                        decorationBox = { innerTextField ->
//                            Box(
//                                contentAlignment = Alignment.Center,
//                                modifier = Modifier.fillMaxSize()
//                            ) {
//                                innerTextField()
//                            }
//                        }
//                    )
                    val coroutineScope = rememberCoroutineScope()
                    var debounceJob by remember { mutableStateOf<Job?>(null) }
                    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

                    Column {
                        if (textFieldValue.text.toIntOrNull() != null && textFieldValue.text.toIntOrNull()!! > pair.currentQuantity.toInt()) {
                            Text(text = "*Error", color = primeRed, fontSize = 11.sp)
                        }


                    BasicTextField(value = textFieldValue, onValueChange = { newValue ->
                        textFieldValue = newValue

                        debounceJob?.cancel()
                        debounceJob = coroutineScope.launch {
                            delay(100)
                            if (textFieldValue.text.toIntOrNull() != null && textFieldValue.text.toIntOrNull()!! < pair.currentQuantity.toInt()) {

                                val existingItem = outgoingResponseBody.find { it.orderId == pair.orderId }

                                if (existingItem != null) {
                                    val existingBagSize = existingItem.bagUpdates.find { it?.size == pair.size }

                                    if (existingBagSize != null) {
                                        val updatedBagWithOldBag = existingBagSize.copy(quantityToRemove = textFieldValue.text.toInt())
                                        val updatedBagUpdates = existingItem.bagUpdates.toMutableList().apply {
                                            val indexOfBag = indexOf(existingBagSize)
                                            if (indexOfBag != -1) this[indexOfBag] = updatedBagWithOldBag
                                        }
                                        val updatedElement = existingItem.copy(bagUpdates = updatedBagUpdates)
                                        val index = outgoingResponseBody.indexOf(existingItem)
                                        if (index != -1) outgoingResponseBody[index] = updatedElement
                                    } else {
                                        val updatedBagUpdates = existingItem.bagUpdates.toMutableList().apply {
                                            pair.size?.let { add(BagUpdate(size = it, quantityToRemove = textFieldValue.text.toInt())) }
                                        }

                                        val updatedElement = existingItem.copy(bagUpdates = updatedBagUpdates)
                                        val index = outgoingResponseBody.indexOf(existingItem)
                                        if (index != -1) outgoingResponseBody[index] = updatedElement
                                    }
                                } else {
                                    // Add a new order with the bag size
                                    outgoingResponseBody.add(

                                        OutgoingDataClassItem(
                                                orderId = pair.orderId,
                                                variety = pair.variety,
                                                bagUpdates = listOf(
                                                    pair.size?.let { BagUpdate(size = it, quantityToRemove = textFieldValue.text.toInt()) }
                                                )
                                            )
                                    )


                                }


                            } else{
                                Toast.makeText(context, "Please a enter a value less than ${pair.currentQuantity.toInt()+1}!", Toast.LENGTH_SHORT).show()

                            }
                            //keyboardController?.hide()

                        }
                    },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        // Set font size to 11.sp


                        modifier = Modifier
                            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                            .width(134.dp)
                            .height(40.dp)
                            .padding(vertical = 2.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (textFieldValue.text.toIntOrNull() != null && textFieldValue.text.toIntOrNull()!! < pair.currentQuantity.toInt()) {

                                    keyboardController?.hide()
                               val existingItem =      outgoingResponseBody.find {it.orderId == pair.orderId}

                                if(existingItem!= null){
                                val updatedBagUpdates =     existingItem.bagUpdates.toMutableList().apply {
                                        this.add(pair.size?.let { BagUpdate(size = it, quantityToRemove = textFieldValue.text.toInt() ) })
//                                    pair.size?.let {
//                                        add(BagUpdate(size = it, quantityToRemove = textFieldValue.text.toInt()))
//                                    }

                                    }

                                val updatedElement = existingItem.copy(bagUpdates = updatedBagUpdates)
                                    val index = outgoingResponseBody.indexOf(existingItem)
                                    outgoingResponseBody[index] = updatedElement
                                }else{
                                    outgoingResponseBody.add(
                                        OutgoingDataClassItem(
                                            orderId = pair.orderId,
                                            variety = pair.variety,
                                            bagUpdates = listOf(pair.size?.let {
                                                BagUpdate(
                                                    it,
                                                    textFieldValue.text.toInt()
                                                )
                                            })
                                        )
                                    )
                                }


                                } else{
                                    Toast.makeText(context, "Please a enter a value less than ${pair.currentQuantity.toInt()+1}!", Toast.LENGTH_SHORT).show()

                                }
                            }
                        ), singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                innerTextField()
                            }
                        })
                }

                    var lastChangeTime by remember { mutableStateOf(System.currentTimeMillis()) }
                    val typingTimeout = 300L // Timeout in milliseconds
//                    LaunchedEffect(textFieldValue ){
//                        lastChangeTime = System.currentTimeMillis()
//                    }
//                    LaunchedEffect(lastChangeTime) {
//                        delay(typingTimeout)
//                        if (System.currentTimeMillis() >= lastChangeTime + typingTimeout) {
//                            outgoingResponseBody.add(
//                                OutgoingDataClassItem(
//                                    orderId = pair.orderId,
//                                    variety = pair.variety,
//                                    bagUpdates = listOf(pair.size?.let { BagUpdate(it, textFieldValue.text.toInt()) })
//                                )
//                            )                        }
//                    }


                }

    }}
            
            Spacer(modifier = Modifier.padding(27.dp))

            // add remarks here
            Text(text = "Remarks",  fontSize = 14.sp, fontWeight = FontWeight.Medium)
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