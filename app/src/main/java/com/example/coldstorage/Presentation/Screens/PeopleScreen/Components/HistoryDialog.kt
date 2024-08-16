package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LotDetailsDialogWrapper(
    reciept: MutableState<String?>,
    bags: String = "500",
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                //.clickable(onClick = {  })
        ) {
            LotDetailsDialog(
                reciept = reciept,
                bags = bags,
                onClose = onClose
            )
        }
    }
}

@Composable
fun LotDetailsDialog(
    reciept: MutableState<String?>,
    bags: String = "500",
    onClose: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 130.dp)
            ,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardColors(Color.White, contentColor = Color.Black, disabledContentColor = Color.Gray, disabledContainerColor = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Lot Details of receipt ${reciept.value}",
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.clickable { onClose() }
                )
            }

            Text(
                "See Breakdown of each bag size",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            LotDetailItem("Total Bags", "550", true)
            LotDetailItem("Ration / Table", "100")
            LotDetailItem("Seed", "120")
            LotDetailItem("No. 12", "-")
            LotDetailItem("Goli", "20")
            LotDetailItem("Cut & Tok", "100")
        }
    }
}

@Composable
fun LotDetailItem(label: String, value: String, isHighlighted: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                .background(
                    color = if (isHighlighted) Color(0xFFFF4E4E) else Color.LightGray,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                color = if (isHighlighted) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Example usage in a screen
