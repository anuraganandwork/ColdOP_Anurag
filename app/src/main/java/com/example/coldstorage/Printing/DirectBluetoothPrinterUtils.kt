package com.example.coldstorage.Utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.util.*

/**
 * Manager class for handling Bluetooth thermal printer connections and printing
 * Uses ESC/POS commands for thermal printer compatibility
 */
class BluetoothThermalPrinterManager(private val context: Context) {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("printer_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TAG = "ThermalPrinterManager"
        private val PRINTER_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        private const val LAST_PRINTER_ADDRESS_KEY = "last_printer_address"
        private const val LAST_PRINTER_NAME_KEY = "last_printer_name"

        // ESC/POS Commands
        private val ESC = byteArrayOf(0x1B)
        private val INIT_PRINTER = byteArrayOf(0x1B, 0x40) // ESC @
        private val LINE_FEED = byteArrayOf(0x0A)
        private val CARRIAGE_RETURN = byteArrayOf(0x0D)
        private val CUT_PAPER = byteArrayOf(0x1D, 0x56, 0x00) // GS V 0
        private val ALIGN_CENTER = byteArrayOf(0x1B, 0x61, 0x01) // ESC a 1
        private val ALIGN_LEFT = byteArrayOf(0x1B, 0x61, 0x00) // ESC a 0
        private val BOLD_ON = byteArrayOf(0x1B, 0x45, 0x01) // ESC E 1
        private val BOLD_OFF = byteArrayOf(0x1B, 0x45, 0x00) // ESC E 0
        private val DOUBLE_HEIGHT = byteArrayOf(0x1B, 0x21, 0x10) // ESC ! 16
        private val NORMAL_SIZE = byteArrayOf(0x1B, 0x21, 0x00) // ESC ! 0

        // Printer wake-up and status commands
        private val WAKE_UP = byteArrayOf(0x1B, 0x3D, 0x01) // ESC = 1 (Select printer)
        private val STATUS_REQUEST = byteArrayOf(0x10, 0x04, 0x01) // DLE EOT 1 (Printer status)
        private val CASH_DRAWER_PULSE = byteArrayOf(0x1B, 0x70, 0x00, 0x19, 0x19) // ESC p (sometimes helps wake printer)
    }

    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    /**
     * Check if Bluetooth is enabled on the device
     */
    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    /**
     * Check if Bluetooth permissions are granted
     */
    fun hasBluetoothPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Get list of paired Bluetooth devices that are likely to be printers
     */
    fun getPairedPrinters(): List<BluetoothDevice> {
        if (!hasBluetoothPermissions() || bluetoothAdapter == null) {
            Log.e(TAG, "Bluetooth permissions not granted or adapter is null")
            return emptyList()
        }

        return try {
            bluetoothAdapter!!.bondedDevices?.filter { device ->
                // Filter for devices that are likely to be printers
                val deviceName = try {
                    device.name?.lowercase() ?: ""
                } catch (e: SecurityException) {
                    ""
                }
                deviceName.contains("printer") ||
                        deviceName.contains("pos") ||
                        deviceName.contains("thermal") ||
                        deviceName.contains("receipt") ||
                        deviceName.contains("rpp") ||
                        deviceName.contains("mtp") ||
                        deviceName.contains("spp") ||
                        deviceName.contains("bt")
            } ?: emptyList()
        } catch (e: SecurityException) {
            Log.e(TAG, "Security exception getting paired devices", e)
            emptyList()
        }
    }

    /**
     * Connect to a specific Bluetooth printer with enhanced connection logic
     */
    suspend fun connectToPrinter(device: BluetoothDevice): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            disconnect() // Close any existing connection

            if (!hasBluetoothPermissions()) {
                Log.e(TAG, "Bluetooth permissions not granted")
                return@withContext false
            }

            val deviceName = try {
                device.name ?: "Unknown Device"
            } catch (e: SecurityException) {
                "Unknown Device"
            }

            Log.d(TAG, "Attempting to connect to $deviceName (${device.address})")

