package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order
import java.text.SimpleDateFormat
import java.util.*

data class Voucher(val type: String, val voucherNumber: Int)
data class Location(val floor: String, val row: String, val chamber: String)
data class Quantity(val initialQuantity: Int, val currentQuantity: Int)
data class BagSize(val quantity: Quantity, val size: String)
data class OrderDetail(val location: Location, val variety: String, val bagSizes: List<BagSize>)
data class Orderr(
    val voucher: Voucher,
    val id: String,
    val coldStorageId: String,
    val farmerId: String,
    val dateOfSubmission: String,
    val fulfilled: Boolean,
    val orderDetails: List<OrderDetail>,
    val createdAt: String,
    val updatedAt: String
)

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Voucher: ${order.voucher.type} - ${order.voucher.voucherNumber}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
          //  Text("Order ID: ${order.id}", style = MaterialTheme.typography.bodyMedium)
            Text("Cold Storage ID: ${order.coldStorageId}", style = MaterialTheme.typography.bodyMedium)
            Text("Farmer ID: ${order.farmerId}", style = MaterialTheme.typography.bodyMedium)
            Text("Date of Submission: ${order.dateOfSubmission}", style = MaterialTheme.typography.bodyMedium)
            Text("Fulfilled: ${if (order.fulfilled) "Yes" else "No"}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Order Details:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

            LazyColumn {
                items(order.orderDetails) { detail ->
                    Text(text = "Variety "+detail.variety)
                    Text(text = "Location"+ detail.location)
                    Text(text = "Bags "+detail.bagSizes.toString())
                    Spacer(modifier = Modifier.padding(15.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Created: ${formatDate(order.createdAt)}", style = MaterialTheme.typography.bodySmall)
            Text("Updated: ${formatDate(order.updatedAt)}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun OrderDetailItem(detail: com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.OrderDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Location: ${detail.location}")
            Text("Variety: ${detail.variety}")
            detail.bagSizes.forEach { bagSize ->
                Text("Size: ${bagSize.size}")
                Text("Initial Quantity: ${bagSize.quantity.initialQuantity}")
                Text("Current Quantity: ${bagSize.quantity.currentQuantity}")
            }
        }
    }
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

