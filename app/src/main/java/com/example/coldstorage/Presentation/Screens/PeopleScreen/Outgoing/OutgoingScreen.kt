package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.OutgoingData.BagUpdate
import com.example.coldstorage.DataLayer.Api.OutgoingData.MainOutgoingOrderClass
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ClickableBlock
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpTextField
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Util.outgoingEntry
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.ReceiptRow
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.SelectedCellData
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.mapReceiptsToRows
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutgoingStockScreen(fromDaybook: Boolean,accNum: String ,viewmodel: FunctionStoreOwner = hiltViewModel() ,navController: NavController) {
    var selectedVariety by remember { mutableStateOf("") }
    var selectedBagSize by remember { mutableStateOf("") }
    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit ){
        Log.d("fromDayBook" ,fromDaybook.toString())
        if(fromDaybook) {
            viewmodel.resetSearchResult()
        }
    }
    val isNameSelected = remember { mutableStateOf(false) }
     if(fromDaybook){
    LaunchedEffect(query){
        if(fromDaybook){
            viewmodel.getAllVarieties(viewmodel.farmerAcc.value)
        }else{
            viewmodel.getAllVarieties(accNum)}
    }
} else{
    LaunchedEffect(Unit){
        if(fromDaybook){
            viewmodel.getAllVarieties(viewmodel.farmerAcc.value)
        }else{
            Log.d("qwertyuiop" ,accNum)
            viewmodel.getAllVarieties(accNum)}
    }
}


    val searchResults = viewmodel.searchResults.collectAsState()

    val allVarieties = viewmodel.allVarieties.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Outgoing Stock") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()
//                    viewmodel.searchResults.collectAsState() = emptyList()
                        viewmodel.resetSearchResult()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .padding(16.dp)

        ) {

            if(fromDaybook){
                Text(text = "Enter Account Name (search and select)")
                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewmodel.onSearchQuery(query)
                    },
                    label = { Text("Search farmers") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = Color.Black, // Text color inside the TextField
                        disabledTextColor = Color.Gray,
                        cursorColor = Color.Black, // Cursor color
                        containerColor = Color.Transparent ,
                        errorCursorColor = Color.Red, // Cursor color in error state
                        focusedIndicatorColor = primeGreen, // Border color when focused
                        unfocusedIndicatorColor = Color.Gray, // Border color when not focused
                        errorIndicatorColor = Color.Red, // Border color in error state
                        focusedLeadingIconColor = Color.Black,
                        focusedTrailingIconColor = Color.Black,
                        focusedLabelColor = primeGreen, // Label color when focused
                        unfocusedLabelColor = Color.Gray, // Label color when not focused
                        errorLabelColor = Color.Red // Label color in error state
                    )
                )


            LazyColumn(){
                if (!isNameSelected.value && query.length>2) {
//            item{
//                Column {
//
//                    Text("New Farmer")
//                }
//            }
//            if(searchResults.value.size < 1) {
//               item{
//
//                   Column {
//
//
//                ColdOpTextField(value = accountHolderName.value, onValueChange = {
//                    accountHolderName.value = it
//                } , placeholder = "Enter name" )
//                       Spacer(modifier = Modifier.padding(10.dp))
//                   Row {
//
//
//                       ColdOpTextField(value = mobileNumber.value, onValueChange = {
//                           mobileNumber.value = it
//                       } , modifier = Modifier.weight(.75f), placeholder = "Enter mobile number")
//                       Button(onClick = { query = accountHolderName.value
//                                        isNameSelected.value = true }, enabled = accountHolderName.value.length > 0 && mobileNumber.value.length == 10
//
//                           , colors = ButtonColors(containerColor = primeGreen , contentColor = Color.White , disabledContainerColor = Color.Gray , disabledContentColor = Color.White)) {
//                           Text(text = "Save")
//                       }
//
//                   }
//
//                   }
//            }
//
//
//
//
//            }
                    item{
                        Surface(
                            color= Color.White,
                            tonalElevation = 10.dp,
                            shadowElevation = 10.dp,
                            border= BorderStroke(2.dp, primeGreen )
                        ) {
                            if(searchResults.value.isNotEmpty()){
//                        LazyColumn{
//                            items(searchResults.value) { result ->
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .drawBehind {
//                                            drawLine(
//                                                color = Color.Gray,
//                                                start = Offset(0f, size.height),
//                                                end = Offset(size.width, size.height),
//                                                strokeWidth = 1.dp.toPx()
//                                            )
//                                        }
//                                        .clickable {
//                                            query = result.name
//                                            isNameSelected.value = true
//                                            viewmodel.updateFarmerAcc(result._id)
//                                        }
//                                        .padding(vertical = 8.dp)
//                                ) {
//                                    Text(text = result.name)
//                                    Text(text = result.mobileNumber)
//                                }
//                            }
//
//                        }
                                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    searchResults.value.forEach {result->
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color.White)

                                                .drawBehind {
                                                    drawLine(
                                                        color = Color.Gray,
                                                        start = Offset(0f, size.height),
                                                        end = Offset(size.width, size.height),
                                                        strokeWidth = 1.dp.toPx()
                                                    )
                                                }
                                                .clickable {
                                                    query = result.name
                                                    isNameSelected.value = true
                                                    viewmodel.updateFarmerAcc(result._id)
                                                }
                                                .padding(vertical = 8.dp)
                                        ) {
                                            Text(text = result.name)
                                            Text(text = result.mobileNumber)
                                        }
                                    }

                                }
                            } else{
                                Text(text = "No farmer found!")
                                Log.d("ghghgfh" , "NO search results")
                            }

                        }

                    }
                }

            }}



            DropdownMenu_(
                label = "Which variety would you like to take?",
                options = if(allVarieties.value.status!="") allVarieties.value.varieties else listOf("Please select farmer"),
                selectedOption = selectedVariety,
                onOptionSelected = { selectedVariety = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

//            DropdownMenu_(
//                label = "Which bag size would you like to take?",
//                options = listOf("All"),
//                selectedOption = selectedBagSize,
//                onOptionSelected = { selectedBagSize = it }
//            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("See available options", fontWeight = FontWeight.Bold)

           //StockTable()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                StockTable(selectedVariety ,fromDaybook, accNum, viewmodel, navController)
            }






        }

    }
}

