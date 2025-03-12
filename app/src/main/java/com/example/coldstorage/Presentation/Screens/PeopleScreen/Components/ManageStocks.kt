package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.DisposableHandle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageStocks(onContinue: ()->Unit, viewmodel:FunctionStoreOwner= hiltViewModel(), onClick: ()->Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    val variety = viewmodel.variety.collectAsState()
    val lotSize = viewmodel.lotsize.collectAsState()
    val Ration = viewmodel.Ration.collectAsState()
    val seedBags = viewmodel.seedBags.collectAsState()
    val twelveNumber = viewmodel.twelveNumber.collectAsState()

    val Goli = viewmodel.goli.collectAsState()
    val cutAndTok = viewmodel.cuttok.collectAsState()
//    val textFieldColors = MaterialTheme.Color.textFieldColors.copy(
//        backgroundColor = Color.Transparent, // Set background color to transparent
//        focusedIndicatorColor = Color.Transparent // Optional: Set focused indicator color
//    )
    val currentReceiptNum by viewmodel.currentRecieptNum.collectAsState(0)
    LaunchedEffect(Unit){
        viewmodel.getRecieptNumbers()
    }

    Column(modifier = Modifier.padding(12.dp)) {
    Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Create order" , fontSize = 30.sp, fontWeight = FontWeight.Bold)
       Icon(imageVector = Icons.Default.Close, contentDescription ="Close sheet " ,
           modifier = Modifier.clickable {
               onClick()
               viewmodel.updateRation("")
               viewmodel.updateGoli("")
               viewmodel.updateCutAndTok("")
               viewmodel.updateSeedBags("")
               viewmodel.updateTwelveNumber("")
               viewmodel.updateVariety("")
               viewmodel.updateChamber("")
               viewmodel.updateRemarks("")

           })}
  Spacer(modifier = Modifier.padding(20.dp))

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

         Text(text = "Enter variety ", fontSize = 14.sp, fontWeight = FontWeight.Medium)

         TextField(value = variety.value , onValueChange ={ text-> viewmodel.updateVariety(text)},
             label = { Text(text = "Enter name of variety")},
             keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
             keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
             ,
             modifier = Modifier.fillMaxWidth() ,
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
                 errorLabelColor = Color.Red
             )
         )
         Spacer(modifier = Modifier.padding(10.dp))

         Text(text = "Enter Lot Number/ Lot Size ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
         TextField(value = lotSize.value , onValueChange ={ text-> viewmodel.updateLotSize(text)},
             label = { Text(text = "No. of bags in this receipt")},
             keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
             keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
             ,
             modifier = Modifier.fillMaxWidth()
         )
         Spacer(modifier = Modifier.padding(15.dp))

         Text(text = "Enter Quantities ", fontSize = 18.sp, fontWeight = FontWeight.Medium)

         Text(text = "Set quantities of each size", fontSize = 12.sp, fontWeight = FontWeight.Medium)

         Spacer(modifier = Modifier.padding(10.dp))

          Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
          ) {
              Text(text = "Ration/Table bags", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                  BasicTextField(
                      value = Ration.value,
                      onValueChange = { text -> viewmodel.updateRation(text) },
                      keyboardOptions = KeyboardOptions(
                          imeAction = ImeAction.Done,
                          keyboardType = KeyboardType.Number
                      ),
                      keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                      modifier = Modifier
                          .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                          .border(
                              BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                              shape = MaterialTheme.shapes.small
                          )
                          .padding(horizontal = 5.dp , vertical = 2.dp)
                          .width(134.dp)
                          .height(40.dp),
                      singleLine = true,
                      maxLines = 1,
                      textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
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
         Spacer(modifier = Modifier.padding(10.dp))

         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
         ) {
             Text(text = "Seed bags", fontSize = 14.sp, fontWeight = FontWeight.Medium)
             BasicTextField(value = seedBags.value , onValueChange ={ text-> viewmodel.updateSeedBags(text)},
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done , keyboardType = KeyboardType.Number),
                 keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
                 ,
                 modifier = Modifier
                     .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                     .border(
                         BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                         shape = MaterialTheme.shapes.small
                     )
                     .padding(horizontal = 5.dp , vertical = 2.dp)
                     .width(134.dp)
                     .height(40.dp),
                 singleLine = true,
                 maxLines = 1,
                 textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
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
         Spacer(modifier = Modifier.padding(10.dp))


         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
         ) {
             Text(text = "12 No. seed bags", fontSize = 14.sp, fontWeight = FontWeight.Medium)
             BasicTextField(value = twelveNumber.value , onValueChange ={ text-> viewmodel.updateTwelveNumber(text)},
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done , keyboardType = KeyboardType.Number),
                 keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
                 , modifier = Modifier
                     .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                     .border(
                         BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                         shape = MaterialTheme.shapes.small
                     )
                     .padding(horizontal = 5.dp , vertical = 2.dp)
                     .width(134.dp)
                     .height(40.dp),
                 singleLine = true,
                 maxLines = 1,
                 textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
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
         Spacer(modifier = Modifier.padding(10.dp))


         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
         ) {
             Text(text = "Goli bags", fontSize = 14.sp, fontWeight = FontWeight.Medium)
             BasicTextField(value = Goli.value , onValueChange ={ text-> viewmodel.updateGoli(text)},
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done , keyboardType = KeyboardType.Number),
                 keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
                 ,
                 modifier = Modifier
                     .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                     .border(
                         BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                         shape = MaterialTheme.shapes.small
                     )
                     .padding(horizontal = 5.dp , vertical = 2.dp)
                     .width(134.dp)
                     .height(40.dp),
                 singleLine = true,
                 maxLines = 1,
                 textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
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
         Spacer(modifier = Modifier.padding(10.dp))

         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
         ) {
             Text(text = "Cut & Tok bags", fontSize = 14.sp, fontWeight = FontWeight.Medium)
             BasicTextField(value = cutAndTok.value , onValueChange ={ text-> viewmodel.updateCutAndTok(text)},
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done , keyboardType = KeyboardType.Number),
                 keyboardActions = KeyboardActions(onDone ={keyboardController?.hide()})
                 ,
                 modifier = Modifier
                     .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                     .border(
                         BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                         shape = MaterialTheme.shapes.small
                     )
                     .padding(horizontal = 5.dp , vertical = 2.dp)
                     .width(134.dp)
                     .height(40.dp),
                 singleLine = true,
                 maxLines = 1,
                 textStyle = TextStyle(fontSize = 18.sp , textAlign = TextAlign.Center),
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
         Spacer(modifier = Modifier.padding(20.dp))

         Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
             Button(
                 onClick= { onContinue() }
                 ,
                 modifier = Modifier
                 .padding(horizontal =  10.dp)
                  ,

                 shape = RoundedCornerShape(10.dp),
                 colors = ButtonColors(containerColor = primeGreen , contentColor = Color.Black,
                     disabledContainerColor = primeGreen , disabledContentColor = Color.White)

                 ) {
                 Text(text = "Continue" , modifier = Modifier
                     .padding(horizontal = 20.dp, vertical = 5.dp))
             }
         }

         Spacer(modifier = Modifier.height(216.dp))


     }


}
}