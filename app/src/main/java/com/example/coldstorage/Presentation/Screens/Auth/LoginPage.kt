package com.example.coldstorage.Presentation.Screens.Auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.logInData
import com.example.coldstorage.Presentation.Navigation.Sections
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpTextField
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ui.theme.primeGreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CustomLoginPage(navController: NavController, viewModel: AuthViewmodel = hiltViewModel()) {
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loadingLogIn  = viewModel.loadingLogIn.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(viewModel.logInStatus.value ){
        if(viewModel.logInStatus.value == "Success"){
            Log.d("ErrorLogIn",viewModel.logInStatus.value)
            navController.navigate(Sections.StoreOwner.route)}
    }
 //  var sharedPreference =
    if (errorMessage != null  ) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissError() },
            title = {
                Text(
                    text = "Login Failed",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(errorMessage ?: "")
            },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissError() }) {
                    Text("OK")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C3E50),
                modifier = Modifier.padding(bottom = 32.dp)
            )

//            CustomTextField(
//                value = mobileNumber,
//                onValueChange = { mobileNumber = it },
//                placeholder = "Mobile Number",
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//            )
            ColdOpTextField(value = mobileNumber, onValueChange = {mobileNumber = it},
                placeholder = "Mobile Number",                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ColdOpTextField(value = password, onValueChange = { password = it },
                placeholder = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),


                )



//            CustomTextField(
//                value = password,
//                onValueChange = { password = it },
//                placeholder = "Password",
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                onClick = {
                    val data = logInData(
                        mobileNumber= mobileNumber,
                        password = password,
                        isMobile = true

                    )
                    viewModel.logInStoreOwner(data)



                          },loadingLogIn = loadingLogIn.value ,
                text = "Log In"
            )
            Row(){
                Text("New user ? ")
                Text("Register" , modifier = Modifier.clickable {
                    navController.navigate(AllScreens.StoreAdminRegistrationForm.name)
                }, color = primeGreen)

            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visualTransformation: PasswordVisualTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        androidx.compose.foundation.text.BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color(0xFF2C3E50),
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
            visualTransformation = visualTransformation ?: androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = keyboardOptions,
           // decorationBox = { innerTextField ->
//                if (value.isEmpty()) {
//                    Text(
//                        text = placeholder,
//                        color = Color(0xFFBDC3C7),
//                        fontSize = 16.sp
//                    )
//                }
//                innerTextField()
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (value.isEmpty()) {
                            Text(
                       text = placeholder,
                                modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFBDC3C7),
                     fontSize = 16.sp,
                                textAlign = TextAlign.Start
                   )
                }
                        innerTextField()
                    }

            },
            singleLine = true
        )
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String ,
    loadingLogIn : Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(primeGreen)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center, // Centers the content within the Box
            modifier = Modifier.fillMaxSize()     // Ensures Box takes full size of Surface
        ) {
            if (loadingLogIn) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp), // Small size for the indicator
                    color = Color.White
                )
            } else {
                Text(
                    text = text,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}
//Launched effect mein navigation use krna hai , start outgoing
// outgoing ki dikkat ko fix
//342