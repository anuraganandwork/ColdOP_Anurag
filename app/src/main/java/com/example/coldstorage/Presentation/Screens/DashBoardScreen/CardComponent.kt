package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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


//           orderDaybook.orderDetails[0].bagSizes.forEach {
//                   bag->
//               incomingSum.value += bag.quantity?.currentQuantity!!
//               outgoingSum.value += bag.quantityRemoved!!
//
//
//           }


    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
        ){
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                  Row() {
                      Text(text = orderDaybook.voucher.type)
                      Text(text = orderDaybook.voucher.voucherNumber.toString())
                  }
                    
                   Row {
                       Text(text = orderDaybook.orderDetails[0].variety)
                       //Icon(imageVector = Icon)


                       Icon(painter = painterResource(id = R.drawable.down), contentDescription = "Drop down icon" ,
                           modifier = Modifier
                               .width(14.dp)
                               .height(14.dp)
                               .clickable {
                                   expanded = !expanded
                               })


                   } 
                    
                }




                Row(modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {
                        Text(text = "Dated :")
                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") orderDaybook.dateOfSubmission.toString() else orderDaybook.dateOfExtraction.toString())
                    }
                    Row {
                        Text(text = "Lot No:")
                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") incomingSum.value.toString() else outgoingSum.value.toString())

                    }
                }
                if(expanded){
                    //make this a lazyrow with bagsizes
                StockDetailsScreen(orderDaybook , orderDaybook.orderDetails[0].bagSizes )}
            }
        }


    }
}


@Composable
fun StockDetailsScreen(orderDaybook: OrderDaybook, bagsizes: List<BagSizeDaybook>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Header Text
            Text(
                text = "Stock Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )

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
                    DetailRow("Location", "")
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Chamber", it.chamber) }
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Floor", it.floor) }
                    orderDaybook.orderDetails[0].location?.let { DetailRow("Row", it.row) }
                }

                // Farmer Data
                Column {
                    DetailRow("Farmer data", "")
                    DetailRow("Name", orderDaybook.farmerId.name)
                    DetailRow("Acc No", orderDaybook.farmerId._id.trim())
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
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        //.border(0.5.dp, Color.Black)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            values.forEach { value ->

                //                        Text(text = if(orderDaybook.voucher.type == "RECEIPT") incomingSum.value.toString() else outgoingSum.value.toString())
                Text(
                    text = if(orderDaybook.voucher.type == "RECEIPT") value.quantity?.currentQuantity.toString() else value.quantityRemoved.toString() ,
                    fontSize = 10.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                       // .border(0.5.dp, Color.Black)
                )
            }
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
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label :",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
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