@Composable
fun DropdownMenu_(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOption.ifEmpty { "Select" })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }    , text = {Text(option , textAlign = TextAlign.Center) } ,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun StockTable(selectedVariety:String ,fromDaybook: Boolean,accNum: String,viewmodel: FunctionStoreOwner  , navController: NavController) {
    val headers = listOf("V No.", "Variety", "Seed", "Goli", "Ration","Cut&Tok", "No.12")
    val selectedBlock =  remember { mutableStateOf(Color.White) }
   val selectedCells  = remember {
       mutableStateMapOf<Pair<Int , Int
               > , Boolean>()
   }
    val transactionAllHistory = viewmodel.transactionHistory.collectAsState() // to learn
    LaunchedEffect(Unit ){
        Log.d("fromDayBook" ,fromDaybook.toString())

    }
//    if(fromDaybook){
//        LaunchedEffect(selectedVariety){
//
//            if(fromDaybook){
//                viewmodel.getAllRecipts(viewmodel.farmerAcc.value)
//            } else{
//                viewmodel.getAllRecipts(accNum)
//
//            }
//        }
//
//    } else{
//        LaunchedEffect(Unit){
//
//            if(fromDaybook){
//                viewmodel.getAllRecipts(viewmodel.farmerAcc.value)
//            } else{
//                viewmodel.getAllRecipts(accNum)
//
//            }
//        }
//
//    }
    LaunchedEffect(selectedVariety){

        if(fromDaybook){
            viewmodel.getAllRecipts(viewmodel.farmerAcc.value)
        } else{
            viewmodel.getAllRecipts(accNum)

        }
    }

    val selectedCellsList = remember { mutableStateListOf<SelectedCellData>() }
    val outgoingResponseBody = remember {
        mutableListOf<OutgoingDataClassItem>()
    }
    val mainOutgoingBody: MutableState<MainOutgoingOrderClass>  = remember {
        mutableStateOf(MainOutgoingOrderClass(remarks = "" , orders = emptyList() ))    }
    val OutgoingOrderLoader by viewmodel.outgoingOrderLoader.collectAsState()

    val OutgoingOrderStatus by viewmodel.outgoingOrderStatus.collectAsState()
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

    var rows by mutableStateOf<List<ReceiptRow>>(emptyList())

    when(val state = transactionAllHistory.value) {
        is getAllReciptsResponse.success -> {
            rows  = mapReceiptsToRows(state.reciptData)
            Log.d("OutgoingTableeeeee" , rows.toString())
            Log.d("OutgoingTable" , "XXxxxxxxxxxxxxxxxx1123456789")

        }
        else -> {
            Log.d("OutgoingTable" , state.toString())
        }
    }
    Log.d("OutgoingTable" , rows.toString())

//    fun saveInTheList(cellData : SelectedCellData){
//
//
//    }
Column(modifier = Modifier
    .fillMaxHeight()
    ) {


    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        ) {
        item {
            Row(Modifier.fillMaxWidth()) {
                headers.forEach { header ->
                    Log.d("OutgoingTable", rows.toString())

                    Text(
                        text = header,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,

                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
//val _row = rows.filter{ it.variety == selectedVariety}
       // if(_row != null){
        items(rows.size) { rowIndex ->
            val row = rows.filter { it.variety == selectedVariety }.getOrNull(rowIndex)
            //val row = _row[rowIndex]
            Log.d("Outgoingggrgrgrgrgrgrgr", row.toString())
if(row!= null){
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = row.voucherNumber.toString(), // First column - voucher number
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(15.dp)
                       // .background(primeRed)
                    ,                    fontSize = 11.sp

                )

                Text(
                    text = row.variety, // Second column - variety
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(50.dp)
                        ,
                    fontSize = 11.sp
                )
//                listOf(row.size, row.currentQuantity /* Add other fields if needed */).forEachIndexed { index, cell ->
//                    Log.d("Outgoing" , index.toString())
//
//                    ClickableBlock(
//                        cell = cell.toString(),
//                        isSelected = selectedCells[Pair(rowIndex, index + 2)] ?: false, // Use +2 to skip the first two columns
//                        onToggle = { isSelected ->
//                            selectedCells[Pair(rowIndex, index + 2)] = isSelected
//                        }
//                    )
//                }

                val openDailogForQtyRemovedZero = remember {
                    mutableStateOf(false)
                }
                val qtyToRemoveZero = remember{
                    mutableStateOf("0")
                }
                ClickableBlock(
                    cell = row.size.getOrNull(0)?.quantity?.currentQuantity?.toString() ?: "0",
                    cellTwo = row.size.getOrNull(0)?.quantity?.initialQuantity?.toString() ?: "0",
                    qtyToRemove = qtyToRemoveZero.value ,

                    isSelected = selectedCells[Pair(rowIndex, 2)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 2)] = isSelected
                        openDailogForQtyRemovedZero.value = true
                    } ,saveSelected = {
                       // if(selectedCells[Pair(rowIndex, 2)] == true){
                            if(selectedCellsList.contains( SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(0)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(0)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))){
                                selectedCellsList.remove(SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(0)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(0)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))
                                } else{
                                selectedCellsList.add(
                                    SelectedCellData(
                                        orderId = row.orderId,
                                        voucherNumber = row.voucherNumber,
                                        variety = row.variety,
                                        size = row.size.getOrNull(0)?.size?.toString(),
                                        address = row.address,
                                        dateOfSubmission = row.dateOfSubmission,
                                        currentQuantity = row.size.getOrNull(0)?.quantity?.currentQuantity?.toString() ?: "0"

                                    )
                                )
                                }

                      //  }

                    }
                )
                if(openDailogForQtyRemovedZero.value){
                    AlertDialog(onDismissRequest = { openDailogForQtyRemovedZero.value = false }, confirmButton = { /*TODO*/ } , text={
                         Column {
                             Text(text = "Order id")
                             Text(text = row.orderId)
                             row.size.getOrNull(0)?.size?.let { Text(text = it) }
                             ColdOpTextField(value = qtyToRemoveZero.value , onValueChange = {
                                  qtyToRemoveZero.value = it
                             })
                             Button(onClick = {
//                                 outgoingResponseBody.add(
//
//                                 OutgoingDataClassItem(
//                                     orderId = row.orderId,
//                                     variety = row.variety,
//                                     bagUpdates = listOf(
//                                        // row.size?.let { BagUpdate(size = it, quantityToRemove = qtyToRemoveZero.value.toInt()) }
//                                         row.size.getOrNull(0)
//                                             ?.let { BagUpdate(size = it.size , quantityToRemove =qtyToRemoveZero.value.toInt()  ) }
//                                     )
//                                 )
//                             )
                                 CoroutineScope(Dispatchers.Main).launch{
                                     outgoingEntry(qtyToRemoveZero ,row , outgoingResponseBody , 0 )
                                     delay(300)
                                     openDailogForQtyRemovedZero.value = false
                                 }


                             }) {
                                 Text(text = "Save")
                             }
                         }


                    })


                }
                if(qtyToRemoveZero.value !== "0"){
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Badge size
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = qtyToRemoveZero.value,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }








                val openDailogForQtyRemovedOne = remember {
                    mutableStateOf(false)
                }
                val qtyToRemoveOne = remember{
                    mutableStateOf("0")
                }
                ClickableBlock(
                    cell = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0",
                    cellTwo = row.size.getOrNull(1)?.quantity?.initialQuantity?.toString() ?: "0",
                    qtyToRemove = qtyToRemoveOne.value ,

                    isSelected = selectedCells[Pair(rowIndex, 3)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 3)] = isSelected
                        openDailogForQtyRemovedOne.value = true

                    },
                    saveSelected =  {
                      //  if(selectedCells[Pair(rowIndex, 3)] == true){
                            if(selectedCellsList.contains( SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(1)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))){
                                selectedCellsList.remove(SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(1)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))
                            } else{
                                selectedCellsList.add(
                                    SelectedCellData(
                                        orderId = row.orderId,
                                        voucherNumber = row.voucherNumber,
                                        variety = row.variety,
                                        size = row.size.getOrNull(1)?.size?.toString(),
                                        address = row.address,
                                        dateOfSubmission = row.dateOfSubmission,
                                        currentQuantity = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0"

                                    )
                                )
                            }

                       // }

                    }
                )
                if(openDailogForQtyRemovedOne.value){
                    AlertDialog(onDismissRequest = { openDailogForQtyRemovedOne.value = false }, confirmButton = { /*TODO*/ } , text={
                        Column {
                            Text(text = "Order id")
                            Text(text = row.orderId)
                            row.size.getOrNull(1)?.size?.let { Text(text = it) }
                            ColdOpTextField(value = qtyToRemoveOne.value , onValueChange = {
                                qtyToRemoveOne.value = it
                            })
                            Button(onClick = {
//                                outgoingResponseBody.add(
//
//                                OutgoingDataClassItem(
//                                    orderId = row.orderId,
//                                    variety = row.variety,
//                                    bagUpdates = listOf(
//                                        // row.size?.let { BagUpdate(size = it, quantityToRemove = qtyToRemoveZero.value.toInt()) }
//                                        row.size.getOrNull(1)
//                                            ?.let { BagUpdate(size = it.size , quantityToRemove =qtyToRemoveZero.value.toInt()  ) }
//                                    )
//                                )
//                            )
                               // outgoingEntry(qtyToRemoveOne ,row , outgoingResponseBody , 1 )
                                CoroutineScope(Dispatchers.Main).launch{


                                    outgoingEntry(qtyToRemoveOne ,row , outgoingResponseBody , 1 )
                                    delay(300)
                                    openDailogForQtyRemovedOne.value = false
                                }


                            }) {
                                Text(text = "Save")
                            }
                        }


                    })


                }
                if(qtyToRemoveOne.value !== "0"){
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Badge size
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = qtyToRemoveOne.value,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }


                val openDailogForQtyRemovedTwo = remember {
                    mutableStateOf(false)
                }
                val qtyToRemoveTwo = remember{
                    mutableStateOf("0")
                }

                ClickableBlock(
                    cell = row.size.getOrNull(2)?.quantity?.currentQuantity?.toString() ?: "0",
                    cellTwo = row.size.getOrNull(2)?.quantity?.initialQuantity?.toString() ?: "0",
                    qtyToRemove = qtyToRemoveTwo.value ,

                    isSelected = selectedCells[Pair(rowIndex, 4)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 4)] = isSelected
                        openDailogForQtyRemovedTwo.value = true

                    },
                    saveSelected = {
                       // if(selectedCells[Pair(rowIndex, 4)] == true){
                            if(selectedCellsList.contains( SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(2)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(2)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))){
                                selectedCellsList.remove(SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(2)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(2)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))
                            } else{
                                selectedCellsList.add(
                                    SelectedCellData(
                                        orderId = row.orderId,
                                        voucherNumber = row.voucherNumber,
                                        variety = row.variety,
                                        size = row.size.getOrNull(2)?.size?.toString(),
                                        address = row.address,
                                        dateOfSubmission = row.dateOfSubmission,
                                        currentQuantity = row.size.getOrNull(2)?.quantity?.currentQuantity?.toString() ?: "0"

                                    )
                                )
                            }

                       // }

                    }
                )
                if(openDailogForQtyRemovedTwo.value){
                    AlertDialog(onDismissRequest = { openDailogForQtyRemovedTwo.value = false }, confirmButton = { /*TODO*/ } , text={
                        Column {
                            Text(text = "Order id")
                            Text(text = row.orderId)
                            row.size.getOrNull(2)?.size?.let { Text(text = it) }
                            ColdOpTextField(value = qtyToRemoveTwo.value , onValueChange = {
                                qtyToRemoveTwo.value = it
                            })
                            Button(onClick = {
//                                outgoingResponseBody.add(
//
//                                OutgoingDataClassItem(
//                                    orderId = row.orderId,
//                                    variety = row.variety,
//                                    bagUpdates = listOf(
//                                        // row.size?.let { BagUpdate(size = it, quantityToRemove = qtyToRemoveZero.value.toInt()) }
//                                        row.size.getOrNull(2)
//                                            ?.let { BagUpdate(size = it.size , quantityToRemove =qtyToRemoveTwo.value.toInt()  ) }
//                                    )
//                                )
//                            )
                                //outgoingEntry(qtyToRemoveTwo ,row , outgoingResponseBody , 2 )
                                CoroutineScope(Dispatchers.Main).launch{
                                    outgoingEntry(qtyToRemoveTwo ,row , outgoingResponseBody , 2 )
                                    delay(300)
                                    openDailogForQtyRemovedTwo.value = false
                                }

                            }) {
                                Text(text = "Save")
                            }
                        }


                    })


                }
                if(qtyToRemoveTwo.value !== "0"){
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Badge size
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = qtyToRemoveTwo.value,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }


                val openDailogForQtyRemovedThree = remember {
                    mutableStateOf(false)
                }
                val qtyToRemoveThree = remember{
                    mutableStateOf("0")
                }

                ClickableBlock(
                    cell = row.size.getOrNull(3)?.quantity?.currentQuantity?.toString() ?: "0",
                    cellTwo = row.size.getOrNull(3)?.quantity?.initialQuantity?.toString() ?: "0",
                    qtyToRemove = qtyToRemoveThree.value ,

                    isSelected = selectedCells[Pair(rowIndex, 5)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 5)] = isSelected
                        openDailogForQtyRemovedThree.value = true

                    },
                    saveSelected = {
                       // if(selectedCells[Pair(rowIndex, 5)] == true){
                            if(selectedCellsList.contains( SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(3)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(3)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))){
                                selectedCellsList.remove(SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(3)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(3)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))
                            } else{
                                selectedCellsList.add(
                                    SelectedCellData(
                                        orderId = row.orderId,
                                        voucherNumber = row.voucherNumber,
                                        variety = row.variety,
                                        size = row.size.getOrNull(3)?.size?.toString(),
                                        address = row.address,
                                        dateOfSubmission = row.dateOfSubmission,
                                        currentQuantity = row.size.getOrNull(3)?.quantity?.currentQuantity?.toString() ?: "0"

                                    )
                                )
                            }

                       // }

                    }
                )
                //Spacer(modifier = Modifier.padding(start = 3.dp))
                if(openDailogForQtyRemovedThree.value){
                    AlertDialog(onDismissRequest = { openDailogForQtyRemovedThree.value = false }, confirmButton = { /*TODO*/ } , text={
                        Column {
                            Text(text = "Order id")
                            Text(text = row.orderId)
                            row.size.getOrNull(3)?.size?.let { Text(text = it) }
                            ColdOpTextField(value = qtyToRemoveThree.value , onValueChange = {
                                qtyToRemoveThree.value = it
                            })
                            Button(onClick = {

//                                outgoingResponseBody.add(
//
//                                OutgoingDataClassItem(
//                                    orderId = row.orderId,
//                                    variety = row.variety,
//                                    bagUpdates = listOf(
//                                        // row.size?.let { BagUpdate(size = it, quantityToRemove = qtyToRemoveZero.value.toInt()) }
//                                        row.size.getOrNull(3)
//                                            ?.let { BagUpdate(size = it.size , quantityToRemove =qtyToRemoveThree.value.toInt()  ) }
//                                    )
//                                )
//                            )
                               // outgoingEntry(qtyToRemoveThree ,row , outgoingResponseBody , 3 )
                                CoroutineScope(Dispatchers.Main).launch{
                                    outgoingEntry(qtyToRemoveThree ,row , outgoingResponseBody , 3 )
                                    delay(300)
                                    openDailogForQtyRemovedThree.value = false
                                }

                            }) {
                                Text(text = "Save")
                            }
                        }


                    })


                }
                if(qtyToRemoveThree.value !== "0"){
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Badge size
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = qtyToRemoveThree.value,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }




                val openDailogForQtyRemovedFour = remember {
                    mutableStateOf(false)
                }
                val qtyToRemoveFour = remember{
                    mutableStateOf("0")
                }

                ClickableBlock(
                    cell = row.size.getOrNull(4)?.quantity?.currentQuantity?.toString() ?: "0",
                    cellTwo = row.size.getOrNull(4)?.quantity?.initialQuantity?.toString() ?: "0",
                    qtyToRemove = qtyToRemoveFour.value ,
                    isSelected = selectedCells[Pair(rowIndex, 6)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 6)] = isSelected
                        openDailogForQtyRemovedFour.value = true

                    },saveSelected =  {
                       // if(selectedCells[Pair(rowIndex, 6)] == true){
                            if(selectedCellsList.contains( SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(4)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(4)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))){
                                selectedCellsList.remove(SelectedCellData(
                                    orderId = row.orderId,
                                    voucherNumber = row.voucherNumber,
                                    variety = row.variety,
                                    size = row.size.getOrNull(4)?.size?.toString(),
                                    address = row.address,
                                    dateOfSubmission = row.dateOfSubmission,
                                    currentQuantity = row.size.getOrNull(4)?.quantity?.currentQuantity?.toString() ?: "0"

                                ))
                            } else{
                                selectedCellsList.add(
                                    SelectedCellData(
                                        orderId = row.orderId,
                                        voucherNumber = row.voucherNumber,
                                        variety = row.variety,
                                        size = row.size.getOrNull(4)?.size?.toString(),
                                        address = row.address,
                                        dateOfSubmission = row.dateOfSubmission,
                                        currentQuantity = row.size.getOrNull(4)?.quantity?.currentQuantity?.toString() ?: "0"

                                    )
                                )
                            }

                       // }

                    }
                )
                if(openDailogForQtyRemovedFour.value){
                    AlertDialog(onDismissRequest = { openDailogForQtyRemovedFour.value = false }, confirmButton = { /*TODO*/ } , text={
                        Column {
                            Text(text = "Order id")
                            Text(text = row.orderId)
                            row.size.getOrNull(4)?.size?.let { Text(text = it) }
                            ColdOpTextField(value = qtyToRemoveFour.value , onValueChange = {
                                qtyToRemoveFour.value = it
                            })
                            Button(onClick = {
//                                outgoingResponseBody.add(
//
//                                OutgoingDataClassItem(
//                                    orderId = row.orderId,
//                                    variety = row.variety,
//                                    bagUpdates = listOf(
//                                        // row.size?.let { BagUpdate(size = it, quantityToRemove = qtyToRemoveZero.value.toInt()) }
//                                        row.size.getOrNull(4)
//                                            ?.let { BagUpdate(size = it.size , quantityToRemove =qtyToRemoveFour.value.toInt()  ) }
//                                    )
//                                )
//                            )
                             // outgoingEntry(qtyToRemoveFour ,row , outgoingResponseBody , 4 )
                                CoroutineScope(Dispatchers.Main).launch{
                                    outgoingEntry(qtyToRemoveFour ,row , outgoingResponseBody , 4 )
                                    delay(300)
                                    openDailogForQtyRemovedFour.value = false
                                }



                            }) {
                                Text(text = "Save")
                            }
                        }


                    })


                }
                if(qtyToRemoveFour.value !== "0"){
                    Box(
                        modifier = Modifier
                            .size(20.dp) // Badge size
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = qtyToRemoveFour.value,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

//                val totalQuantity = row.size
//                    .take(5) // Take the first 5 elements or fewer if the list is smaller
//                    .sumOf {
//                        it.quantity?.currentQuantity ?: 0
//                    } // Safely access currentQuantity and sum them
//                val totalQuantityTwo = row.size
//                    .take(5) // Take the first 5 elements or fewer if the list is smaller
//                    .sumOf {
//                        it.quantity?.initialQuantity ?: 0
//                    }
               // Spacer(modifier = Modifier.padding(start = 10.dp))
//                ClickableBlock(
//                    cell = totalQuantity.toString(),
//                    cellTwo = totalQuantityTwo.toString(),
//                    qtyToRemove = "X" ,
//
//                    isSelected = selectedCells[Pair(rowIndex, 7)]
//                        ?: false, // Use +2 to skip the first two columns
//                    onToggle = { isSelected ->
//                        selectedCells[Pair(rowIndex, 7)] = isSelected
//                    },saveSelected = {
////                        viewmodel.saveSelectedCellData(
////                            orderId = row.orderId,
////                            voucherNumber = row.voucherNumber,
////                            variety = row.variety,
////                            size = "",
////                            address = row.address,
////                            dateOfSubmission = row.dateOfSubmission,
////                            currentQuantity = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0"
////
////                        )
//                    }
//                )

            }} else{
                //Text(text = "Please select size!")
                Log.d("NUllllll" , "No size selected")
            }
        }

