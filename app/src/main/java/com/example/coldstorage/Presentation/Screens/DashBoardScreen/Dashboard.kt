package com.example.coldstorage.Presentation.Screens.DashBoardScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstorage.R
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.primeRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(viewmodel: FunctionStoreOwner = hiltViewModel()){
    var query by remember { mutableStateOf("") }

    var showBottomSheetOnDaybook = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Daybook" , fontWeight = FontWeight.Bold) } , scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior() ) }) {
    Column(modifier = Modifier.padding(it)) {
        Column() {


        Row(modifier = Modifier.fillMaxWidth()) {
            Surface(shape = RoundedCornerShape(10.dp), color = primeGreen, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .clickable {
                    showBottomSheetOnDaybook.value = true
                }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add" , tint = Color.White)
                    Text(text = "Incoming" , color = Color.White)

                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp), color = primeRed,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .clickable { },
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = "Remove",
                        modifier = Modifier.size(24.dp) ,
                        tint = Color.White
                    )
                    Text(text = "Outgoing" , color = Color.White)

                }
            }


        }

        Text(text = "Transactions", fontWeight = FontWeight.Bold , fontSize = 20.sp ,  modifier = Modifier.padding(horizontal = 15.dp , vertical = 10.dp))
    } }

    if(showBottomSheetOnDaybook.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetOnDaybook.value = false },
            sheetState = sheetState, modifier = Modifier.height(700.dp),
            properties = ModalBottomSheetProperties(
                securePolicy = SecureFlagPolicy.Inherit,
                isFocusable = true,
                shouldDismissOnBackPress = true
            )){
            FirstBottomSheet(viewmodel = viewmodel)
            }



    }
    }
}


@Preview
@Composable
fun prevDay(){
    Dashboard()
}

//406

// work on the debouncing feature using jobs ,also  what is debouncing

//1223- 123