package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.BagSize
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Location
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.AddressDetails
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.OtherDetails
import com.example.coldstorage.Presentation.Screens.PeopleScreen.DataClass.StockDetails
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse

@Composable
fun TransactionCard(
    stockDetails: List<BagSize>, addressDetails: Location, otherDetails: OtherDetails,
    date: String, reciptNo: String, type: String,
){



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardColors( contentColor = Color.Black , containerColor = Color.White , disabledContainerColor = Color.White, disabledContentColor = Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Header
            Row( modifier = Modifier.padding(vertical = 5.dp), horizontalArrangement = Arrangement.Start) {
                Canvas(modifier = Modifier) {
                    when(type){ "incoming" ->  {   drawCircle(
                        color = Color(0xFF22C55E),
                        radius = 8.dp.toPx() // Radius in dp, converted to pixels
                    )}
                        "outgoing" ->  {drawCircle(
                            color = Color.Red,
                            radius = 8.dp.toPx() // Radius in dp, converted to pixels
                        )

                        }
                    }}
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dated: $date", fontWeight = FontWeight.SemiBold , fontSize = 15.sp)
                Text("Reciept: $reciptNo", fontWeight = FontWeight.SemiBold , fontSize = 15.sp)

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stock Details
            Text("Stock Details", style = MaterialTheme.typography.titleMedium , fontSize = 15.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Ration: ${stockDetails[0].quantity.currentQuantity}" , fontSize = 15.sp)
                    Text("Goli: ${stockDetails[1].quantity.initialQuantity}" , fontSize = 15.sp)
                    Text("Cut & Tok: ${stockDetails[2].quantity.currentQuantity}" , fontSize = 15.sp)
                }
                Column {
                    Text("No. 12: ${stockDetails[3].quantity.currentQuantity}" , fontSize = 15.sp)
                    Text("Seed: ${stockDetails[4].quantity.currentQuantity}" , fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Address and Other Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Address Details", style = MaterialTheme.typography.titleMedium , fontSize = 15.sp)
                    Text("Chamber: ${addressDetails.chamber}" , fontSize = 15.sp)
                    Text("Floor: ${addressDetails.floor}" , fontSize = 15.sp)
                    Text("Row: ${addressDetails.row}" , fontSize = 15.sp)
                }
                Column {
                    Text("Other Details", style = MaterialTheme.typography.titleMedium , fontSize = 15.sp)
                    Text("Variety: ${otherDetails.Variety}" , fontSize = 15.sp)
                    Text("Lot No.: ${otherDetails.Lotno}"  , fontSize = 15.sp)
                    Text("Marka: ${otherDetails.marka}"  , fontSize = 15.sp)
                }
            }
        }
    }
}