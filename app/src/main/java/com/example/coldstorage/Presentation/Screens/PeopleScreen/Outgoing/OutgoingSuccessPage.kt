package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.LottieAnimationCheck
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen

@Composable
fun OutgoingOrderSuccess(viewmodel:FunctionStoreOwner , navController: NavController){
    val outgoingOrderResult = viewmodel.orderOutgoingResult.value
    LaunchedEffect( outgoingOrderResult){
         
        Log.d("xczxas","assaasaa")
    }
    Box(modifier = Modifier
        .fillMaxHeight()
        .background(color = Color.White) // Set the background color
    ){
        Column(modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Confirmation",  fontSize = 30.sp, fontWeight = FontWeight.Bold)

            LottieAnimationCheck()


            Text(text = "Your response has been submitted successfully.", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(20.dp))

//        Surface(){
//            Text(text = "Go back to  home" , modifier = Modifier
//                .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
//                .background(Color.Green, RoundedCornerShape(10.dp))
//                .padding(horizontal = 20.dp, vertical = 10.dp)
//                .height(40.dp)
//                .wrapContentSize(align = Alignment.Center)
//                , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
//            )
//        }

            Button(onClick = { navController.navigate(AllScreens.Dashboard.name) } , colors = ButtonDefaults.buttonColors(
                containerColor = primeGreen,
                contentColor = Color.White
            )) {
                Text(text = "Go back to  home" , modifier = Modifier
                    //.border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                    //.background(Color.Green, RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .height(40.dp)
                    .wrapContentSize(align = Alignment.Center)
                    , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
                )
            }
            Surface(modifier = Modifier.padding(50.dp)){
                Text(text = "Print receipt" , modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .height(40.dp)
                    .wrapContentSize(align = Alignment.Center)
                    , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
                )
            }
        }
    }
}