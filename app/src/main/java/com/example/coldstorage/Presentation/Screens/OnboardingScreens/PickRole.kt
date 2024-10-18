package com.example.coldstorage.Presentation.Screens.OnboardingScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coldstorage.Presentation.Navigation.Roles
import com.example.coldstorage.Presentation.Navigation.bottomNav
import com.example.coldstorage.R
@Composable
fun pickYourRole(onRoleSelected: (Roles)->Unit){
val setFarmer = remember{
     mutableStateOf(false)
}
    Column {
         Column(modifier = Modifier.fillMaxSize(),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
             Text(text = "Pick Your Role.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
       Spacer(modifier = Modifier.padding(30.dp))

             Column(


             ) {
                 Surface(modifier = Modifier
                     .width(100.dp)
                     .height(100.dp)
                     .align(Alignment.CenterHorizontally)
                     .clickable {
                         onRoleSelected(Roles.ColdStoreOwnere)
                     }
                     , color = Color.Gray
                 )
                 {
Image(painter = painterResource(id = R.drawable.inventoryowner), contentDescription = "" ,
    modifier = Modifier.width(100.dp).height(100.dp))
                 }
                 Text(text = "I am a store owner.")
             }
             Spacer(modifier = Modifier.padding(20.dp))
             Column {
                 Surface(modifier = Modifier
                     .width(100.dp)
                     .height(100.dp)
                     .align(Alignment.CenterHorizontally)

                     .clickable { onRoleSelected(Roles.Farmer) }, color = Color.Gray
                 ) {
                     Image(painter = painterResource(id = R.drawable.farmerimage), contentDescription = "" ,
                         modifier = Modifier.width(100.dp).height(100.dp))

                 }
                 Text(text = "I am a Farmer.")
             }
         }

        
        }
    }