            // Try primary connection method
            var connected = false
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(PRINTER_UUID)
                bluetoothSocket?.connect()
                connected = bluetoothSocket?.isConnected == true
            } catch (e: IOException) {
                Log.w(TAG, "Primary connection failed, trying fallback method")

                // Fallback connection method using reflection
                try {
                    val method = device.javaClass.getMethod("createRfcommSocket", Int::class.java)
                    bluetoothSocket = method.invoke(device, 1) as BluetoothSocket
                    bluetoothSocket?.connect()
                    connected = bluetoothSocket?.isConnected == true
                } catch (fallbackException: Exception) {
                    Log.e(TAG, "Fallback connection also failed", fallbackException)
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "Security exception during connection - check Bluetooth permissions", e)
                return@withContext false
            }

            if (connected) {
                outputStream = bluetoothSocket?.outputStream

                // Initialize and wake up the printer
                initializePrinter()

                // Save as last used printer
                saveLastPrinter(device)
                Log.d(TAG, "Successfully connected and initialized printer: $deviceName")
                true
            } else {
                Log.e(TAG, "Failed to connect to printer")
                disconnect()
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception connecting to printer", e)
            disconnect()
            false
        }
    }

    /**
     * Initialize and wake up the printer
     */
    private suspend fun initializePrinter() {
        try {
            outputStream?.let { stream ->
                // Send wake-up sequence
                stream.write(WAKE_UP)
                stream.flush()
                delay(100)

                // Initialize printer
                stream.write(INIT_PRINTER)
                stream.flush()
                delay(100)

                // Send a test to ensure printer is responsive
                stream.write("TEST\n".toByteArray(Charsets.UTF_8))
                stream.flush()
                delay(200)

                Log.d(TAG, "Printer initialization complete")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error initializing printer", e)
        }
    }

    /**
     * Try to connect to the last successfully used printer
     */
    suspend fun connectToLastPrinter(): Boolean {
        val lastPrinterAddress = sharedPreferences.getString(LAST_PRINTER_ADDRESS_KEY, null)
        if (lastPrinterAddress == null) {
            Log.d(TAG, "No last printer saved")
            return false
        }

        return try {
            val device = bluetoothAdapter?.getRemoteDevice(lastPrinterAddress)
            device?.let { connectToPrinter(it) } ?: false
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to last printer", e)
            false
        }
    }

    /**
     * Get the last used printer info
     */
    fun getLastPrinterInfo(): Pair<String?, String?> {
        val address = sharedPreferences.getString(LAST_PRINTER_ADDRESS_KEY, null)
        val name = sharedPreferences.getString(LAST_PRINTER_NAME_KEY, null)
        return Pair(name, address)
    }

    /**
     * Save the last successfully connected printer
     */
    private fun saveLastPrinter(device: BluetoothDevice) {
        if (!hasBluetoothPermissions()) {
            Log.e(TAG, "Cannot save printer - no Bluetooth permissions")
            return
        }

        try {
            val deviceName = try {
                device.name ?: "Unknown"
            } catch (e: SecurityException) {
                "Unknown"
            }

            sharedPreferences.edit()
                .putString(LAST_PRINTER_ADDRESS_KEY, device.address)
                .putString(LAST_PRINTER_NAME_KEY, deviceName)
                .apply()
        } catch (e: SecurityException) {
            Log.e(TAG, "Security exception saving last printer", e)
        }
    }

    /**
     * Disconnect from the current printer
     */
    fun disconnect() {
        try {
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing connections", e)
        } finally {
            outputStream = null
            bluetoothSocket = null
        }
    }

    /**
     * Check if currently connected to a printer
     */
    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected == true
    }

    /**
     * Test print function to verify printer is working
     */
    suspend fun printTest(): Boolean = withContext(Dispatchers.IO) {
        if (outputStream == null) {
            Log.e(TAG, "Not connected to printer")
            return@withContext false
        }

        return@withContext try {
            // Wake up printer first
            outputStream!!.write(WAKE_UP)
            outputStream!!.flush()
            delay(100)

            // Initialize printer
            outputStream!!.write(INIT_PRINTER)
            outputStream!!.flush()
            delay(100)

            // Print test receipt
            printCenteredText("TEST PRINT", true, true)
            printLine()
            printLeftAlignedText("Printer is working correctly!")
            printLine()
            printLine()
            printLine()

            // Cut paper
            outputStream!!.write(CUT_PAPER)
            outputStream!!.flush()

            Log.d(TAG, "Test print completed")
            true

        } catch (e: IOException) {
            Log.e(TAG, "Error in test print", e)
            false
        }
    }

    /**
     * Generate print preview text for the receipt
     */
    fun generatePrintPreview(orderDaybook: OrderDaybook): String {
        return buildString {
            // Header
            appendLine("================================")
            appendLine("           RECEIPT              ")
            appendLine("================================")
            appendLine()

            // Date
            val date = if (orderDaybook.voucher.type == "RECEIPT") {
                orderDaybook.dateOfSubmission
            } else {
                orderDaybook.dateOfExtraction
            }
            appendLine("Date: $date")
            appendLine()

            // Basic Info
            appendLine("Type: ${orderDaybook.voucher.type}")
            appendLine("Voucher No.: ${orderDaybook.voucher.voucherNumber}")
            appendLine("Variety: ${orderDaybook.orderDetails[0].variety}")
            appendLine()

            appendLine("--------------------------------")

            // Bag Details
            if (orderDaybook.voucher.type == "RECEIPT") {
                appendLine("ADDED BAGS:")
                orderDaybook.orderDetails[0].bagSizes.forEach { bag ->
                    val quantity = bag.quantity?.initialQuantity ?: "N/A"
                    appendLine("${bag.size}: $quantity")
                }
            } else {
                appendLine("REMOVED BAGS:")
                orderDaybook.orderDetails.forEach { order ->
                    order.bagSizes.forEach { bag ->
                        val quantity = bag.quantityRemoved ?: "N/A"
                        appendLine("${bag.size}: $quantity")
                    }
                }
            }

            appendLine("--------------------------------")

            // Total
            val totalBags = if (orderDaybook.voucher.type == "RECEIPT") {
                totalIncomingBags(orderDaybook)
            } else {
                totalOutgoingBags(orderDaybook)
            }
            appendLine("TOTAL BAGS: $totalBags")
            appendLine()

            // Farmer Info
            appendLine("FARMER: ${orderDaybook.farmerId.name}")
            appendLine("ACC: ${orderDaybook.farmerId._id.take(5)}")
            appendLine()

            appendLine("================================")
            appendLine("        Powered By ColdOp       ")
            appendLine("         Thank You              ")
            appendLine("================================")
        }
    }

    /**
     * Print the receipt to the connected thermal printer with enhanced error handling
     */
    suspend fun printReceipt(orderDaybook: OrderDaybook): Boolean = withContext(Dispatchers.IO) {
        if (outputStream == null) {
            Log.e(TAG, "Not connected to printer")
            return@withContext false
        }

        return@withContext try {
            Log.d(TAG, "Starting receipt print process")

            // Wake up printer first
            outputStream!!.write(WAKE_UP)
            outputStream!!.flush()
            delay(100)

            // Initialize printer
            outputStream!!.write(INIT_PRINTER)
            outputStream!!.flush()
            delay(200) // Longer delay for initialization

            // Print header
            printCenteredText("RECEIPT", true, true)
            printLine()

            // Date
            val date = if (orderDaybook.voucher.type == "RECEIPT") {
                orderDaybook.dateOfSubmission
            } else {
                orderDaybook.dateOfExtraction
            }
            printCenteredText(date.toString())
            printLine()
            printDivider()

            // Basic information
            printLeftAlignedText("Type: ${orderDaybook.voucher.type}")
            printLeftAlignedText("Voucher No.: ${orderDaybook.voucher.voucherNumber}")
            printLeftAlignedText("Variety: ${orderDaybook.orderDetails[0].variety}")
            printLine()
            printDivider()

            // Bag details
            if (orderDaybook.voucher.type == "RECEIPT") {
                printLeftAlignedText("ADDED BAGS:", bold = true)
                orderDaybook.orderDetails[0].bagSizes.forEach { bag ->
                    val quantity = bag.quantity?.initialQuantity ?: "N/A"
                    printLeftAlignedText("${bag.size}: $quantity")
                }
            } else {
                printLeftAlignedText("REMOVED BAGS:", bold = true)
                orderDaybook.orderDetails.forEach { order ->
                    order.bagSizes.forEach { bag ->
                        val quantity = bag.quantityRemoved ?: "N/A"
                        printLeftAlignedText("${bag.size}: $quantity")
                    }
                }
            }

            printLine()
            printDivider()

            // Total bags
            val totalBags = if (orderDaybook.voucher.type == "RECEIPT") {
                totalIncomingBags(orderDaybook)
            } else {
                totalOutgoingBags(orderDaybook)
            }
            printLeftAlignedText("TOTAL BAGS: $totalBags", bold = true)
            printLine()
            printDivider()

            // Farmer information
            printLeftAlignedText("FARMER: ${orderDaybook.farmerId.name}", bold = true)
            printLeftAlignedText("ACC: ${orderDaybook.farmerId._id.take(5)}")
            printLine()
            printDivider()

            // Footer
            printCenteredText("Powered By ColdOp")
            printCenteredText("Thank You")
            printLine()
            printLine()
            printLine() // Extra lines before cut

            // Cut paper
            outputStream!!.write(CUT_PAPER)
            outputStream!!.flush()
            delay(500) // Wait for cut to complete

            Log.d(TAG, "Receipt printed successfully")
            true

        } catch (e: IOException) {
            Log.e(TAG, "Error printing receipt", e)
            false
        }
    }

    // Helper methods for printing with delays
    private suspend fun printCenteredText(text: String, bold: Boolean = false, largeSize: Boolean = false) {
        try {
            outputStream?.write(ALIGN_CENTER)
            delay(50)

            if (bold) {
                outputStream?.write(BOLD_ON)
                delay(50)
            }
            if (largeSize) {
                outputStream?.write(DOUBLE_HEIGHT)
                delay(50)
            }

            outputStream?.write(text.toByteArray(Charsets.UTF_8))
            outputStream?.write(LINE_FEED)
            outputStream?.flush()
            delay(100)

            if (bold) {
                outputStream?.write(BOLD_OFF)
                delay(50)
            }
            if (largeSize) {
                outputStream?.write(NORMAL_SIZE)
                delay(50)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error printing centered text", e)
        }
    }

    private suspend fun printLeftAlignedText(text: String, bold: Boolean = false) {
        try {
            outputStream?.write(ALIGN_LEFT)
            delay(50)

            if (bold) {
                outputStream?.write(BOLD_ON)
                delay(50)
            }

            outputStream?.write(text.toByteArray(Charsets.UTF_8))
            outputStream?.write(LINE_FEED)
            outputStream?.flush()
            delay(100)

            if (bold) {
                outputStream?.write(BOLD_OFF)
                delay(50)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error printing left aligned text", e)
        }
    }

    private suspend fun printLine() {
        try {
            outputStream?.write(LINE_FEED)
            outputStream?.flush()
            delay(50)
        } catch (e: IOException) {
            Log.e(TAG, "Error printing line", e)
        }
    }

    private suspend fun printDivider() {
        try {
            outputStream?.write(ALIGN_LEFT)
            outputStream?.write("--------------------------------".toByteArray(Charsets.UTF_8))
            outputStream?.write(LINE_FEED)
            outputStream?.flush()
            delay(100)
        } catch (e: IOException) {
            Log.e(TAG, "Error printing divider", e)
        }
    }

    // These functions should be implemented based on your OrderDaybook structure
    private fun totalIncomingBags(orderDaybook: OrderDaybook): Int {
        return orderDaybook.orderDetails.sumOf { order ->
            order.bagSizes.sumOf { bag ->
                bag.quantity?.initialQuantity ?: 0
            }
        }
    }

    private fun totalOutgoingBags(orderDaybook: OrderDaybook): Int {
        return orderDaybook.orderDetails.sumOf { order ->
            order.bagSizes.sumOf { bag ->
                bag.quantityRemoved ?: 0
            }
        }
    }
}