package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.farmerCard
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.nameAndFathersName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun People(navController: NavHostController){
   // val navController = rememberNavController()

    val listOfFarmer = mutableListOf(
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","1"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","2"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","3"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","4"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","5"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","6"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","7"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","8"
        ),
        nameAndFathersName(
            "Farmer's name","S/O: Father's name","9"
        )
    )
var farmerName = remember{ mutableStateOf("") }
   // Text(text = "People" , fontSize = 24.sp , fontWeight = FontWeight.Bold)
    val keyboardController = LocalSoftwareKeyboardController.current

Scaffold(topBar = {
    TopAppBar(title = { Text(text = "People") })
}) {

Column(modifier = Modifier.padding(
    it
)) {
    TextField(value =farmerName.value , onValueChange = {name ->farmerName.value= name},
        label = { Text(text = "Search by name or mobile")
    }, modifier = Modifier.fillMaxWidth() , keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {keyboardController?.hide()})
        )
    Spacer(modifier = Modifier.padding(10.dp))
    Row(){
    Text(text = "Sort by name" , fontWeight = FontWeight.Medium , fontSize = 14.sp)
    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Sorting")}
    
    //lazycolumn
    
    LazyColumn(){
        items(listOfFarmer){farmer->
            farmerCard(farmerName = farmer.name, fatherName = farmer.fatherName, accNum = farmer.accNUmber, navController = navController)

            Spacer(modifier = Modifier.padding(15.dp))
        }
    }
}
}

}