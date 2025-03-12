package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.coldstorage.DataLayer.Api.ColdStorageInfo
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.CardComponentDaybook
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.AssignLocation
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpDropDown
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ConfirmationPageForOrder
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.EmailFilterDropdowns
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.LotDetailsDialogWrapper
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ManageStocks
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.finalConfirmation
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.AddressDetails
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.OtherDetails
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.StockDetails
import com.example.coldstorage.R
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.singleFarmerTransactionApiState
import kotlinx.coroutines.launch

data class Transaction(
    val stockDetails: StockDetails,
    val addressDetails: AddressDetails,
    val otherDetails: OtherDetails,
    val date: String,
    val receiptNo: String,
    val type: String
)

val dummyTransactions = listOf(
    Transaction(
        StockDetails("50", "40", "30", "10", "20", "150"),
        AddressDetails("A", "5", "29"),
        OtherDetails("Pukhraj", "150", "230/150"),
        "16.12.23",
        "230",
        "incoming"
    ),
    Transaction(
        StockDetails("45", "35", "25", "15", "30", "150"),
        AddressDetails("B", "3", "17"),
        OtherDetails("Kufri Jyoti", "120", "180/120"),
        "17.12.23",
        "231",
        "outgoing"
    ),
    Transaction(
        StockDetails("60", "50", "20", "5", "15", "150"),
        AddressDetails("C", "2", "41"),
        OtherDetails("Chipsona", "100", "200/100"),
        "18.12.23",
        "232",
        "incoming"
    ),
    Transaction(
        StockDetails("55", "45", "35", "5", "10", "150"),
        AddressDetails("D", "1", "33"),
        OtherDetails("Kufri", "130", "210/130"),
        "19.12.23",
        "233",
        "outgoing"
    ),
    Transaction(
        StockDetails("40", "30", "40", "20", "20", "150"),
        AddressDetails("E", "4", "22"),
        OtherDetails("Rosetta", "140", "220/140"),
        "20.12.23",
        "234",
        "incoming"
    )
)


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun storeOrRetrieve(accountNumber: String,totalIncoming:String? , totalOutgoing:String? , navController: NavHostController, viewmodel: FunctionStoreOwner = hiltViewModel()) {
    //variables for bottom sheet
    val transactionHistory = viewmodel.singleFarmerCard.collectAsState() // to learn
//755
    LaunchedEffect(Unit) {
        viewmodel.updateFarmerAcc(accountNumber)
        Log.d("currentfarmer" , "Account number is"+accountNumber)
        viewmodel.getSingleFarmerTransaction(accountNumber)
        viewmodel.getAllRecipts(accountNumber)

        //
    }

    LaunchedEffect( accountNumber){
        Log.d("current farmer" , "Account number is"+accountNumber)
    }


    //Log.d("TransactionhistoryUI", transactionHistory.value.toString())
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet = remember {
        mutableStateOf(false)
    }

    //For second bottom sheet
    val secondSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSecondBottomSheet = remember {
        mutableStateOf(false)
    }


    //For third bottom sheet
    val thirdSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showThirdBottomSheet = remember {
        mutableStateOf(false)
    }

    //For fourth bottom sheet
    val fourthSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showFourthBottomSheet = remember {
        mutableStateOf(false)
    }


    //for dailog box
    var selectedRow = remember { mutableStateOf<String?>(null) }
    var isDailogOpen = remember { mutableStateOf<Boolean>(false) }
    Log.d("PopOP", "OPOPO initial ${isDailogOpen.value}")

    val hideBottomSheet: () -> Unit = {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet.value = false
            }
            showSecondBottomSheet.value = true
        }
    }

    //hiding the second bottom sheet
    val hideSecondBottomSheet: () -> Unit = {
        scope.launch {
            secondSheetState.hide()
        }.invokeOnCompletion {
            if (!secondSheetState.isVisible) {
                showSecondBottomSheet.value = false
            }
            showFourthBottomSheet.value = true
        }
    }
    //hiding the third bottom sheet
    val hideThirdBottomSheet: () -> Unit = {
        scope.launch {
            thirdSheetState.hide()
        }.invokeOnCompletion {
            if (!thirdSheetState.isVisible) {
                showThirdBottomSheet.value = false
            }
            showFourthBottomSheet.value = true
        }
    }

    val hidefourthBottomSheet:()->Unit = {
        scope.launch {
            fourthSheetState.hide()
        }.invokeOnCompletion {
            if (!fourthSheetState.isVisible) {
                showFourthBottomSheet.value = false
            }}
    }
    var expandedGroupBy = remember { mutableStateOf(false) }
    var selectedGroupBy = remember { mutableStateOf("Group by") }
    var expandedSortBy = remember { mutableStateOf(false) }
    var selectedSortBy = remember { mutableStateOf("Sort by Date") }
    val fromDaybookValue = false
    val showEmptyBagAlert = remember{ mutableStateOf(false) }

    val stockSummary = viewmodel.stockSummary.collectAsState()
    val loadingStockSummary = viewmodel.loadingStockSummary.collectAsState()

    val typeOfCard = remember { mutableStateOf("all") }
    val sortingOrder = remember { mutableStateOf("latest") }
