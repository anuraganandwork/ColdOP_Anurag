package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpTextField
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.primeRed

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  SecondBottomSheet( navController: NavController, viewmodel:FunctionStoreOwner  ,){

    val currentReceiptNum by viewmodel.currentRecieptNum.collectAsState(0)

    val keyboardController = LocalSoftwareKeyboardController.current
    val chamber = viewmodel.chamber.collectAsState()
    val floor = viewmodel.floor.collectAsState()
    val row = viewmodel.row.collectAsState()
    val loadingState = viewmodel.loading.collectAsState()

    val orderResult by viewmodel.orderResult.collectAsState()
    var showAlert by remember{
        mutableStateOf(false)
    }
    val remarks = viewmodel.remarks.collectAsState()
    val incomingOrderStatus = viewmodel.incomingOrderStatus.collectAsState()
    var errorMessage by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit){
        viewmodel.getRecieptNumbers()
    }


    LaunchedEffect(orderResult ){
        orderResult.let {
            result ->
            if (result != null) {
                when{
                    result.isSuccess  ->{

                     //onSuccess()
                        navController.navigate(AllScreens.OutgoingScreenSuccess.name)
                        viewmodel.resetOrderResult() // Clear the result after handling

                    }
                    result.isFailure -> {
                        errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                        showAlert = true
                        viewmodel.resetOrderResult() //
                    }
                }
            }


        }

    }

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){

        item{
            Text(
                text = "Assign Location",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))


        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Current Reciept Number :")
            if (currentReceiptNum != 0) {
                Text(text = currentReceiptNum.toString(), color = Color.Blue)
            } else {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            }
        }

        }




        item{
            Text(text = "Enter Address Details")
            Text(text = "Set the respective location in your cold. ", color = lightGrayBorder , fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Location", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = chamber.value,
                    onValueChange = { text -> viewmodel.updateChamber(text) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp, vertical = 3.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
//            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Floor", fontSize = 14.sp, fontWeight = FontWeight.Medium)
//                BasicTextField(
//                    value = floor.value,
//                    onValueChange = { text -> viewmodel.updateFloor(text) },
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done,
//                        keyboardType = KeyboardType.Text
//                    ),
//                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
//                    modifier = Modifier
//                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
//                        .border(
//                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
//                            shape = MaterialTheme.shapes.small
//                        )
//                        .padding(horizontal = 5.dp , vertical = 3.dp)
//                        .width(134.dp)
//                        .height(40.dp),
//                    singleLine = true,
//                    maxLines = 1,
//                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center)
//                )
//            }
//            Spacer(modifier = Modifier.padding(10.dp))

//            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Row", fontSize = 14.sp, fontWeight = FontWeight.Medium)
//                BasicTextField(
//                    value = row.value,
//                    onValueChange = { text -> viewmodel.updateRow(text)  },
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done,
//                        keyboardType = KeyboardType.Text
//                    ),
//                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
//                    modifier = Modifier
//                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
//                        .border(
//                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
//                            shape = MaterialTheme.shapes.small
//                        )
//                        .padding(horizontal = 5.dp , vertical = 3.dp)
//                        .width(134.dp)
//                        .height(40.dp),
//                    singleLine = true,
//                    maxLines = 1,
//                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center)
//                )
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))


            Text(text = "Remarks",  fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.padding(6.dp))

            ColdOpTextField(value = remarks.value, onValueChange = {
                viewmodel.updateRemarks(it)
            } ,  placeholder = "Describe any sort of exception to be handelled in\n" +
                    "the order , could be multiple address allocation." ,
                modifier = Modifier
                    .width(370.dp)
                    .height(125.dp))


            Spacer(modifier = Modifier.padding(9.dp))

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Surface(modifier = Modifier
                    .background(primeRed, RoundedCornerShape(10.dp))
                    .clickable {
                        //onPrevious()
                        navController.navigate(
                            AllScreens.FirstBottomSheetIncoming.name
                        )
                    } , color= primeRed , shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Previous" , modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Surface(
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                        .background(primeGreen, RoundedCornerShape(10.dp))
                        .clickable {
                            viewmodel.createIncomingOrderForUi()
                            // onContinue()
                        },
                    color = primeGreen,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center, // Centers the content within the Box
                        modifier = Modifier.fillMaxSize()     // Ensures Box takes full size of Surface
                    ) {
                        if (loadingState.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp), // Small size for the indicator
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = "Continue",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

            }


        }
    }
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Failed order creation!") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showAlert = false } , colors  = ButtonDefaults.buttonColors(
                    containerColor = primeGreen,       // Background color
                    contentColor = Color.White        // Text color
                )) {
                    Text("OK")
                }
            }
        )
    }

}

//714
// font size of textfields ,   position mein , log in loading
//1024