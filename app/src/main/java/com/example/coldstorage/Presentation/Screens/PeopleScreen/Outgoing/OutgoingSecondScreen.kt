package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Location
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.finalConfirmation
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.forSecondOutgoingPage
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.mapDataForSecondOutgoingPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutgoingSecondScreen(viewmodel: FunctionStoreOwner , navController: NavController) {
    var seedBags by remember { mutableStateOf("0") }
    var rationBags by remember { mutableStateOf("0") }
    var no12Bags by remember { mutableStateOf("0") }
    var cutTokBags by remember { mutableStateOf("0") }
    var goliBags by remember { mutableStateOf("0") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember {
      mutableStateOf(false)
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
        Text("Select Quantities Required for : Pukhraj")

        Spacer(modifier = Modifier.height(16.dp))

        InputField("Seed Bags", seedBags) { seedBags = it }
        InputField("Ration Bags", rationBags) { rationBags = it }
        InputField("No. 12 bags", no12Bags) { no12Bags = it }
        InputField("Cut & Tok Bags", cutTokBags) { cutTokBags = it }
        InputField("Goli Bags", goliBags) { goliBags = it }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.25f) // This makes the table take the remaining space
        ) {
            StockTablee(viewmodel)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("OutgoingSuccess" , "Pressed button")
                showBottomSheet.value = true
                viewmodel.confirmOutgoingOrder("66eab1db10eb613c2efca385" )
                      viewmodel.clearSelectedCells()},
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Proceed")
        }
    }
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

@Composable
fun StockTablee(viewmodel: FunctionStoreOwner) {
    val transactionHistory = viewmodel.transactionHistory.collectAsState()
    val outgoingItem by viewmodel.outgoingItemState

    LaunchedEffect(Unit){

        viewmodel.getAllRecipts("66eab1db10eb613c2efca385")

        Log.d("OutgoingSh" , viewmodel.getTheSelectedStock().toString())
        Log.d("OutgoingSh" , viewmodel.getTheSelectedIndex().toString())

    }

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
    when(val state = transactionHistory.value){
        is getAllReciptsResponse.success ->{
            val filteredData = state.reciptData.filter { recipt ->
                recipt.voucher.voucherNumber.toString() in SelectedVoucherNumber
            }
            Log.d("FilteredId" , filteredData.toString())
            rows = mapDataForSecondOutgoingPage(filteredData)
            Log.d("roowa" , rows.toString())
        }
        else ->{
            Log.d("Outgoing second " , "XXXXXX")
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


    val headers = listOf("Dated", "Address", "Bag Type", "Current bags", "Quantity Reqd.")
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


    val field  = remember{
        mutableStateOf("")
    }

    Column {
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
        LazyColumn {
            items(voucherBagSizePairs) { pair ->
                Log.d("Rowwww" ,voucherBagSizePairs.toString())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = pair.date,
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${pair.address.chamber}-${pair.address.floor}-${pair.address.row}",
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = pair.bagSize,
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = pair.currentQuantity.toString(),
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp
                    )
                    BasicTextField(
                        value = quantityValues[pair.voucherNum to pair.bagSize] ?: "",
                        onValueChange = { newValue ->
                            quantityValues = quantityValues.toMutableMap().apply {
                                put(pair.voucherNum to pair.bagSize, newValue)
                            }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(40.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        maxLines = 1,
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
}}}

data class VoucherBagSizePair(
    val orderId : String,
    val voucherNum: Int,
    val bagSize: String,
    val date: String,
    val address: Location,
    val currentQuantity: Int
)