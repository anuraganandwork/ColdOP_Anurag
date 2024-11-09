package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.ui.theme.primeGreen

@Composable
fun farmerCard(farmerName:String,fatherName:String , accNum: String,  navController: NavController ,showAcc:Boolean){

   Column {


       Row(
           modifier = Modifier
               .fillMaxWidth()
               .height(80.dp)
               .background(Color.White)
               .padding(start = 10.dp, end = 10.dp , top = 3.dp)
               .background(Color.White)
               .clickable {
                   try {
                       //navController.navigate("${AllScreens.FarmerDetailedScreen.name}")
                       if (showAcc) {
                           navController.navigate(route = AllScreens.FarmerDetailedScreen.name + "/${accNum}")
                           Log.d("FarmerCard", "Navigating to Detailed Screen")
                       }
                   } catch (e: Exception) {

                       Log.e("FarmerCard", "${AllScreens.FarmerDetailedScreen.name}/${accNum}", e)
                   }
               },
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween

       ) {
           Row(verticalAlignment = Alignment.CenterVertically) {
               AsyncImage(
                   model = "https://images.indianexpress.com/2020/05/virat-kohli-1.jpg?w=414",
                   contentDescription = null,

                   modifier = Modifier
                       .width(65.dp)
                       .height(65.dp)
                       .clip(CircleShape)
                       .background(Color.Green)// Ensure the shape is clipped to a circle
                   //  .border(border = BorderStroke(1.dp, Color.Transparent), shape = RoundedCornerShape(100.dp) )

                   ,
                   contentScale = ContentScale.Crop
               )
               Spacer(modifier = Modifier.padding(horizontal = 10.dp))
               Column(verticalArrangement = Arrangement.Center) {
                   Text(text = farmerName)
                   if (showAcc) {
                       Text(text = fatherName)
                   }
               }
           }

           Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Three dots")
       }
       //
       Divider(
           color = primeGreen,
           thickness = 1.dp,
       )
   }


}

