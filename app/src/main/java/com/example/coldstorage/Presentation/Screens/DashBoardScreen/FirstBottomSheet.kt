package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner

@Composable
fun FirstBottomSheet(viewmodel:FunctionStoreOwner){
 var query by remember {
     mutableStateOf("")
 }

    val currentReceiptNum by viewmodel.currentRecieptNum.collectAsState(0)
    val isNameSelected = remember {
        mutableStateOf(false
        )
    }
    LaunchedEffect(Unit){
        viewmodel.getRecieptNumbers()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Search TextField
        Text(text = "Create Order", fontSize = 30.sp , fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(25.dp))
        Row(horizontalArrangement = Arrangement.Start , verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Current Reciept Number :" )
            if (currentReceiptNum !== 0){
                Text(text = currentReceiptNum.toString() , color = Color.Blue)
            } else {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            }
        }
        
        Text(text = "Enter Account Name (search and select)")
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewmodel.onSearchQuery(query)
            },
            label = { Text("Search farmers") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display search results
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(viewmodel.searchResults){
//                        Text(text = it.)
//                    }
//                }

        if(isNameSelected.value == false){
            LazyColumn(){
                items(viewmodel.searchResults){
                    Column(modifier = Modifier.clickable {
                        query = it.name
                        isNameSelected.value = true
                    }) {
                        Text(text = it.name)
                        Text(text = it.mobileNumber)

                    }

                }
            }
        }

    }
}

//1240
// for the value of textfields use purana wala mutable states of the viewmodel , and see how the update functions are called