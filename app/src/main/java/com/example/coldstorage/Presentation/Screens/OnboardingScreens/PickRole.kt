package com.example.coldstorage.Presentation.Screens.OnboardingScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coldstorage.Presentation.Navigation.Roles
import com.example.coldstorage.Presentation.Navigation.bottomNav
import com.example.coldstorage.R
import com.example.coldstorage.ui.theme.primeGreen

@Composable
fun pickYourRole(onRoleSelected: (Roles)->Unit){
val setFarmer = remember{
     mutableStateOf(false)
}
    Column {
         Column(modifier = Modifier.fillMaxSize(),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
             Text(text = "Pick Your Role.", fontSize = 40.sp, fontWeight = FontWeight.Bold)
       Spacer(modifier = Modifier.padding(30.dp))

             Column(
       verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally

             ) {
                 Surface(modifier = Modifier
                     .width(180.dp)
                     .height(180.dp)
                     .align(Alignment.CenterHorizontally)
                     .clickable {
                         onRoleSelected(Roles.ColdStoreOwnere)
                     },
                     shape = RoundedCornerShape(8.dp)
                     , color = Color.Gray,

                 )
                 {
Image(painter = painterResource(id = R.drawable.inventoryowner), contentDescription = "" ,
    modifier = Modifier
        .width(180.dp)
        .height(180.dp)
        .border(0.dp , Color.Transparent, RoundedCornerShape(8.dp))


)
                 }
                 Text(text = "I am a store owner.")
             }

             Spacer(modifier = Modifier.padding(20.dp))

             Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.CenterVertically) {
                 HorizontalDivider(modifier = Modifier.weight(.5f).padding(start = 15.dp), thickness = 1.dp)
                 Text(text = "OR" ,color= primeGreen, modifier = Modifier.weight(.3f) , textAlign = TextAlign.Center)
                 HorizontalDivider(modifier = Modifier.weight(.5f).padding(end = 15.dp) ,thickness = 1.dp)

             }
             Spacer(modifier = Modifier.padding(20.dp))

             Column(
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally

             ) {
                 Surface(modifier = Modifier
                     .width(180.dp)
                     .height(180.dp)
                     .align(Alignment.CenterHorizontally)

                     .clickable { onRoleSelected(Roles.Farmer) },
                     shape= RoundedCornerShape(8.dp),
                     color = Color.Gray
                 ) {
                     Image(painter = painterResource(id = R.drawable.farmerimage), contentDescription = "" ,
                         modifier = Modifier
                             .width(180.dp)
                             .height(180.dp)
                             .border(0.dp , Color.Transparent, RoundedCornerShape(8.dp))



                     )

                 }
                 Text(text = "I am a Farmer.")
             }
         }

        
        }
    }
