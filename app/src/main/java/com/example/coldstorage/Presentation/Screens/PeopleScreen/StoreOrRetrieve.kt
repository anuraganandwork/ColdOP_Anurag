package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.AssignLocation
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ConfirmationPageForOrder
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.HistoryTable
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.LotDetailsDialog
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.LotDetailsDialogWrapper
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ManageStocks
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.TableView
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.finalConfirmation
import com.example.coldstorage.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun storeOrRetrieve(){
    //variables for bottom sheet

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet = remember{
        mutableStateOf(false)
    }

    //For second bottom sheet
    val secondSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSecondBottomSheet = remember{
        mutableStateOf(false)
    }


    //For third bottom sheet
    val thirdSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showThirdBottomSheet = remember{
        mutableStateOf(false)
    }

    //For fourth bottom sheet
   val fourthSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showFourthBottomSheet = remember{
        mutableStateOf(false)
    }


    //for dailog box
    var selectedRow = remember{ mutableStateOf<String?>(null) }
    var isDailogOpen= remember { mutableStateOf<Boolean>(false) }
    Log.d("PopOP","OPOPO initial ${isDailogOpen.value}")

    val  hideBottomSheet: ()-> Unit = {
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
    val  hideSecondBottomSheet: ()-> Unit = {
        scope.launch {
            secondSheetState.hide()
        }.invokeOnCompletion {
            if (!secondSheetState.isVisible) {
                showSecondBottomSheet.value = false
            }
            showThirdBottomSheet.value = true
        }
    }

    //hiding the third bottom sheet
    val  hideThirdBottomSheet: ()-> Unit = {
        scope.launch {
            thirdSheetState.hide()
        }.invokeOnCompletion {
            if (!thirdSheetState.isVisible) {
                showThirdBottomSheet.value = false
            }
            showFourthBottomSheet.value = true
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(  modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    painter = painterResource(id = R.drawable.backicon),
                    contentDescription = "Back Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
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
           Row ( horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){


            Surface(modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 0.dp)
                .background(Color.Green)
                .clickable { showBottomSheet.value = true }) {
                Text(text = "Deposit stocks" , modifier = Modifier
                    .border(1.dp, Color(0xFF22C55E), RoundedCornerShape(10.dp))
                    .background(Color(0xFF22C55E), RoundedCornerShape(10.dp))
                    .width(148.dp)
                    .height(70.dp)
                    .wrapContentSize(align = Alignment.Center)
                , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
                )
            }
            Surface(modifier = Modifier
                .padding(horizontal = 12.dp)) {
                Text(text = "Deliver stocks", modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(10.dp))

                    .width(148.dp)
                    .height(70.dp)
                    .wrapContentSize(align = Alignment.Center))

            }

           }

            //table starting here
            Spacer(modifier = Modifier.padding(15.dp))

            Text(text = "History", fontWeight = FontWeight.Bold)
            HistoryTable()
            Spacer(modifier = Modifier.padding(20.dp))

            TableView(selectedRow= selectedRow, isDailogOpen= isDailogOpen )
            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.End) {

            Surface(modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable { }
                , ) {
                Text(text = "Scrollable View", modifier = Modifier
                    .background(Color(0xFF22C55E), RoundedCornerShape(10.dp))

                    .width(148.dp)
                    .height(50.dp)
                    .wrapContentSize(align = Alignment.Center))

            }}


        }
        if(isDailogOpen.value){
            LotDetailsDialogWrapper(reciept = selectedRow, bags = selectedRow.value!!, onClose =  {isDailogOpen.value = false} )
            Log.d("PopOP","OPOPO ${isDailogOpen.value}")


        }
        if (showBottomSheet.value){
            ModalBottomSheet(onDismissRequest = { showBottomSheet.value = false },
                sheetState = sheetState, modifier = Modifier.height(700.dp ),
                properties = ModalBottomSheetProperties(securePolicy = SecureFlagPolicy.Inherit, isFocusable = true, shouldDismissOnBackPress = true)



            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()), // fill maximum available height
                ) {
//important
                    ManageStocks({ hideBottomSheet() }){
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet.value = false
                            }
                        }
                    }
                }


            }
        }
        if(showSecondBottomSheet.value
        ){
            ModalBottomSheet(onDismissRequest = { showSecondBottomSheet.value = false },
                sheetState = sheetState, modifier = Modifier.height(700.dp ),
                properties = ModalBottomSheetProperties(securePolicy = SecureFlagPolicy.Inherit, isFocusable = true, shouldDismissOnBackPress = true)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()), // fill maximum available height
                ){
                    AssignLocation(onContinue = { hideSecondBottomSheet()}) {
                        scope.launch { secondSheetState.hide() }.invokeOnCompletion {
                            if (!secondSheetState.isVisible) {
                                showSecondBottomSheet.value = false

                            }
                            showBottomSheet.value= true
                        }
                    }
                }
            }
        }

        if(showThirdBottomSheet.value){
            ModalBottomSheet(onDismissRequest = { showThirdBottomSheet.value = false },
                sheetState = thirdSheetState, modifier = Modifier.height(700.dp ),
                properties = ModalBottomSheetProperties(securePolicy = SecureFlagPolicy.Inherit, isFocusable = true, shouldDismissOnBackPress = true)
            ){
                ConfirmationPageForOrder(onPrevious = { /*TODO*/ }) {
                    hideThirdBottomSheet()
                }
            }
        }

        if(showFourthBottomSheet.value){
            ModalBottomSheet(onDismissRequest = { showFourthBottomSheet.value = false },
                sheetState = fourthSheetState, modifier = Modifier.height(700.dp ),

                properties = ModalBottomSheetProperties(securePolicy = SecureFlagPolicy.Inherit, isFocusable = true, shouldDismissOnBackPress = true, ),


            ){
                Box(modifier = Modifier.height(700.dp) ){
                finalConfirmation {
                    // TODO:
                }}
            }

        }

    }
}