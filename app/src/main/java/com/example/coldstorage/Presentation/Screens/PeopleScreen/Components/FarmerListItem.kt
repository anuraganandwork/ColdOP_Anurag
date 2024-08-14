package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coldstorage.Presentation.Screens.AllScreens

@Composable
fun farmerCard(farmerName:String, fatherName:String , accNum: String,  navController: NavController){
Row(modifier = Modifier.fillMaxWidth().background(Color(239,237, 241))
    .padding(horizontal = 10.dp, vertical = 0.dp)
    .clickable { try {
        //navController.navigate("${AllScreens.FarmerDetailedScreen.name}")
  navController.navigate(route = AllScreens.FarmerDetailedScreen.name+"/${accNum}")
        Log.d("FarmerCard", "Navigating to Detailed Screen")

    } catch (e: Exception) {

        Log.e("FarmerCard", "${AllScreens.FarmerDetailedScreen.name}/${accNum}", e)
    } },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween

){
    Row(verticalAlignment = Alignment.CenterVertically ){
    AsyncImage(
        model = "https://bcciplayerimages.s3.ap-south-1.amazonaws.com/ipl/IPLHeadshot2024/2.png",
        contentDescription = null,
        modifier = Modifier.size(75.dp)
    )
    Column {
        Text(text = farmerName)
        Text(text = fatherName)
    }
    }

    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Three dots")
}
}

