package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmailFilterDropdowns( expandedGroupBy:MutableState<Boolean> , selectedGroupBy:MutableState<String> , expandedSortBy:MutableState<Boolean> , selectedSortBy:MutableState<String>) {


       Row(
        modifier = Modifier.padding().fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            OutlinedButton(onClick = { expandedGroupBy.value = true }) {
                Text(selectedGroupBy.value , color = Color.Gray)
                Icon(Icons.Default.ArrowDropDown, "dropdown arrow" , tint = Color.Gray)
            }
            DropdownMenu(
                expanded = expandedGroupBy.value,
                onDismissRequest = { expandedGroupBy.value = false }
            ) {
                DropdownMenuItem( text  = { Text(text = "Only Incoming" , color = Color.Gray ) },onClick = {
                    selectedGroupBy.value = "Only Incoming"
                    expandedGroupBy.value = false
                })
                DropdownMenuItem( text  = { Text(text = "Only Outgoing" , color = Color.Gray ) }, onClick = {
                    selectedGroupBy.value = "Only Outgoing"
                    expandedGroupBy.value = false
                })
                DropdownMenuItem( text  = { Text(text = "Both" , color = Color.Gray ) },onClick = {
                    selectedGroupBy.value = "Both"
                    expandedGroupBy.value = false
                })
            }
        }

        Box {
            OutlinedButton(onClick = { expandedSortBy.value = true }) {
                Text(selectedSortBy.value , color = Color.Gray)
                Icon(Icons.Default.ArrowDropDown, "dropdown arrow" ,  tint = Color.Gray)
            }
            DropdownMenu(
                expanded = expandedSortBy.value,
                onDismissRequest = { expandedSortBy.value = false }
            ) {
                DropdownMenuItem( text  = { Text(text = "Earliest first" , color = Color.Gray) },onClick = {
                    selectedSortBy.value = "Earliest first"
                    expandedSortBy.value = false
                })
                DropdownMenuItem( text  = { Text(text = "Latest first " , color = Color.Gray ) },onClick = {
                    selectedSortBy.value = "Latest first"
                    expandedSortBy.value = false
                })
            }
        }
    }
}