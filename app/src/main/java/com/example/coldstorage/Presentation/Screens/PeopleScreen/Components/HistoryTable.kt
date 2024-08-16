package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryTable() {
    val backgroundColor = Color.White

    Box(
        modifier = Modifier
            .width(300.dp)
            //.height(80.dp)
            .background(backgroundColor)
            .padding(8.dp) // Optional padding inside the table
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // First row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecTableCell(text = "Total Incoming")

                SecTableCell(text = "600")


            }

            // Second row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecTableCell(text = "Total outgoing")

                SecTableCell(text = "200")



            }

            //third row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecTableCell(text = "Remaining stock")

                SecTableCell(text = "400")



            }
        }
    }
}

