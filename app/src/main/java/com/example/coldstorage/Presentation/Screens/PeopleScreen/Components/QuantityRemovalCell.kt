package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Util.outgoingEntry
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.ReceiptRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Close as Close1

@Composable
fun QuantityRemovalComponent(
    rowIndex: Int,
    currentQuantity: String,
    initialQuantity: String,
    openDialogState: MutableState<Boolean>,
    qtyToRemoveState: MutableState<String>,
    primeGreen: Color,
    row: ReceiptRow,
    outgoingResponseBody: MutableList<OutgoingDataClassItem>,
    columnIndex: Int = 0,
    modifier: Modifier = Modifier,
    updateStateFunction: (value : String) -> Unit = {}
//    outgoingEntry: (qtyToRemove: MutableState<String>, row: Any, outgoingResponseBody: Any, columnIndex: Int) -> Unit
) {

    Box(
        modifier = modifier.wrapContentSize() // Use the passed modifier instead of adding weight here
    ) {
        // Clickable block that shows current and initial quantities
        ClickableBlock(
            cell = currentQuantity,
            cellTwo = initialQuantity,
            qtyToRemove = qtyToRemoveState.value,
            isSelected = false, // You may want to pass this as a parameter too
            onToggle = { _ ->
                openDialogState.value = true
            },
            saveSelected = {}
        )

        val textSize = when {
            qtyToRemoveState.value.length > 2 -> 7.sp
            qtyToRemoveState.value.length == 2 -> 8.sp
            else -> 10.sp
        }
        // Badge showing quantity to be removed (improved circular design)
        if (qtyToRemoveState.value.isNotEmpty() && qtyToRemoveState.value != "0") {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = 1.dp, y = 15.dp)
                    .background(color = Color.Red, shape = CircleShape)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = qtyToRemoveState.value,
                    fontSize = textSize,
                    color = Color.White,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentSize(align = Alignment.Center)

                )
            }
        }
    }

    // Dialog for entering quantity to remove
    if (openDialogState.value) {
        AlertDialog(
            onDismissRequest = { openDialogState.value = false },
            confirmButton = { /* Moved to the dialog content */ },
            text = {
                Column {
                    // Dialog header with title and close button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Quantity to be removed",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = {
                                qtyToRemoveState.value = ""
                                openDialogState.value = false
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close1,
                                contentDescription = "Close dialog",
                                tint = Color.Gray
                            )
                        }
                    }

                    // Show current available quantity
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "Current Available Quantity: ")
                        Text(text = currentQuantity, color = primeGreen)
                    }

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    // Input field for quantity to remove
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Enter Qty: ")
                            ColdOpTextField(
                                value = qtyToRemoveState.value,
                                onValueChange = {
                                    qtyToRemoveState.value = it
                                    updateStateFunction(it)
                                                },
                                placeholder = "Enter Quantity",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                )
                            )
                        }

                        // Validation message
                        val inputQty = qtyToRemoveState.value.toIntOrNull()
                        val currentQty = currentQuantity.toIntOrNull() ?: 0
                        if (inputQty != null && currentQty < inputQty) {
                            Text(
                                text = "Cannot remove more than current qty!",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    // Save button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    // Integrated outgoingEntry function call
                                    if (qtyToRemoveState.value.isNotEmpty()) {
                                        outgoingEntry(
                                            qtyToRemoveState,
                                            row,
                                            outgoingResponseBody,
                                            columnIndex
                                        )
                                    } else {
                                        outgoingEntry(
                                            mutableStateOf("0"),
                                            row,
                                            outgoingResponseBody,
                                            columnIndex
                                        )
                                    }

                                    delay(300)
                                    openDialogState.value = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primeGreen,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        )
    }
}