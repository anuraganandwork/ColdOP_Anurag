package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import FirstBottomSheet
import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpDropDown
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.finalConfirmation
import com.example.coldstorage.R
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.primeRed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard( navController: NavController, viewmodel: FunctionStoreOwner = hiltViewModel()){
    var query by remember { mutableStateOf("") }

    var showBottomSheetOnDaybook = remember {
        mutableStateOf(false)
    }
    
    var showSecondSheetOnDaybook = remember{
        mutableStateOf(false)
    }

    var showThirdSheetOnDayBook = remember {
        mutableStateOf(false)
    }
    val firstSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val secondSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val thirdSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val typeOfCard = remember { mutableStateOf("all") }
    val sortingOrder = remember { mutableStateOf("latest") }
    LaunchedEffect(typeOfCard.value , sortingOrder.value ){
        Log.d("asdfghjk",typeOfCard.value)
        Log.d("qwertyu" , sortingOrder.value)
       if(typeOfCard.value === "Outgoing"){
           viewmodel.getOrdersDayBook("outgoing" , sortingOrder.value , 1,50)

       }
       else if(typeOfCard.value === "Incoming"){
           viewmodel.getOrdersDayBook("incoming" , sortingOrder.value , 1,50)
       }
        else{
           viewmodel.getOrdersDayBook("all" , sortingOrder.value , 1,50)

       }
    }
    val state by viewmodel.dayBookOrdersData.collectAsState()

    val hideFirstBottomSheet: () -> Unit = {
        scope.launch {
            firstSheetState.hide()
            showBottomSheetOnDaybook.value = false
            // Only show second sheet after first is hidden
            scope.launch {
                showSecondSheetOnDaybook.value = true
            }
        }
    }


    val openFirstBottomSheet :()-> Unit = {
        scope.launch {
            secondSheetState.hide()
            showSecondSheetOnDaybook.value = false
            showBottomSheetOnDaybook.value = true
        }
    }
    val hideSecondBottomSheet : ()-> Unit = {
        scope.launch {
            secondSheetState.hide()
           showSecondSheetOnDaybook.value = false

           scope.launch {
                showThirdSheetOnDayBook.value = true
            }
        }
    }




    Scaffold(topBar = { TopAppBar(title = { Text(text = "Daybook" , fontWeight = FontWeight.Bold) } , scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior() ) }) {
    Column(modifier = Modifier.padding(it)) {
        Column {


        Row(modifier = Modifier.fillMaxWidth()) {
            Surface(shape = RoundedCornerShape(10.dp), color = primeGreen, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .clickable {
                    showBottomSheetOnDaybook.value = true
                }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add" , tint = Color.White)
                    Text(text = "Incoming" , color = Color.White)

                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp), color = primeRed,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .clickable {

                        val fromDaybook = true
                        val accountNumber = "12"
                        navController.navigate(AllScreens.OutgoingStockScreen.name + "/$fromDaybook/$accountNumber")
                    },
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = "Remove",
                        modifier = Modifier.size(24.dp) ,
                        tint = Color.White
                    )
                    Text(text = "Outgoing" , color = Color.White)

                }
            }


        }

        Text(text = "Transactions", fontWeight = FontWeight.Bold , fontSize = 20.sp ,  modifier = Modifier.padding(horizontal = 15.dp , vertical = 10.dp))
        Row(modifier = Modifier.padding(horizontal = 10.dp) , horizontalArrangement = Arrangement.SpaceAround) {
            
            ColdOpDropDown(label = "Sort", options = listOf("Latest" , "Oldest"), onSelect = { selected -> sortingOrder.value = selected.toLowerCase()} )
            Spacer(modifier = Modifier.padding(8.dp))
            ColdOpDropDown(label = "Filter", options = listOf("Incoming" , "Outgoing" , "Show All"), onSelect = { selected -> typeOfCard.value = selected} )

        }
            when (state) {
                is FunctionStoreOwner.ApiStateDaybook.Loading -> {
                    // Show a loading indicator
                    Column(modifier = Modifier.fillMaxSize() ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            color= Color.Gray
                        )

                    }
                }
                is FunctionStoreOwner.ApiStateDaybook.success -> {
                    val data = (state as FunctionStoreOwner.ApiStateDaybook.success).data

                    if (data?.data.isNullOrEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "No transactions!", color = Color.Gray)
                        }
                    } else {
                        LazyColumn {
                            if (data!= null) {
                                items(data.data) {
                                    cardWrapper(context , it)
                                }
                            }
                        }
                    }

                }
                is FunctionStoreOwner.ApiStateDaybook.Error -> {
                    val errorMessage = (state as FunctionStoreOwner.ApiStateDaybook.Error).message
                    // Show error message
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Error: $errorMessage :(", color = Color.Gray)
                    }
                }
            }
    } }

    if(showBottomSheetOnDaybook.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetOnDaybook.value = false },
            sheetState = firstSheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )){

            FirstBottomSheet( hideFirstBottomSheet , viewmodel = viewmodel)
            }



    }
         if (showSecondSheetOnDaybook.value){
             ModalBottomSheet(
                 onDismissRequest = { showSecondSheetOnDaybook.value = false },
                 sheetState = secondSheetState, modifier = Modifier.height(700.dp),
                 properties = ModalBottomSheetProperties(
                     securePolicy = SecureFlagPolicy.Inherit,
                     isFocusable = true,
                     shouldDismissOnBackPress = true
                 )){
                 SecondBottomSheet(onContinue = {
                     Log.d("Success", "Pressed")

                      }, onSuccess = {hideSecondBottomSheet()}, viewmodel = viewmodel) {
                     openFirstBottomSheet()
                 }
             }
         }

        if(showThirdSheetOnDayBook.value){
            ModalBottomSheet(
                onDismissRequest = { showSecondSheetOnDaybook.value = false },
                sheetState = thirdSheetState, modifier = Modifier.height(700.dp),
                properties = ModalBottomSheetProperties(
                    securePolicy = SecureFlagPolicy.Inherit,
                    isFocusable = true,
                    shouldDismissOnBackPress = true
                )){
                finalConfirmation {
                    scope.launch {

                        thirdSheetState.hide()
                        showThirdSheetOnDayBook.value = false
                        navController.navigate(AllScreens.Dashboard.name)
                    }

                }
                }
            }



        }


    }