//        } else{
//        }
        }
 if(!rows.filter { it.variety == selectedVariety }.isNullOrEmpty()){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp) , horizontalArrangement = Arrangement.End) {
        Surface(modifier = Modifier
            .padding(horizontal = 7.dp, vertical = 3.dp)
            .background(
                Color(0xFF23C45E), RoundedCornerShape(5.dp)
            )
            .clickable {
//                val (firstKeys, secondKeys) = getSeparateKeys(selectedCells)
//                val finalVouchers =
//                    firstKeys.map { itt -> rows[itt.toInt()].voucherNumber.toString() }
//                viewmodel.proceedToNextOutgoing(finalVouchers, secondKeys)
//                Log.d("Next", "finalvouchers " + finalVouchers + "second keys " + secondKeys)
//                //Log.d("SelectedCellListttt",selectedCellsList)
//                viewmodel.saveSelectedCellData(selectedCellsList)
//
//                if (fromDaybook) {
//                    navController.navigate(AllScreens.OutgoingSecondScreen.name + "/${viewmodel.farmerAcc.value}")
//
//                } else {
//                    navController.navigate(AllScreens.OutgoingSecondScreen.name + "/${accNum}")
//                }

                mainOutgoingBody.value = MainOutgoingOrderClass(
                    remarks = "Added from new flow",
                    orders = outgoingResponseBody
                )
                try{viewmodel.confirmOutgoingOrderForUi(accNum ,mainOutgoingBody.value)

                    viewmodel.clearSelectedCellData()
                    //viewmodel.clearSelectedCells()
                    Log.d("OutgoingInTry" , "Pressedsffd button")

                }catch (e:Exception){
                    Log.d("innnnnn" , e.message.toString())
                }
            }

            , shape = RoundedCornerShape(5.dp) , color =Color(0xFF23C45E) ){
//            Text(text = "Proceed" ,modifier = Modifier
//                .padding(horizontal = 7.dp, vertical = 3.dp) )
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
    }} else{
        Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center) {
           // Spacer(modifier = Modifier.padding(200.dp))
            Text(text = "Please select farmer and size!" , modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Center)
        }

    }

}
    //add button here


}


//@Preview
//@Composable
//fun OutgoingPreview(){
//    OutgoingStockScreen()
//}

fun getSeparateKeys(selectedCells: MutableMap<Pair<Int, Int>, Boolean>): Pair<List<String>, List<String>> {
    // Extract the first key and second key as separate lists
    val firstKeyList = selectedCells.keys.map { it.first.toString() }
    val secondKeyList = selectedCells.keys.map { it.second.toString() }

    // Return the two lists as separate lists
    return Pair(firstKeyList, secondKeyList)
}


//117