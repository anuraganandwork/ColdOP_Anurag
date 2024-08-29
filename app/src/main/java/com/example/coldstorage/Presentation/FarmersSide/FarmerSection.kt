package com.example.coldstorage.Presentation.FarmersSide

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.decode.ImageSource
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.farmerCard
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.nameAndFathersName

@Composable
fun FarmerHomeScreen(navController: NavHostController){
val coldStoreName = remember {
    mutableStateOf("")
}
    var expandedGroupBy = remember { mutableStateOf(false) }
    var selectedGroupBy = remember { mutableStateOf("Sort by") }
    var expandedSortBy = remember { mutableStateOf(false) }
    var selectedSortBy = remember { mutableStateOf("Sort by Date") }

    val listOfFarmer = mutableListOf(
        nameAndFathersName(
            "SB Cold Store","S/O: Father's name","1"
        ),
        nameAndFathersName(
            "Narang Cold Store","S/O: Father's name","2"
        ),
        nameAndFathersName(
            "Sehgal Cold Store","S/O: Father's name","3"
        ),


        )




    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.padding(horizontal = 13.dp , vertical = 25.dp)) {
    Row( horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()) {
        Text(text = "My Cold Stores" , fontSize = 24.sp , fontWeight = FontWeight.SemiBold )
        Text(text = "Add" , modifier = Modifier.clickable {  })
    }
Spacer(modifier = Modifier.padding(12.dp))
        BasicTextField(
            value = coldStoreName.value,
            onValueChange = { name -> coldStoreName.value = name },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                ) // White background with rounded corners
                .border(
                    BorderStroke(1.dp, Color.Gray), // Border with color and width
                    shape = RoundedCornerShape(10.dp) // Apply the same rounded corners
                )
                .padding(horizontal = 10.dp,), // Inner padding to align with standard TextField padding
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (coldStoreName.value.isEmpty()) {
                        Text(text = "Search by name or mobile", color = Color.Gray) // Placeholder
                    }
                    innerTextField() // The actual text input field
                    //innerTextField()

                }
            }
        )

        Spacer(modifier = Modifier.padding(6.dp))

        Row(
            modifier = Modifier
                .padding()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                OutlinedButton(onClick = { expandedGroupBy.value = true } ,         shape = RoundedCornerShape(10.dp) // Reduce the border radius here
                ) {
                    Text(selectedGroupBy.value , color = Color.Gray)
                    Icon(Icons.Default.ArrowDropDown, "dropdown arrow" , tint = Color.Gray)
                }
                DropdownMenu(
                    expanded = expandedGroupBy.value,
                    onDismissRequest = { expandedGroupBy.value = false }
                ) {
                    DropdownMenuItem( text  = { Text(text = "Only Incoming" , color = Color.Gray ) },onClick = {
                        selectedGroupBy.value = "Only Incoming"
                        expandedGroupBy.value = false
                    })
                    DropdownMenuItem( text  = { Text(text = "Only Outgoing" , color = Color.Gray ) }, onClick = {
                        selectedGroupBy.value = "Only Outgoing"
                        expandedGroupBy.value = false
                    })
                    DropdownMenuItem( text  = { Text(text = "Both" , color = Color.Gray ) },onClick = {
                        selectedGroupBy.value = "Both"
                        expandedGroupBy.value = false
                    })
                }
            }

        }

        LazyColumn(){
            items(listOfFarmer){farmer->
                farmerCard(farmerName = farmer.name, fatherName = farmer.fatherName, accNum = farmer.accNUmber, navController = navController , showAcc = false)

                Spacer(modifier = Modifier.padding(10.dp))
            }
        }


Text(text = "Visit Marketplace to see cold stores and add more." , fontWeight = FontWeight.Bold, color = Color.Gray , fontSize = 14.sp)


    }
}