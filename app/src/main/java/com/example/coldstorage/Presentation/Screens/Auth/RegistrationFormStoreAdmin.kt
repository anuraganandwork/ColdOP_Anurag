package com.example.coldstorage.Presentation.Screens.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.DataLayer.Api.StoreAdminModel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun StoreAdminRegistrationForm(navController: NavController, viewModel: AuthViewmodel = hiltViewModel() ) {




    var coldStorageAddress by remember { mutableStateOf("") }
    var coldStorageContactNumber by remember { mutableStateOf("") }
    var coldStorageName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var isVerified by remember { mutableStateOf(false) }
    var mobileNumber by remember { mutableStateOf("") }
    var EnteredOtp by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var personalAddress by remember { mutableStateOf("") }
    var ActualOtp by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Store Admin Registration")

        OutlinedTextField(
            value = coldStorageAddress,
            onValueChange = { coldStorageAddress = it },
            label = { Text("Cold Storage Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = coldStorageContactNumber,
            onValueChange = { coldStorageContactNumber = it },
            label = { Text("Cold Storage Contact Number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = coldStorageName,
            onValueChange = { coldStorageName = it },
            label = { Text("Cold Storage Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Checkbox(
//                checked = isVerified,
//                onCheckedChange = { isVerified = it }
//            )
//            Text("Is Verified")
//        }

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick={
            viewModel.sendOtp(mobileNumber)


        }, modifier = Modifier.fillMaxWidth()){
            Text(text = "Send otp")
        }

        OutlinedTextField(
            value = EnteredOtp,
            onValueChange = { EnteredOtp = it },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick={
            viewModel.verifyMobile(mobileNumber,EnteredOtp)

            isVerified = viewModel.verificationResult.value!!
        }, modifier = Modifier.fillMaxWidth()){
            Text(text = "Verify mobile")
        }


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = personalAddress,
            onValueChange = { personalAddress = it },
            label = { Text("Personal Address") },
            modifier = Modifier.fillMaxWidth()
        )

        if(isVerified){Button(
            onClick = {
                val formData = StoreAdminFormData(
                    coldStorageAddress = coldStorageAddress,
                    coldStorageContactNumber = coldStorageContactNumber,
                    coldStorageName = coldStorageName,
                    imageUrl = imageUrl,
                    isVerified = isVerified,
                    mobileNumber = mobileNumber,

                    name = name,
                    password = password,
                    personalAddress = personalAddress
                )
                viewModel.registerStoreOwner(formData)
                //onSubmit(formData)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }} else{
            Button(onClick = {}){
                Text(text = "Verify mobile first")
            }
        }

    }
}