//    LaunchedEffect(Unit ){
//        viewmodel.getStockSummary(accountNumber)
//    }
    LaunchedEffect(typeOfCard.value , sortingOrder.value ){
        viewmodel.getSingleFarmerTransaction(accountNumber)
    }
   val sum =  stockSummary.value.totalInitialQuantity-stockSummary.value.totalQuantityRemoved
   var totalSum = (totalIncoming?.toInt() ?: 0) - (totalOutgoing?.toInt() ?: 0)
//var totalSum  = 0
    Column(modifier = Modifier.verticalScroll(enabled = true, state = rememberScrollState(),)) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.backicon),
                    contentDescription = "Back Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navController.popBackStack() }
                )
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = "More Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)

                )

            }
            Spacer(modifier = Modifier.padding(10.dp))

            Text(text = "Manage stocks", fontWeight = FontWeight.Bold, fontSize = 24.sp)


            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Summary", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(8.dp))

            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Criterion",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "No. of bags",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
                Row() {
                    Text(text = "Total bags incoming", modifier = Modifier.weight(2f))
                    if (totalIncoming != null) {
                        Text(text = totalIncoming, modifier = Modifier.weight(1f))
                    } else{
                        Text(text = "0", modifier = Modifier.weight(1f))

                    }
                }
                Row() {
                    Text(text = "Total bags outgoing", modifier = Modifier.weight(2f))
                    if (totalOutgoing != null) {
                        Text(text =totalOutgoing, modifier = Modifier.weight(1f))
                    } else{
                        Text(text = "0", modifier = Modifier.weight(1f))

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
                Row() {
                    Text(
                        text = "Current holdings",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                    Text(text = totalSum.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Actions", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(8.dp))

            Surface(modifier = Modifier
                .padding(vertical = 0.dp)
                .background(Color.Green)
                .clickable {
                    // showBottomSheet.value = true
                }) {
                Text(
                    text = "View/Download Ledger Report",
                    modifier = Modifier
                        .border(1.dp, Color(0xFF22C55E), RoundedCornerShape(10.dp))
                        .background(Color(0xFF22C55E), RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(35.dp)
                        .wrapContentSize(align = Alignment.Center),
                    color = Color.White,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        textDirection = TextDirection.Content
                    )
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {


                Surface(modifier = Modifier
                    .padding(vertical = 0.dp)
                    .background(Color.Green)
                    .clickable { showBottomSheet.value = true }) {
                    Text(
                        text = "Incoming",
                        modifier = Modifier
                            .border(1.dp, Color(0xFF22C55E), RoundedCornerShape(10.dp))
                            .background(Color(0xFF22C55E), RoundedCornerShape(10.dp))
                            .width(148.dp)
                            .height(35.dp)
                            .wrapContentSize(align = Alignment.Center),
                        color = Color.White,
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content
                        )
                    )
                }
                Surface(
                    modifier = Modifier
                        .padding()
                        .clickable {
                            // navController.navigate(AllScreens.OutgoingStockScreen.name+ "/${accNumber}")
                            if (totalSum > 0) {
                                val fromDaybook = false
                                navController.navigate(AllScreens.OutgoingStockScreen.name + "/$fromDaybook/$accountNumber")
                            } else {
                                showEmptyBagAlert.value = true
                            }

                        }
                ) {
                    Text(
                        text = "Outgoing", modifier = Modifier
                            .background(Color.Red, RoundedCornerShape(10.dp))

                            .width(148.dp)
                            .height(35.dp)
                            .wrapContentSize(align = Alignment.Center),
                        color = Color.White
                    )

                }

            }
            if(showEmptyBagAlert.value){
                AlertDialog(
                    onDismissRequest = { showEmptyBagAlert.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                           // viewModel.resetAddFarmerStatus()
                           // navController.popBackStack()
                            showEmptyBagAlert.value = false
                        }) {
                            Text("OK")
                        }
                    },
                    title = { Text(text = "No bags!") },
                    text = { Text(text = "No bags remaining") }
                )
            }
            //table starting here
            Spacer(modifier = Modifier.padding(15.dp))

            Text(text = "Transaction", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(8.dp))

//            EmailFilterDropdowns(
//                expandedGroupBy = expandedGroupBy,
//                selectedGroupBy = selectedGroupBy,
//                expandedSortBy = expandedSortBy,
//                selectedSortBy = selectedSortBy
//            )
            Row(modifier = Modifier.padding(horizontal = 10.dp) , horizontalArrangement = Arrangement.SpaceAround) {

                ColdOpDropDown(stateToUpdate = null , label = "Sort", options = listOf("Latest" , "Oldest"), onSelect = { selected -> sortingOrder.value = selected.toLowerCase()} )
                Spacer(modifier = Modifier.padding(8.dp))
                ColdOpDropDown(stateToUpdate = null, label = "Filter", options = listOf("Incoming" , "Outgoing" , "Show All"), onSelect = { selected -> typeOfCard.value = selected} )

            }


//            LazyColumn {
//                items(dummyTransactions){ data -> TransactionCard(
//                    stockDetails = data.stockDetails,
//                    addressDetails = data.addressDetails ,
//                    otherDetails = data.otherDetails,
//                    date = data.date,
//                    reciptNo = data.receiptNo,
//                    type = data.type
//                )}
//            }

            when (val state = transactionHistory.value) {

                is singleFarmerTransactionApiState.idle -> {
                    CircularProgressIndicator()
                }



                is singleFarmerTransactionApiState.success -> {


                    Log.d("SuccessRedcipt", state.data.toString())
//                    LazyColumn(){
//                        items(state.data ){
//
//                            CardComponentDaybook(orderDaybook = it )
//                        }
//                    }
                    if(state.data != null){
                    state.data.forEach {
                        CardComponentDaybook(orderDaybook = it )
                    }} else {
                        Text(text = "No previous transactions" )

                    }
//                    LazyColumn(){
//                        items(state.data){
//                            CardComponentDaybook(orderDaybook = it )
//                        }
//                    }
                }

                is singleFarmerTransactionApiState.error->{
                    Text(text = state.message)
                }

                else -> {Log.d("Else branch " , "transaction card in the farmer card")}
            }
//            Column {
//                Log.d("Dummy transactionnn", dummyTransactions.toString())
//
//                if (selectedGroupBy.value == "Only Incoming") {
//                    dummyTransactions.filter { data -> data.type == "incoming" }.forEach { data ->
//                        TransactionCard(
//                            stockDetails = data.stockDetails,
//                            addressDetails = data.addressDetails,
//                            otherDetails = data.otherDetails,
//                            date = data.date,
//                            reciptNo = data.receiptNo,
//                            type = data.type
//                        )
//                    }
//                    Log.d("Dummy transaction", dummyTransactions.toString())
//                } else if (selectedGroupBy.value == "Only Outgoing") {
//                    dummyTransactions.filter { data -> data.type == "outgoing" }.forEach { data ->
//                        TransactionCard(
//                            stockDetails = data.stockDetails,
//                            addressDetails = data.addressDetails,
//                            otherDetails = data.otherDetails,
//                            date = data.date,
//                            reciptNo = data.receiptNo,
//                            type = data.type
//                        )
//                    }
//                    Log.d("Dummy transaction", dummyTransactions.toString())
//                } else if (selectedGroupBy.value == "Both") {
//                    dummyTransactions.forEach { data ->
//                        TransactionCard(
//                            stockDetails = data.stockDetails,
//                            addressDetails = data.addressDetails,
//                            otherDetails = data.otherDetails,
//                            date = data.date,
//                            reciptNo = data.receiptNo,
//                            type = data.type
//                        )
//                    }
//                    Log.d("Dummy transaction", dummyTransactions.toString())
//                } else {
//                    dummyTransactions.forEach { data ->
//                        TransactionCard(
//                            stockDetails = data.stockDetails,
//                            addressDetails = data.addressDetails,
//                            otherDetails = data.otherDetails,
//                            date = data.date,
//                            reciptNo = data.receiptNo,
//                            type = data.type
//                        )
//                    }
//                }
//
//
////            dummyTransactions.forEach { data -> TransactionCard(
////                    stockDetails = data.stockDetails,
////                    addressDetails = data.addressDetails ,
////                   otherDetails = data.otherDetails,
////                    date = data.date,
////                   reciptNo = data.receiptNo,
////                   type = data.type
////               )  }
//
//            }

            //HistoryTable()
            //Spacer(modifier = Modifier.padding(20.dp))

            //TableView(selectedRow= selectedRow, isDailogOpen= isDailogOpen )
            //Spacer(modifier = Modifier.padding(15.dp))

            //Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.End) {

//            Surface(modifier = Modifier
//                .padding(horizontal = 10.dp)
//                .clickable { }
//                , ) {
//                Text(text = "Scrollable View", modifier = Modifier
//                    .background(Color(0xFF22C55E), RoundedCornerShape(10.dp))
//
//                    .width(148.dp)
//                    .height(50.dp)
//                    .wrapContentSize(align = Alignment.Center))
//
//            }

        }


    }
    if (isDailogOpen.value) {
        LotDetailsDialogWrapper(
            reciept = selectedRow,
            bags = selectedRow.value!!,
            onClose = { isDailogOpen.value = false })
        Log.d("PopOP", "OPOPO ${isDailogOpen.value}")


    }
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false
                viewmodel.updateRation("")
                viewmodel.updateGoli("")
                viewmodel.updateCutAndTok("")
                viewmodel.updateSeedBags("")
                viewmodel.updateTwelveNumber("")
                viewmodel.updateVariety("")
                viewmodel.updateChamber("")
                viewmodel.updateRemarks("")



            },
            sheetState = sheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )


        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()), // fill maximum available height
            ) {
//important
                ManageStocks({ hideBottomSheet() }) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                }
            }


        }
    }


    if (showSecondBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSecondBottomSheet.value = false },
            sheetState = sheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()), // fill maximum available height
            ) {
                AssignLocation(onContinue = {
              hideSecondBottomSheet()

                }) {
                    scope.launch { secondSheetState.hide() }.invokeOnCompletion {
                        if (!secondSheetState.isVisible) {
                            showSecondBottomSheet.value = false

                        }
                        showBottomSheet.value = true
                    }
                }
            }
        }
    }

    if (showThirdBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showThirdBottomSheet.value = false },
            sheetState = thirdSheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )
        ) {
            ConfirmationPageForOrder(onPrevious = { /*TODO*/ }) {
                hideThirdBottomSheet()
            }
        }
    }

    if (showFourthBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showFourthBottomSheet.value = false },
            sheetState = fourthSheetState, modifier = Modifier.height(700.dp),

            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true,
            )


            ) {
            finalConfirmation {

                hidefourthBottomSheet()
                viewmodel.getTheSelectedStock()
                   viewmodel.getTheSelectedIndex()
            }
        }

    }

}
