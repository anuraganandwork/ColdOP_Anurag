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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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


    Column(modifier = Modifier.padding(12.dp)) {
    Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Create order" , fontSize = 30.sp, fontWeight = FontWeight.Bold)
       Icon(imageVector = Icons.Default.Close, contentDescription ="Close sheet " ,
           modifier = Modifier.clickable {
               onClick()
           })}
  Spacer(modifier = Modifier.padding(20.dp))

     Column {
         Text(text = "Current Reciept Number : ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
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
                 cursorColor = Color.Blue, // Cursor color
                 errorCursorColor = Color.Red, // Cursor color in error state
                 focusedIndicatorColor = Color.Green, // Border color when focused
                 unfocusedIndicatorColor = Color.Gray, // Border color when not focused
                 errorIndicatorColor = Color.Red, // Border color in error state
                 focusedLeadingIconColor = Color.Black,
                 focusedTrailingIconColor = Color.Black,
                 focusedLabelColor = Color.Green, // Label color when focused
                 unfocusedLabelColor = Color.Gray, // Label color when not focused
                 errorLabelColor = Color.Red // Label color in error state
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
                      textStyle = TextStyle(fontSize = 24.sp),
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
                 textStyle = TextStyle(fontSize = 24.sp),
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
                 textStyle = TextStyle(fontSize = 24.sp),
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
                 textStyle = TextStyle(fontSize = 24.sp),
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
                 textStyle = TextStyle(fontSize = 24.sp),
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
             Surface(modifier = Modifier
                 .padding(10.dp)
                 .background(Color.Green)
                 .clickable {
                     onContinue()
                 }
             ) {
                 Text(text = "Continue" , modifier = Modifier
                     .background(Color.Green)
                     .padding(horizontal = 10.dp, vertical = 5.dp))
             }
         }

     }


}
}