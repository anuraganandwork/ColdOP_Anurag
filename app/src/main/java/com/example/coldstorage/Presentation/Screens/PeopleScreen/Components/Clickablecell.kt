package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClickableBlock(cell: String ,  isSelected: Boolean,
                   onToggle: (Boolean) -> Unit) {
    // Individual state for each block's background color
    var selectedBlock = remember { mutableStateOf(Color.White) }

    Surface(modifier = Modifier
        .width(37.dp)
        .padding(start = 6.dp, top = 3.dp, end = 4.dp)
        .clickable {   onToggle(!isSelected) }) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(

                color = if (isSelected) Color.Green else Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Green,
                shape = RoundedCornerShape(4.dp)
            )

        ) {
            Text(text = cell , modifier = Modifier
                .fillMaxWidth()
                //.padding(vertical = 2.dp),
                ,
                fontSize = 12.sp , textAlign = TextAlign.Center
            )

            Divider(thickness = 1.dp , color = Color.Black , modifier = Modifier.padding(horizontal = 1.dp))

            Text(text = cell , modifier = Modifier
                .fillMaxWidth()
                //.padding(vertical = 2.dp)

                ,
                fontSize = 12.sp , textAlign = TextAlign.Center
            )

        }
    }
}
