package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDetailDaybook


data class RationRate(
    val type: String,
    val rate: String
)

data class OutgoingCardRowData(
    val Bagtype : String,
    val Address : String ,
    val voucherNum : Int ,
    val QtyIssued:Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutgoingCard(orderDaybook: OrderDaybook) {
    //val rationRates = listOf<String>("Ration" ,"Seed", "Cut-Tok" , "No.12" , "Goli")

    val totalGoli = totalBags(orderDaybook, "Goli")
    val totalRation = totalBags(orderDaybook, "Ration")
    val totalSeed = totalBags(orderDaybook, "Seed")
    val totalCut = totalBags(orderDaybook, "Cut-tok")
    val totalNumber = totalBags(orderDaybook, "Number-12")

//    val listOfBags= mutableStateOf<List<OutgoingCardRowData>>(emptyList())
//    listOfBags.value = mapDataForOutgoingRow(orderDaybook)
    val listOfBags = mapDataForOutgoingRow(orderDaybook)

    val rationRates = listOf<RationRate>(
        RationRate("Goli", totalGoli),
        RationRate("Ration", totalRation),
        RationRate("Seed", totalSeed),
        RationRate("Cut-tok", totalCut),
        RationRate("No.12", totalNumber)


    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
    ) {
        rationRates.forEach { rate ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rate.type,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = rate.rate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
////        item {
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .background(Color.LightGray)
////                    .padding(8.dp)
////            ) {
////                listOf(
////                    "Bag Type",
////                    "Address",
////                    "R. Voucher",
////                    "Avl. Qty.",
////                    "Qty. Issued"
////                ).forEach { headerText ->
////                    Text(
////                        text = headerText,
////                        modifier = Modifier.weight(1f),
////                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
////                    )
////                }
////            }
////        }
//
//
//        items(listOfBags){item->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = 8.dp)
//            ) {
//                Text(
//                    text = item.Bagtype,
//                    modifier = Modifier.weight(1f)
//                )
//                Text(
//                    text = item.Address,
//                    modifier = Modifier.weight(1f)
//                )
//                Text(
//                    text = item.voucherNum.toString(),
//                    modifier = Modifier.weight(1f)
//                )
//                Text(
//                    text = item.QtyIssued.toString(),
//                    color = Color.Red,
//                    modifier = Modifier.weight(1f)
//                )
//
//            }
//        }
//
//
//    }

    listOfBags.forEach {item->
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = item.Bagtype,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.Address,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.voucherNum.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.QtyIssued.toString(),
                    color = Color.Red,
                    modifier = Modifier.weight(1f)
                )

            }
    }
}
fun totalBags(orderDaybook: OrderDaybook , size:String):String{
    var total = 0;
    val sum = orderDaybook.orderDetails.forEach{
       it.bagSizes.forEach {si->
           if(si.size == size){
           total= total+ si.quantityRemoved!!
       }
    }
}
return total.toString()

}

fun mapDataForOutgoingRow(orderDaybook: OrderDaybook ):List<OutgoingCardRowData>{
    // add the returned data in a list and use that in the list
    var listData = mutableListOf<OutgoingCardRowData>()
    orderDaybook.orderDetails.forEach {order->
     order.bagSizes.forEach{
         var dataaa =
             it.quantityRemoved?.let { it1 ->
                 OutgoingCardRowData(Bagtype = it.size , Address = order.location?.chamber
                         +"-"+order.location?.floor+"-"+order.location?.row ,
                     voucherNum = orderDaybook.voucher.voucherNumber, QtyIssued = it1
                 )
             }

         if(dataaa!= null){
             listData.add(dataaa)
         }
     }}

   return  listData

}
//6:00

//fun mapDataForOutgoingRow(orderDaybook: OrderDaybook): List<OutgoingCardRowData> {
//    return orderDaybook.orderDetails.flatMap { order ->
//        order.bagSizes.mapNotNull { bagSize ->
//            bagSize.quantityRemoved?.let { qty ->
//                OutgoingCardRowData(
//                    Bagtype = bagSize.size,
//                    Address = buildString {
//                        append(order.location?.chamber ?: "")
//                        append("-")
//                        append(order.location?.floor ?: "")
//                        append("-")
//                        append(order.location?.row ?: "")
//                    },
//                    voucherNum = orderDaybook.voucher.voucherNumber,
//                    QtyIssued = qty
//                )
//            }
//        }
//    }
//}