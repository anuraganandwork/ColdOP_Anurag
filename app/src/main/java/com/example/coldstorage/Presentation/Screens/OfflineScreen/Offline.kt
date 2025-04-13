package com.example.coldstorage.Presentation.Screens.OfflineScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StoreSummaryResponse.StoreStockItem
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner



import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.coldstorage.ui.theme.bgWhite
import com.example.coldstorage.ui.theme.dropdownGrey
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.textBrown

// Data classes
data class StockSummaryResponse(
    val status: String,
    val stockSummary: List<StockItem>
)

data class StockItem(
    val variety: String,
    val sizes: List<Size>
)

data class Size(
    val size: String,
    val initialQuantity: Int,
    val currentQuantity: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Offline(viewmodel: FunctionStoreOwner = hiltViewModel()) {

    val stockData = viewmodel.StoreSummaryData.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.getStoreStockSummary()
    }

    // Calculate total stock quantity across all varieties with proper null safety
    val maxCapacity = 87000 // As per requirement
    val totalCurrentQuantity = stockData.value.stockSummary?.let { summary ->
        summary.sumOf { stockItem ->
            stockItem.sizes?.sumOf { it.currentQuantity ?: 0 } ?: 0
        }
    } ?: 0


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Summary") }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Overall total progress
                TotalStockProgress(
                    totalQuantity = totalCurrentQuantity,
                    maxCapacity = maxCapacity
                )

                Spacer(modifier = Modifier.height(16.dp))

                // List of varieties with their stock info
                LazyColumn {
                    items(stockData.value.stockSummary ?: emptyList()) { stockItem ->
                        StockItemCard(
                            stockItem = stockItem,
                            maxCapacity = maxCapacity
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TotalStockProgress(totalQuantity: Int, maxCapacity: Int) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentProgress = animateFloatAsState(
        targetValue = if (animationPlayed) totalQuantity.toFloat() / maxCapacity else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "ProgressAnimation"
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(containerColor = bgWhite , contentColor = textBrown , disabledContainerColor = bgWhite , disabledContentColor = textBrown)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Total Stock",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = currentProgress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(50.dp))
                ,
                color = primeGreen,
                trackColor = lightGrayBorder

            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$totalQuantity of $maxCapacity",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${(totalQuantity.toFloat() / maxCapacity * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StockItemCard(stockItem: StoreStockItem, maxCapacity: Int) {
    // Calculate total current quantity for this variety
    val varietyTotalQuantity = stockItem.sizes.sumOf { it.currentQuantity }

    var animationPlayed by remember { mutableStateOf(false) }
    val currentProgress = animateFloatAsState(
        targetValue = if (animationPlayed) varietyTotalQuantity.toFloat() / maxCapacity else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "VarietyProgressAnimation"
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardColors(containerColor = bgWhite , contentColor = textBrown , disabledContainerColor = bgWhite , disabledContentColor = textBrown)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = stockItem.variety,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = currentProgress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(50.dp))
                ,
                color = primeGreen,
                trackColor = lightGrayBorder

            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: $varietyTotalQuantity",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${(varietyTotalQuantity.toFloat() / maxCapacity * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Display individual size information
            stockItem.sizes.forEach { size ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "${size.size}:",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${size.currentQuantity} / ${size.initialQuantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}