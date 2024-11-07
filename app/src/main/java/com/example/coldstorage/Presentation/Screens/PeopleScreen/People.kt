package com.example.coldstorage.Presentation.Screens.PeopleScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.farmerCard
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.nameAndFathersName
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun People(navController: NavHostController , viewmodel:FunctionStoreOwner = hiltViewModel()){
   // val navController = rememberNavController()

    val listOfFarmer = viewmodel.listOfFarmers
  var farmerName = remember{ mutableStateOf("") }
   // Text(text = "People" , fontSize = 24.sp , fontWeight = FontWeight.Bold)
    val keyboardController = LocalSoftwareKeyboardController.current
    //Log.d("List of farmers", "Farmers "+listOfFarmer)



    LaunchedEffect(Unit ){
    viewmodel.fetchFarmersList()
        Log.d("List of farmers", "Farmers, inside launched effect "+listOfFarmer)

    }
Scaffold(modifier = Modifier.padding(10.dp), topBar = {
    TopAppBar(title = { Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "People")
        Surface(modifier = Modifier.clickable {
            navController.navigate(AllScreens.QuickAddFarmer.name)
        }) {
            Text(text = "Add")
        }
    } })
}) {

Column(modifier = Modifier.padding(
    it
)) {
    TextField(value =farmerName.value , onValueChange = {name ->farmerName.value= name},
        label = { Text(text = "Search by name or mobile")
    }, modifier = Modifier.fillMaxWidth()
            , keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {keyboardController?.hide()}),
        colors = TextFieldDefaults.textFieldColors(
             // Text color inside the text field
            containerColor = Color.Transparent, // Background color (transparent if desired)
            cursorColor = Color.Black, // Cursor color
            focusedIndicatorColor = Color.Black, // Border color when focused
            unfocusedIndicatorColor = Color.Black, // Border color when unfocused
            focusedLabelColor = Color.White, // Label color when focused
            unfocusedLabelColor = Color.Gray // Label color when unfocused
        )
        )
    Spacer(modifier = Modifier.padding(10.dp))
    Row(){
    Text(text = "Sort by name" , fontWeight = FontWeight.Medium , fontSize = 14.sp)
    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Sorting")}
    
    //lazycolumn
    
    LazyColumn(){
        items(listOfFarmer){farmer->
           if (farmer != null){
               farmerCard(farmerName = farmer.name, fatherName = farmer.address, accNum = farmer._id, navController = navController , showAcc = true)

           }


            Spacer(modifier = Modifier.padding(15.dp))
        }
    }
}
}

}

//5:00