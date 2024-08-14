package com.example.coldstorage.Presentation.Screens.OnboardingScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun welcomeBackScreen(){
val mobileNum = remember{
    mutableStateOf("")
}


    val password = remember {
        mutableStateOf("")
    }
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Welcome Back")
      Spacer(modifier = Modifier.padding(30.dp))
        OutlinedTextField(value = mobileNum.value ,
                     onValueChange = {text -> mobileNum.value},
                     singleLine = true,
            placeholder = { Text(text = "Enter mobile number")}
                     )
        Spacer(modifier = Modifier.padding(15.dp))


        OutlinedTextField(value = password.value ,
            onValueChange = {text -> password.value},
            singleLine = true,
            placeholder = { Text(text = "Enter password")}
        )
        Spacer(modifier = Modifier.padding(25.dp))

        Surface(modifier = Modifier.height(50.dp).width(70.dp)) {
            Text(text = "Continue" , )
        }

    }
}

@Preview
@Composable
fun preWelcomeBack(){
    welcomeBackScreen()
}