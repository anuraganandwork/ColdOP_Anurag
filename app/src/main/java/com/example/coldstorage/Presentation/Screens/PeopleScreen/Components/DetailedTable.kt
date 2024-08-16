package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableView(selectedRow:MutableState<String?>, isDailogOpen: MutableState<Boolean>) {
    val tableData = listOf(
        TableRow("11/12/19", "616", "Pukhraj", "2", "4", "A", "438", "Incoming"),
        TableRow("1/5/08", "486", "Diamond", "5", "3", "F", "656", "Incoming"),
        TableRow("23/4/08", "567", "Chipsona", "6", "1", "V", "786", "Outgoing"),

        TableRow("15/9/08", "876", "Diamond", "2", "3", "B", "456", "Incoming"),

        TableRow("30/6/08", "676", "Pukhraj", "8", "2", "D", "236", "Outgoing"),

        // Add the rest of the rows here
    )
//    var selectedRow = remember{ mutableStateOf<String?>(null) }
//    var isDailogOpen= remember { mutableStateOf<Boolean>(false) }


    LazyColumn {
        item { TableHeader() }
        items(tableData) { row ->
            TableRowItem(row , selectedRow=selectedRow, isDailogOpen = isDailogOpen)
            Spacer(modifier = Modifier.height(2.dp)) // Adjust the height to control spacing
//            if ( selectedRow.value == row.receiptVoucher &&isDailogOpen.value){
//                LotDetailsDialog(reciept = row.receiptVoucher , bags = row.bagDetails )
//            }
        }
    }


}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        RawTableCell("Date", width = 60.dp)
        RawTableCell("Receipt", width = 50.dp)
        RawTableCell("Variety", width = 60.dp)
        Column(modifier = Modifier.width(60.dp)) {
            Text("Address", fontWeight = FontWeight.Bold, fontSize = 10.sp)
            Row {
                RawTableCell("R", width = 20.dp)
                RawTableCell("F", width = 20.dp)
                RawTableCell("C", width = 20.dp)
            }
        }
        RawTableCell("Bags", width = 40.dp)
        RawTableCell("Type", width = 60.dp)
    }
}

@Composable
fun TableRowItem(row: TableRow, selectedRow:MutableState<String?>, isDailogOpen:MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                selectedRow.value = row.receiptVoucher
                isDailogOpen.value = true
                Log.d("PopOP","OPOPO ${selectedRow.value}")
            }
    ) {
        RawTableCell(row.date, width = 60.dp)
        RawTableCell(row.receiptVoucher, width = 50.dp, fontWeight = FontWeight.Bold)
        RawTableCell(row.variety, width = 60.dp)
        Row(modifier = Modifier.width(60.dp)) {
            RawTableCell(row.row, width = 20.dp)
            RawTableCell(row.floor, width = 20.dp)
            RawTableCell(row.chamber, width = 20.dp)
        }
        RawTableCell(row.bagDetails, width = 40.dp, fontWeight = FontWeight.Bold)
        RawTableCell(row.type, 0.24f, color = if (row.type == "Incoming") Color(0xFF22C55E) else Color.Red)
    }


//    if (isDailogOpen.value){
//        LotDetailsDialog(reciept = row.receiptVoucher , bags = row.bagDetails )
//    }
}

@Composable
fun RowScope.RawTableCell(text: String, weight: Float? = null, width: Dp?= null, color: Color = Color.Black, fontWeight: FontWeight ?= null) {
    Text(
        text = text,
        modifier = when { weight != null -> Modifier.weight(weight)
                        width != null -> Modifier.width(width)
                         else -> Modifier} ,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 10.sp,
        fontWeight= fontWeight
    )
}

data class TableRow(
    val date: String,
    val receiptVoucher: String,
    val variety: String,
    val row: String,
    val floor: String,
    val chamber: String,
    val bagDetails: String,
    val type: String
)