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
fun AddressTable() {
    val backgroundColor = Color(0xFF967BB6)

    Box(
        modifier = Modifier
            .width(290.dp)
            .height(90.dp)
            .background(backgroundColor)
            .padding(8.dp) // Optional padding inside the table
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // First row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell(text = "Chamber")

                TableCell(text = "Floor")

                TableCell(text = "Row/Rack")



            }

            // Second row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell(text = "C")

                TableCell(text = "2")

                TableCell(text = "23")


            }
        }
    }
}
