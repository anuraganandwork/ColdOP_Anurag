package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.Utils.BluetoothThermalPrinterManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

/**
 * Enhanced Print Dialog with Preview functionality
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PrintDialog(
    orderDaybook: OrderDaybook,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val printerManager = remember { BluetoothThermalPrinterManager(context) }

    // States
    var currentStep by remember { mutableStateOf(PrintStep.PREVIEW) }
    var isLoading by remember { mutableStateOf(false) }
    var printerDevices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedPrinter by remember { mutableStateOf<BluetoothDevice?>(null) }

    // Bluetooth permission
    val bluetoothPermission = rememberPermissionState(
        permission = Manifest.permission.BLUETOOTH_CONNECT
    )

    // Clean up when dialog is dismissed
    DisposableEffect(Unit) {
        onDispose {
            printerManager.disconnect()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (currentStep) {
                            PrintStep.PREVIEW -> "Print Preview"
                            PrintStep.PRINTER_SELECTION -> "Select Printer"
                            PrintStep.PRINTING -> "Printing..."
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (currentStep == PrintStep.PREVIEW) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Content based on current step
                when (currentStep) {
                    PrintStep.PREVIEW -> {
                        PreviewContent(
                            orderDaybook = orderDaybook,
                            printerManager = printerManager,
                            onPrintClick = {
                                // Check permissions first
                                if (!bluetoothPermission.status.isGranted) {
                                    bluetoothPermission.launchPermissionRequest()
                                } else {
                                    currentStep = PrintStep.PRINTER_SELECTION
                                    scope.launch {
                                        isLoading = true

                                        // Check if Bluetooth is enabled
                                        if (!printerManager.isBluetoothEnabled()) {
                                            errorMessage = "Bluetooth is not enabled"
                                            isLoading = false
                                            return@launch
                                        }

                                        // Try to connect to last used printer
                                        val connected = printerManager.connectToLastPrinter()
                                        if (connected) {
                                            currentStep = PrintStep.PRINTING
                                            val printed = printerManager.printReceipt(orderDaybook)
                                            if (printed) {
                                                onDismiss()
                                                return@launch
                                            } else {
                                                errorMessage = "Failed to print receipt"
                                            }
                                        }

                                        // Load available printers
                                        printerDevices = printerManager.getPairedPrinters()
                                        isLoading = false

                                        if (printerDevices.isEmpty()) {
                                            errorMessage = "No paired Bluetooth printers found. Please pair your printer in Bluetooth settings."
                                        }
                                    }
                                }
                            }
                        )
                    }

                    PrintStep.PRINTER_SELECTION -> {
                        PrinterSelectionContent(
                            isLoading = isLoading,
                            printerDevices = printerDevices,
                            errorMessage = errorMessage,
                            bluetoothPermission = bluetoothPermission,
                            selectedPrinter = selectedPrinter,
                            onPrinterSelected = { device ->
                                selectedPrinter = device
                                scope.launch {
                                    isLoading = true
                                    currentStep = PrintStep.PRINTING

                                    val connected = printerManager.connectToPrinter(device)
                                    if (connected) {
                                        val printed = printerManager.printReceipt(orderDaybook)
                                        if (printed) {
                                            onDismiss()
                                        } else {
                                            errorMessage = "Failed to print receipt"
                                            currentStep = PrintStep.PRINTER_SELECTION
                                            isLoading = false
                                        }
                                    } else {
                                        errorMessage = "Failed to connect to printer"
                                        currentStep = PrintStep.PRINTER_SELECTION
                                        isLoading = false
                                    }
                                }
                            },
                            onBackClick = {
                                currentStep = PrintStep.PREVIEW
                                errorMessage = null
                            },
                            onCancelClick = onDismiss
                        )
                    }

                    PrintStep.PRINTING -> {
                        PrintingContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun PreviewContent(
    orderDaybook: OrderDaybook,
    printerManager: BluetoothThermalPrinterManager,
    onPrintClick: () -> Unit
) {
    val previewText = remember { printerManager.generatePrintPreview(orderDaybook) }
    val lastPrinterInfo = remember { printerManager.getLastPrinterInfo() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Last printer info (if available)
        if (lastPrinterInfo.first != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Last used: ${lastPrinterInfo.first}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Preview area
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = previewText,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Print button
        Button(
            onClick = onPrintClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Print Receipt")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PrinterSelectionContent(
    isLoading: Boolean,
    printerDevices: List<BluetoothDevice>,
    errorMessage: String?,
    bluetoothPermission: com.google.accompanist.permissions.PermissionState,
    selectedPrinter: BluetoothDevice?,
    onPrinterSelected: (BluetoothDevice) -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = onBackClick) {
                Text("← Back to Preview")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!bluetoothPermission.status.isGranted) {
            // Permission request UI
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bluetooth permission is required to connect to the printer")

                Spacer(modifier = Modifier.height(8.dp))

                if (bluetoothPermission.status.shouldShowRationale) {
                    Text(
                        "This permission allows the app to connect to your thermal printer via Bluetooth",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = { bluetoothPermission.launchPermissionRequest() }
                ) {
                    Text("Grant Permission")
                }
            }
        } else if (isLoading) {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Searching for printers...")
                }
            }
        } else if (errorMessage != null) {
            // Error state
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        } else {
            // Printer list
            Text(
                text = "Select a printer:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(printerDevices) { device ->
                    PrinterDeviceItem(
                        device = device,
                        isSelected = selectedPrinter?.address == device.address,
                        onClick = { onPrinterSelected(device) }
                    )
                }
            }
        }

        // Cancel button
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCancelClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text("Cancel")
        }
    }
}

@Composable
private fun PrintingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Printing receipt...",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please wait",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PrinterDeviceItem(
    device: BluetoothDevice,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = device.name ?: "Unknown Device",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// Enum to track the current step in the printing process
private enum class PrintStep {
    PREVIEW,
    PRINTER_SELECTION,
    PRINTING
}