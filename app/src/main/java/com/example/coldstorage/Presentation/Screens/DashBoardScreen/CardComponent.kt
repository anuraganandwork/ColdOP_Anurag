package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.BagSizeDaybook
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.R


@SuppressLint("SuspiciousIndentation")
@Composable
fun CardComponentDaybook(orderDaybook: OrderDaybook){
    var incomingSum = mutableStateOf(0)
    var outgoingSum = mutableStateOf(0)
//    val totalGoli = totalBags(orderDaybook, "Goli")
//    val totalRation = totalBags(orderDaybook, "Ration")
//    val totalSeed = totalBags(orderDaybook, "Seed")
//    val totalCut = totalBags(orderDaybook, "Cut-tok")
//    val totalNumber = totalBags(orderDaybook, "Number-12")
//    val totalBagsNumber = totalCut+totalGoli+totalRation+totalSeed+totalNumber

//           orderDaybook.orderDetails[0].bagSizes.forEach {
//                   bag->
//               incomingSum.value += bag.quantity?.currentQuantity!!
//               outgoingSum.value += bag.quantityRemoved!!
//
//
//           }


    Card(
        modifier = Modifier
            .animateContentSize()
            .padding(horizontal = 6.dp, vertical = 10.dp)
            ,
        elevation = CardDefaults.cardElevation(defaultElevation = 9.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )


    ){

        var expanded by rememberSaveable {
            mutableStateOf(false)
        }

        val smallString = "It consists of a reactive programming model with conciseness and ease of Kotlin programming language. It is fully declarative so that you can describe your UI by calling some series of functions that will transform your data into a UI hierarchy..."
        val expandedString = "It consists of a reactive programming model with conciseness and ease of Kotlin programming language. It is fully declarative so that you can describe your UI by calling some series of functions that will transform your data into a UI hierarchy. When the data changes or is updated then the framework automatically recalls these functions and updates the view for you.\n" +
                "\n" +
                "Composable Function is represented in code by using @Composable annotation to the function name. This function will let you define your app’s UI programmatically by describing its shape and data dependencies rather than focusing on the UI construction process. "

        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    expanded = !expanded
                }
        ){
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                  Row(modifier = Modifier.weight(1f)) {
                      Text(text = orderDaybook.voucher.type+" : " , fontSize = 13.sp , fontWeight = FontWeight.Bold)
                      Text(text = orderDaybook.voucher.voucherNumber.toString() , fontSize = 13.sp , fontWeight = FontWeight.Bold)
                  }
                    
                   Row(modifier = Modifier.weight(.5f),verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween) {
                       Text(text = orderDaybook.orderDetails[0].variety , fontSize = 13.sp , fontWeight = FontWeight.Bold)
                       //Icon(imageVector = Icon)
                       Spacer(modifier = Modifier.padding( start = 8.dp))
//                     if(expanded){
//                       Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "Drop down icon" ,
//                           modifier = Modifier
//                               .width(17.dp)
//                               .height(17.dp)
//                               .clickable {
//                                   expanded = !expanded
//                               })
//                     } else{

                       val rotationAngle by animateFloatAsState(
                           targetValue = if (expanded) 180f else 0f,
                           animationSpec = tween(durationMillis = 300)
                       )

                       Icon(
                           painter = painterResource(id = R.drawable.down),
                           contentDescription = "Drop down icon",
                           modifier = Modifier
                               .size(17.dp)
                               .graphicsLayer(rotationZ = rotationAngle) // Rotate the icon
                               .clickable { expanded = !expanded }
                       )

                       // }


                   } 
                    
                }




                Row(modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(text = "Dated : ", fontSize = 13.sp , fontWeight = FontWeight.Medium)
                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") orderDaybook.dateOfSubmission.toString() else orderDaybook.dateOfExtraction.toString() , fontSize = 13.sp , fontWeight = FontWeight.Medium)
                    }
                    Row(modifier = Modifier.weight(.5f) ,
                        horizontalArrangement = Arrangement.Start) {
                        Text(text = "Lot No : " , fontSize = 13.sp , fontWeight = FontWeight.Medium)
                        Text(text = if(orderDaybook.voucher.type == "RECEIPT")  totalIncomingBags(orderDaybook).toString() else totalOutgoingBags(orderDaybook).toString(), fontSize = 13.sp , fontWeight = FontWeight.Medium)

                    }
                }
                if(expanded){
                    //make this a lazyrow with bagsizes
                //StockDetailsScreen(orderDaybook , orderDaybook.orderDetails[0].bagSizes )
                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
                    ) {
                        StockDetailsScreen(
                            orderDaybook = orderDaybook,
                            bagsizes = orderDaybook.orderDetails[0].bagSizes
                        )
                    }






                }



            }
        }


    }
}


