package com.example.coldstorage.Presentation.Screens.SettingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.Presentation.Navigation.Sections
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ui.theme.primeGreen

@Composable
fun Setting(navController:NavController){
    val authViewmodel:AuthViewmodel= hiltViewModel()
    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            authViewmodel.logOutStoreOwner()

            navController.navigate(Sections.roleSelectionScreen.route)
                         } ,
            colors = ButtonColors( containerColor = primeGreen , contentColor = Color.White , disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Red
            )) {
            Text(text = "Log out")
        }
    }

}