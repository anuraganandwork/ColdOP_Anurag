package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.sp
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.LocationIncomingDaybook
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
    val QtyIssued:Int,
    val availableQty : Int
)

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutgoingCard(orderDaybook: OrderDaybook) {
    //val rationRates = listOf<String>("Ration" ,"Seed", "Cut-Tok" , "No.12" , "Goli")

    val totalGoli = totalBags(orderDaybook, "Goli")
    val totalRation = totalBags(orderDaybook, "Ration")
    val totalSeed = totalBags(orderDaybook, "Seed")
    val totalCut = totalBags(orderDaybook, "Cut-tok")
    val totalNumber = totalBags(orderDaybook, "Number-12")
    val totalBagsNumber = totalCut+totalGoli+totalRation+totalSeed+totalNumber
//    val listOfBags= mutableStateOf<List<OutgoingCardRowData>>(emptyList())
//    listOfBags.value = mapDataForOutgoingRow(orderDaybook)
    val listOfBags = mapDataForOutgoingRow(orderDaybook)
Log.d("OutgoingCardDar=",listOfBags.toString())
    val rationRates = listOf<RationRate>(
        RationRate("Goli", totalGoli.toString()),
        RationRate("Ration", totalRation.toString()),
        RationRate("Seed", totalSeed.toString()),
        RationRate("Cut-tok", totalCut.toString()),
        RationRate("No.12", totalNumber.toString())


    )

 val headers = listOf<String>("Bag Type" ,"Address","R. Rec","Avl. Qty.","Issued")
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        rationRates.forEach { rate ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rate.type,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
                Text(
                    text = rate.rate,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp
                )
            }
        }
        Column(modifier = Modifier .weight(1f)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Total" ,  style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp)
            Text(text = totalBagsNumber.toString() , style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp)
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
   Row( modifier = Modifier
       .fillMaxWidth()
       .padding(vertical = 8.dp, horizontal = 8.dp)) {
       headers.forEach { 
           Text(text = it , fontSize = 11.sp, fontWeight = FontWeight.Bold,  modifier = Modifier.weight(1f)
           )
       }
   }

    listOfBags.forEach {item->
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = item.Bagtype,
                    modifier = Modifier.weight(1f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = item.Address,
                    modifier = Modifier.weight(1f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = item.voucherNum.toString(),
                    modifier = Modifier.weight(1f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
            Text(
                text = item.availableQty.toString(),
                modifier = Modifier.weight(1f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
            )
                Text(
                    text = item.QtyIssued.toString(),
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )

            }
    }


    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = "Farmer Data" , fontSize = 13.sp, fontWeight = FontWeight.Bold)
        DetailRow("Name", orderDaybook.farmerId.name)
        DetailRow("Acc No", orderDaybook.farmerId._id.take(5))
    }
}
fun totalBags(orderDaybook: OrderDaybook , size:String):Int{
    var total = 0;
    val sum = orderDaybook.orderDetails.forEach{
       it.bagSizes.forEach {si->
           if(si.size == size){
           total= total+ si.quantityRemoved!!
       }
    }
}
return total

}

fun mapDataForOutgoingRow(orderDaybook: OrderDaybook ):List<OutgoingCardRowData>{
    // add the returned data in a list and use that in the list
    var listData = mutableListOf<OutgoingCardRowData>()

    orderDaybook.orderDetails.forEach { order->
        order.incomingOrder?.incomingBagSizes?.forEach { incomingBag->
            order.bagSizes.forEach { bagSize->

                if(incomingBag.size == bagSize.size){
                var dataForOutgoingCard = bagSize.quantityRemoved?.let {
                    OutgoingCardRowData(
                        Bagtype = incomingBag.size,
                        Address = order.incomingOrder.location,
                        voucherNum = order.incomingOrder.voucher.voucherNumber,
                        QtyIssued = it,
                        availableQty = incomingBag.currentQuantity
                    )
                }
                    if(dataForOutgoingCard != null){
                        listData.add(dataForOutgoingCard)
                    }


                }


            }


        }

    }


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

//435