@Composable
fun StockDetailsScreen(orderDaybook: OrderDaybook, bagsizes: List<BagSizeDaybook>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth() ,
        color = Color.White
            //.padding(16.dp)
            //.border(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Header Text
            Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Stock Details",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                )
            }


            if(orderDaybook.voucher.type == "RECEIPT"){


            // Stock Table Row
            StockTableRow(
                orderDaybook,
                headers = bagsizes,
                values = bagsizes
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location and Farmer Data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Location Details
                Column {
                    Text(text = "Location" , fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Chamber", it.chamber) }
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Floor", it.floor) }
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Row", it.row) }
                }

                // Farmer Data
                Column {
                    Text(text = "Farmer Data" , fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    DetailRow("Name", orderDaybook.farmerId.name)
                    DetailRow("Acc No", orderDaybook.farmerId._id.take(3))
                }
            }} else{
                OutgoingCard(orderDaybook)

            }
        }
    }
}

@Composable
fun StockTableRow(
    orderDaybook: OrderDaybook,
    headers: List<BagSizeDaybook>,
    values: List<BagSizeDaybook>
) {
    Log.d("xcxcxcxc" , headers.toString())
    Log.d("xcxcxcxcvlue" , values.toString())

    if(orderDaybook.voucher.type =="RECEIPT"){


    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.border(1.dp, Color.Black)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEach { header ->
                Text(
                    text = if(header.size =="Number-12") "No.12" else header.size,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        //.border(0.5.dp, Color.Black)
                )
            }
            Text(text = "Total" ,fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            values.forEach { value ->

                //                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") incomingSum.value.toString() else outgoingSum.value.toString())
                Text(
                    text = if(orderDaybook.voucher.type == "RECEIPT") value.quantity?.initialQuantity.toString() else value.quantityRemoved.toString() ,
                    fontSize = 11.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                       // .border(0.5.dp, Color.Black)
                )
            }
            
            Text(text = totalIncomingBags(orderDaybook).toString() ,fontSize = 11.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp))
        }
    }

    } else {

        // make a card for outgoing

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(1.dp, Color.Black)
//        ) {
//            Row(modifier = Modifier.fillMaxWidth()) {
//                orderDaybook.orderDetails.forEach { it ->
//                    Text(
//                        text = it.bagSizes.size.toString(),
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(4.dp)
//                            .border(0.5.dp, Color.Black)
//                    )
//                }
//            }
//            Row(modifier = Modifier.fillMaxWidth()) {
//                values.forEach { value ->
//
//                    //                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") incomingSum.value.toString() else outgoingSum.value.toString())
//                    Text(
//                        text = if(orderDaybook.voucher.type == "RECEIPT") value.quantity?.currentQuantity.toString() else value.quantityRemoved.toString() ,
//                        fontSize = 12.sp,
//                        color = Color.Black,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(4.dp)
//                            .border(0.5.dp, Color.Black)
//                    )
//                }
//            }
//        }
    }

}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label :",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            fontSize = 11.sp,
            color = Color.Black
        )
    }
}
//@Preview
//@Composable
//fun prevCard(){
//    CardComponentDaybook()
//}
//155

@SuppressLint("SuspiciousIndentation")
fun totalIncomingBags(orderDaybook: OrderDaybook):Int{
    var totalBagsIn = 0;
        orderDaybook.orderDetails[0].bagSizes.forEach {
            totalBagsIn += it.quantity?.initialQuantity!!
        }

    return totalBagsIn
}

fun totalOutgoingBags(orderDaybook: OrderDaybook):Int{
    var totalBagsOut = 0;
    orderDaybook.orderDetails[0].bagSizes.forEach {
        totalBagsOut += it.quantityRemoved!!
    }

    return totalBagsOut
}
