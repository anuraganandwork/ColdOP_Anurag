package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.SecTableCell(text: String) {
    Box(
        modifier = Modifier
            .weight(1f)
            .border(1.dp, Color.Black)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(4.dp) // Padding inside the cell
        )
    }
}