//@Preview
//@Composable
//fun prevDay(){
//    Dashboard()
//}

//406

// work on the debouncing feature using jobs ,also  what is debouncing

//1223- 123

@Composable
fun cardWrapper(context: Context,orderDaybook: OrderDaybook){
//    val view = ComposeView(context).apply {
//        setContent {         CardComponentDaybook(orderDaybook = orderDaybook)
//        }
//    }


//    val (contentWidth, contentHeight) = calculateDimensions(context
//    ) { CardComponentDaybook(orderDaybook = orderDaybook) }

    Column {
        CardComponentDaybook(orderDaybook = orderDaybook)
//        PdfButton(
//            content = { CardComponentDaybook(orderDaybook = orderDaybook) },
//
//            context = context,
//            contentWidth = 200.dp,
//            contentHeight = 200.dp,
//            density = Density(context.resources.displayMetrics.density)
//        )
    }
}


fun calculateDimensions(context: Context, content: @Composable () -> Unit): Pair<Dp, Dp> {
    val composeView = ComposeView(context).apply {
        setContent { content() }
    }

    // Measure the view to determine its dimensions
    composeView.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    val widthPx = composeView.measuredWidth
    val heightPx = composeView.measuredHeight

    // Convert to Dp
    val density = context.resources.displayMetrics.density
    val widthDp = Dp(widthPx / density)
    val heightDp = Dp(heightPx / density)

    return Pair(widthDp, heightDp)
}
