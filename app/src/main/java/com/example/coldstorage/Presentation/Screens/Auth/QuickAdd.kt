package com.example.coldstorage.Presentation.Screens.Auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.FarmerData
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ImageUploadComponent
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun FarmerQuickAddInputForm(navController: NavController, viewModel: AuthViewmodel = hiltViewModel()) {
    // State variables to hold input data
    var address by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mobileNumberError by remember { mutableStateOf("") } // Error message for mobile number
    var enabledButton = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
   val loading = viewModel.loadingAddFarmer.collectAsState()
    val statusAdding = viewModel.farmerAddStatus.collectAsState()
    LaunchedEffect(address, imageUrl, mobileNumber, name, password) {
        // Validate mobile number and other fields
        mobileNumberError = if (mobileNumber.length in 1..9) {
            "Mobile number must be 10 digits"
        } else {
            ""
        }

        // Check if all fields are valid
        enabledButton.value = address.isNotEmpty() &&
                imageUrl.isNotEmpty() &&
                mobileNumber.length == 10 &&
                name.isNotEmpty() &&
                password.isNotEmpty()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Add Farmer", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 15.dp)
        ) {
            item {

                // Name Input
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {

                // Name Input
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {

                // Name Input
                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image Url") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
//                ImageUploadComponent{image->
//                    imageUrl = image
//                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Mobile Number Input with Error Message
            item {
                Column {
                    TextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        label = { Text("Mobile Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedPlaceholderColor = Color.Gray,
                            focusedLabelColor = Color.Gray,
                            focusedIndicatorColor = Color.Gray
                        )
                    )

                    // Show error message if applicable
                    if (mobileNumberError.isNotEmpty()) {
                        Text(
                            text = mobileNumberError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {

                // Name Input
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Submit Button
            item {
                Button(
                    onClick = {
                        val farmerData = FarmerData(
                            name = name,
                            address = address,
                            imageUrl = imageUrl,
                            mobileNumber = mobileNumber,
                            password = password
                        )
                        viewModel.quickRegister(farmerData)
                        address = ""
                        name = ""
                        mobileNumber = ""
                        imageUrl = ""
                        password = ""
                        Log.d("SuccessfullLOG", "farmer added quickly")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabledButton.value,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primeGreen,
                        contentColor = Color.Black,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    if(loading.value){
                        CircularProgressIndicator(modifier = Modifier.size(20.dp) , color= Color.Black)
                    }else{
                    Text("Submit")}
                }
            }
        }

        if(statusAdding.value){
            AlertDialog(
                onDismissRequest = { viewModel.resetAddFarmerStatus() },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.resetAddFarmerStatus()
                        navController.popBackStack()
                    }) {
                        Text("OK")
                    }
                },
                title = { Text(text = "Success") },
                text = { Text(text = "Farmer added successfully.") }
            )
        }
    }
}
