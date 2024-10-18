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
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                viewmodel.confirmOutgoingOrder("66eab27610eb613c2efca3bc" )  },
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

    LaunchedEffect(Unit){

        viewmodel.getAllRecipts("66eab27610eb613c2efca3bc")

        Log.d("OutgoingSh" , viewmodel.getTheSelectedStock().toString())
        Log.d("OutgoingSh" , viewmodel.getTheSelectedIndex().toString())

    }

    val SelectedVoucherNumber = viewmodel.getTheSelectedStock()
    val selectedColumns = viewmodel.getTheSelectedIndex()
    var rows = mutableListOf<forSecondOutgoingPage>()


    when(val state = transactionHistory.value){
        is getAllReciptsResponse.success ->{
            val filteredData = state.reciptData.filter { recipt ->
                recipt.voucher.voucherNumber.toString() in SelectedVoucherNumber
            }
            rows = mapDataForSecondOutgoingPage(filteredData)
        }
        else ->{
            Log.d("Outgoing second " , "XXXXXX")
        }
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

    var quantityValues by remember { mutableStateOf(data.map { it[4] }) }

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
                    modifier = Modifier.weight(1f)
                )
            }
        }
        LazyColumn {
            items(rows.size) { index  ->
                val row = rows[index]
                selectedColumns.forEachIndexed { index, s ->

                Row(modifier = Modifier.fillMaxWidth()) {
//                    row.forEachIndexed  { Cellindex ,  cell ->
//
//                        when {
//                            Cellindex == 4 ->{
//                                //on change methods
//                                BasicTextField(
//                                    value = quantityValues[index] ,
//                                    onValueChange = { newValue ->
//                                        quantityValues = quantityValues.toMutableList().also { it[index] = newValue }
//                                    },
//                                    modifier = Modifier
//                                        .width(110.dp)
//                                        .height(40.dp)
//                                        .padding(vertical = 4.dp)
//                                        .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
//                                    //.padding(horizontal = 8.dp, vertical = 12.dp), // Adjust padding to vertically center the text
//
//                                    , textStyle = TextStyle(textAlign = TextAlign.Center),
//                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                                    , maxLines = 1 ,
//                                    decorationBox = { innerTextField ->
//                                        Box(
//                                            contentAlignment = Alignment.Center,
//                                            modifier = Modifier.fillMaxSize()
//                                        ) {
//                                            innerTextField()
//                                        }
//                                    }
//                                )
//                            } else ->  Text(
//                            text = cell,
//                            modifier = Modifier.weight(1f)
//                        )
//                        }
//
//                    }



                        Text(
                            text = row.dateOfSubmission,
                            modifier = Modifier.weight(1f)
                        )


                        Text(
                            text = row.address.chamber + "-" + row.address.floor + "-" + row.address.row,
                            modifier = Modifier.weight(1f)
                        )
                        val variety = if(s == "2") "Seed"
                        else if (s == "3") "number-12"
                        else if(s == "4") "Ration"
                        else if (s =="5") "Goli"
                        else "Cut & Tok"
                    val matchingBagSize = row.bagsizeData.find { it.size == variety }
          Log.d("Ouut" ,matchingBagSize.toString() + row.bagsizeData.toString() )
                        Text(
                            text = matchingBagSize?.size ?: "",
                            modifier = Modifier.weight(1f)
                        )
                    Text(
                        text = matchingBagSize?.quantity?.currentQuantity?.toString() ?: "0", // Display currentQuantity or 0 if no match
                        modifier = Modifier.weight(1f)
                    )
                        BasicTextField(
                            value = quantityValues[index],
                            onValueChange = { newValue ->
                                quantityValues =
                                    quantityValues.toMutableList().also { it[index] = newValue }
                            },
                            modifier = Modifier
                                .width(110.dp)
                                .height(40.dp)
                                .padding(vertical = 4.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                            //.padding(horizontal = 8.dp, vertical = 12.dp), // Adjust padding to vertically center the text

                            ,
                            textStyle = TextStyle(textAlign = TextAlign.Center),
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
            }
        }
    }
}