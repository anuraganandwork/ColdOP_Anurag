package com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse.Order
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ClickableBlock
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.ReceiptRow
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.getAllReciptsResponse
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.mapReceiptsToRows

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutgoingStockScreen(viewmodel: FunctionStoreOwner = hiltViewModel() ,navController: NavController) {
    var selectedVariety by remember { mutableStateOf("") }
    var selectedBagSize by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Outgoing Stock") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .padding(16.dp)

        ) {

            DropdownMenu_(
                label = "Which variety would you like to take?",
                options = listOf("Pukhraj"),
                selectedOption = selectedVariety,
                onOptionSelected = { selectedVariety = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownMenu_(
                label = "Which bag size would you like to take?",
                options = listOf("All"),
                selectedOption = selectedBagSize,
                onOptionSelected = { selectedBagSize = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("See available options", fontWeight = FontWeight.Bold)

           //StockTable()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                StockTable( viewmodel, navController)
            }






        }

    }
}

@Composable
fun DropdownMenu_(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOption.ifEmpty { "Select" })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }    , text = {Text(option)}
                )
            }
        }
    }
}

@Composable
fun StockTable(viewmodel: FunctionStoreOwner  , navController: NavController) {
    val headers = listOf("Voucher", "Variety", "Goli", "No. 12", "Ration", "Goli", "Cut & Tok", "Total")
    val selectedBlock =  remember { mutableStateOf(Color.White) }
   val selectedCells  = remember {
       mutableStateMapOf<Pair<Int , Int> , Boolean>()
   }
    val transactionAllHistory = viewmodel.transactionHistory.collectAsState() // to learn
  LaunchedEffect(Unit){
      viewmodel.getAllRecipts("66eab27610eb613c2efca3bc")
  }

    var rows by mutableStateOf<List<ReceiptRow>>(emptyList())

    when(val state = transactionAllHistory.value) {
        is getAllReciptsResponse.success -> {
            rows  = mapReceiptsToRows(state.reciptData)
            Log.d("OutgoingTable" , rows.toString())
            Log.d("OutgoingTable" , "XXxxxxxxxxxxxxxxxx1123456789")

        }
        else -> {
            Log.d("OutgoingTable" , state.toString())
        }
    }
    Log.d("OutgoingTable" , rows.toString())


Column(modifier = Modifier
    .fillMaxHeight()
    ) {


    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)) {
        item {
            Row(Modifier.fillMaxWidth()) {
                headers.forEach { header ->
                    Log.d("OutgoingTable", rows.toString())

                    Text(
                        text = header,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        items(rows.size) { rowIndex ->
            val row = rows[rowIndex]
            Log.d("Outgoing", row.toString())

            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = row.voucherNumber.toString(), // First column - voucher number
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                )

                Text(
                    text = row.variety, // Second column - variety
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                )
//                listOf(row.size, row.currentQuantity /* Add other fields if needed */).forEachIndexed { index, cell ->
//                    Log.d("Outgoing" , index.toString())
//
//                    ClickableBlock(
//                        cell = cell.toString(),
//                        isSelected = selectedCells[Pair(rowIndex, index + 2)] ?: false, // Use +2 to skip the first two columns
//                        onToggle = { isSelected ->
//                            selectedCells[Pair(rowIndex, index + 2)] = isSelected
//                        }
//                    )
//                }
                ClickableBlock(
                    cell = row.size.getOrNull(0)?.quantity?.currentQuantity?.toString() ?: "0",
                    isSelected = selectedCells[Pair(rowIndex, 2)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 2)] = isSelected
                    }
                )

                ClickableBlock(
                    cell = row.size.getOrNull(1)?.quantity?.currentQuantity?.toString() ?: "0",
                    isSelected = selectedCells[Pair(rowIndex, 3)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 3)] = isSelected
                    }
                )
                ClickableBlock(
                    cell = row.size.getOrNull(2)?.quantity?.currentQuantity?.toString() ?: "0",
                    isSelected = selectedCells[Pair(rowIndex, 4)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 4)] = isSelected
                    }
                )
                ClickableBlock(
                    cell = row.size.getOrNull(3)?.quantity?.currentQuantity?.toString() ?: "0",
                    isSelected = selectedCells[Pair(rowIndex, 5)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 5)] = isSelected
                    }
                )
                ClickableBlock(
                    cell = row.size.getOrNull(4)?.quantity?.currentQuantity?.toString() ?: "0",
                    isSelected = selectedCells[Pair(rowIndex, 6)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 6)] = isSelected
                    }
                )
                val totalQuantity = row.size
                    .take(5) // Take the first 5 elements or fewer if the list is smaller
                    .sumOf {
                        it.quantity?.currentQuantity ?: 0
                    } // Safely access currentQuantity and sum them

                ClickableBlock(
                    cell = totalQuantity.toString(),
                    isSelected = selectedCells[Pair(rowIndex, 7)]
                        ?: false, // Use +2 to skip the first two columns
                    onToggle = { isSelected ->
                        selectedCells[Pair(rowIndex, 7)] = isSelected
                    }
                )

            }
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp) , horizontalArrangement = Arrangement.End) {
        Surface(modifier = Modifier
            .padding(horizontal = 7.dp, vertical = 3.dp)
            .background(
                Color(0xFF23C45E), RoundedCornerShape(5.dp)
            )
            .clickable {
                val (firstKeys, secondKeys) = getSeparateKeys(selectedCells)
                val finalVouchers =
                    firstKeys.map { itt -> rows[itt.toInt()].voucherNumber.toString() }
                viewmodel.proceedToNextOutgoing(finalVouchers, secondKeys)
                navController.navigate(AllScreens.OutgoingSecondScreen.name)
            }

            , shape = RoundedCornerShape(5.dp) , color =Color(0xFF23C45E) ){
            Text(text = "Proceed" ,modifier = Modifier
                .padding(horizontal = 7.dp, vertical = 3.dp) )
        }
    }

}
    //add button here


}


//@Preview
//@Composable
//fun OutgoingPreview(){
//    OutgoingStockScreen()
//}

fun getSeparateKeys(selectedCells: MutableMap<Pair<Int, Int>, Boolean>): Pair<List<String>, List<String>> {
    // Extract the first key and second key as separate lists
    val firstKeyList = selectedCells.keys.map { it.first.toString() }
    val secondKeyList = selectedCells.keys.map { it.second.toString() }

    // Return the two lists as separate lists
    return Pair(firstKeyList, secondKeyList)
}
