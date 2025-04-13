package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope

import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpDropDown
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.LottieAnimationCheck
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import kotlinx.coroutines.launch

// viewmodel for handling business logic

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOrderScreen(

    orderId: String,
    viewmodel: FunctionStoreOwner = hiltViewModel(),
    onBackClick: () -> Unit,
    onUpdateSuccess: () -> Unit,
) {
    val singleOrderData by viewmodel.singleOrderDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.getSingleOrder(orderId)
    }

    LaunchedEffect(singleOrderData) {
        singleOrderData?.let {
            viewmodel.loadOrderData(it)
        }
    }


    val scrollState = rememberScrollState()
    val listOfVarieties = viewmodel.allVarities.collectAsState()
    LaunchedEffect(Unit){
        viewmodel.getAllVarietiesForOrders()
    }
    // Observe success state
    LaunchedEffect(viewmodel.updateSuccess.value) {
        if (viewmodel.updateSuccess.value) {
            viewmodel.clearUpdateStatus()
            onUpdateSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Order",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                // Receipt Number Section
                Text(
                    "Current Receipt Number",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                Text(
                    viewmodel.currentReceiptNum.value,
                    color = Color.Gray,

                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider()

                // Account Name Section
                Text(
                    "Farmer Account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                Text(
                    viewmodel.farmerName.value,
                    color = Color.Gray,

                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Divider()

                // Quantities Section
                Text(
                    "Enter Quantities",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                Text(
                    "Set the quantities for each size",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                val cuttok by viewmodel.cuttok.collectAsState()
                val goli by viewmodel.goli.collectAsState()
                val twelveNumber by viewmodel.twelveNumber.collectAsState()
                val ration by viewmodel.Ration.collectAsState()
                val seedBags by viewmodel.seedBags.collectAsState()

                    QuantityField("Cut-tok", cuttok) { viewmodel.updateCutAndTok(it) }
                    QuantityField("Goli", goli) { viewmodel.updateGoli(it) }
                    QuantityField("Number-12", twelveNumber) { viewmodel.updateTwelveNumber(it) }
                    QuantityField("Ration", ration) { viewmodel.updateRation(it) }
                    QuantityField("Seed Bags", seedBags) { viewmodel.updateSeedBags(it) }


                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Location Section
                Text(
                    "Enter Location (CH-R-FL)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    "This will be used as a reference in shipping",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Chamber Field
                Text(
                    "Chamber Location",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                val chamber by viewmodel.chamber.collectAsState()
                val keyboardController = LocalSoftwareKeyboardController.current

                OutlinedTextField(
                    value = chamber,
                    onValueChange = { viewmodel.updateChamber(it) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done  // Set the action to "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()  // Hide the keyboard when "Done" is pressed
                        }
                    ),
                    maxLines = 1
                )

                // Variety Field
                Text(
                    "Variety",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                val variety by viewmodel.variety.collectAsState()
//                OutlinedTextField(
//                    value = variety,
//                    onValueChange = {
//                        viewmodel.updateVariety(it) },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp),
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done  // Set the action to "Done"
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            keyboardController?.hide()  // Hide the keyboard when "Done" is pressed
//                        }
//                    )
//                )
                listOfVarieties.value?.let { it1 ->
                    ColdOpDropDown(   stateToUpdate = viewmodel.variety.collectAsState(), label = "Select Variety", options =
                    it1, onSelect = {selectedVariety -> viewmodel.updateVariety(selectedVariety)}
                    )
                }
                // Remarks Field
                Text(
                    "Remarks",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
                val remarks  by viewmodel.remarks.collectAsState()

                OutlinedTextField(
                    value = remarks,
                    onValueChange = { viewmodel.updateRemarks(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Write any questions, personal notes...") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done  // Set the action to "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()  // Hide the keyboard when "Done" is pressed
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Error message
            viewmodel.errorMessage.value?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Fixed button at bottom
            Button(
                onClick = {
                    viewmodel.viewModelScope.launch {
                        viewmodel.updateOrder()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = !viewmodel.loading.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF04B65C)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (viewmodel.loading.collectAsState().value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Continue")
                }
            }
            val updateSuccess by viewmodel.updateSuccess.collectAsState()

            if (updateSuccess) {
                AlertDialog(
                    onDismissRequest = { viewmodel.clearUpdateStatus() },
                    title = { Text("Order Status") },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            LottieAnimationCheck()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Order edited successfully!")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {

                            viewmodel.clearUpdateStatus()
                            onUpdateSuccess()
                        }) {
                            Text("OK")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun QuantityField(
    label: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(80.dp),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}