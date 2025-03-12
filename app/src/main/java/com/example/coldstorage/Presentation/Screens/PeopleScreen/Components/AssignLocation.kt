package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.primeRed

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignLocation(onContinue:()->Unit, viewmodel:FunctionStoreOwner= hiltViewModel(), onClick:()->Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    val chamber = viewmodel.chamber.collectAsState()
    val floor = viewmodel.floor.collectAsState()
    val row = viewmodel.row.collectAsState()
    val remarks = viewmodel.remarks.collectAsState()
    val loading = viewmodel.loading.collectAsState()
//    val rack = remember{
//        mutableStateOf("")
//    }
    val incomingOrderstatus  = viewmodel.incomingOrderStatus.collectAsState()
    val currentReceiptNum by viewmodel.currentRecieptNum.collectAsState(0)
    LaunchedEffect(Unit){
        viewmodel.getRecieptNumbers()
    }
    LaunchedEffect(incomingOrderstatus.value ){
        if (incomingOrderstatus.value){
           onContinue()
        }

    }


    Column(modifier = Modifier.padding(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Assign location" , fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Close, contentDescription ="Close sheet " ,
                modifier = Modifier.clickable {
                    onClick()
                })
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Column {
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
            Spacer(modifier = Modifier.padding(10.dp))
            
            Text(text = "Enter Location Details", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = "Set the respective location in your cold" , color = lightGrayBorder , fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(10.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Location", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = chamber.value,
                    onValueChange = { text -> viewmodel.updateChamber(text) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center // Center-align the inner content
                        ) {
                            innerTextField()
                        }
                    }
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
//                        .padding(horizontal = 5.dp, vertical = 1.dp)
//                        .width(134.dp)
//                        .height(40.dp),
//                    singleLine = true,
//                    maxLines = 1,
//                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
//                    decorationBox = { innerTextField ->
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center // Center-align the inner content
//                        ) {
//                            innerTextField()
//                        }
//                    }
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
//                        .padding(horizontal = 5.dp, vertical = 1.dp)
//                        .width(134.dp)
//                        .height(40.dp),
//                    singleLine = true,
//                    maxLines = 1,
//                    textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
//                    decorationBox = { innerTextField ->
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center // Center-align the inner content
//                        ) {
//                            innerTextField()
//                        }
//                    }
//                )
//            }
          //  Spacer(modifier = Modifier.padding(10.dp))

//            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Rack", fontSize = 14.sp, fontWeight = FontWeight.Medium)
//                BasicTextField(
//                    value = rack.value,
//                    onValueChange = { text -> viewmodel.updateFloor(text) },
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done,
//                        keyboardType = KeyboardType.Number
//                    ),
//                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
//                    modifier = Modifier
//                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
//                        .border(
//                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
//                            shape = MaterialTheme.shapes.small
//                        )
//                        .padding(horizontal = 5.dp)
//                        .width(134.dp)
//                        .height(40.dp),
//                    singleLine = true,
//                    maxLines = 1,
//                    textStyle = TextStyle(fontSize = 24.sp)
//                )
//            }
            Text(text = "Remarks",  fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.padding(6.dp))

            ColdOpTextField(value = remarks.value, onValueChange = {
                viewmodel.updateRemarks(it)
            } ,  placeholder = "Describe any sort of exception to be handelled in\n" +
                    "the order , could be multiple address allocation." ,
                modifier = Modifier
                    .width(370.dp)
                    .height(125.dp))

            Spacer(modifier = Modifier.padding(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick= {
                    onClick()
                },modifier = Modifier
                   // .padding(10.dp)
                    ,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonColors(containerColor = primeRed , contentColor = Color.Black,
                        disabledContainerColor = primeGreen , disabledContentColor = Color.White)


                ) {
                    Text(text = "Previous" , modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Button(onClick={
                    //onContinue()
                    viewmodel.createIncomingOrderForUi()
                },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)

                    ,

                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonColors(containerColor = primeGreen , contentColor = Color.Black,
                        disabledContainerColor = primeGreen , disabledContentColor = Color.White)


                ) {

                    if(loading.value){
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp), // Small size for the indicator
                            color = Color.Black
                        )
                    } else{
                        Text(text = "Continue" , modifier = Modifier

                        )
                    }

                }
            }

        }
    }
}