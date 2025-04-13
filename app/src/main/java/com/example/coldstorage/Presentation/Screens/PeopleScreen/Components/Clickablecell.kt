package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.ui.theme.primeRed

@Composable
fun ClickableBlock(
    cell: String,
    cellTwo: String,
    qtyToRemove: String,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit,
    saveSelected: () -> Unit
) {
    // Individual state for each block's background color
    var selectedBlock = remember { mutableStateOf(Color.White) }
    var isEnabled = cell.toInt() > 0
    Surface(
        modifier = Modifier
            .width(43.dp)
            .height(43.dp)
            .padding(start = 6.dp, top = 3.dp, end = 4.dp)
            .clickable(enabled = isEnabled ) {
                onToggle(!isSelected)
                saveSelected()
            }
    ) {
        Column {
            Column(
                modifier = Modifier
                    .width(43.dp)
                    .height(43.dp)
                    .background(
                        color = if (isSelected) Color.Green else Color.White,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (cell.toInt() > 0) Color.Green else Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Takes up half the available height
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cell,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                Divider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 1.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Takes up the other half of the height
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cellTwo,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            // Position the qtyToRemove badge in the bottom right corner
            Box(
                modifier = Modifier
                    //.align(Alignment.BottomEnd)
                    .offset(x = 12.dp, y = 12.dp) // Adjust position for the badge
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp) // Badge size
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = qtyToRemove,
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun pppppp(){
//    ClickableBlock(cell = "44", cellTwo = "34", isSelected = false , onToggle ={} ) {
//
//    }
//}