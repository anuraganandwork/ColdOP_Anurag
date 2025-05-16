package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.coldstorage.R
import com.example.coldstorage.ui.theme.dropdownGrey
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.textBrown

@Composable
fun ColdOpDropDown(
    stateToUpdate: State<String>?,
    label: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    isSearchable: Boolean = false // New parameter to conditionally enable search
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(label) }
    var searchQuery by remember { mutableStateOf("") }

    // Filtered options based on search query
    val filteredOptions = remember(searchQuery, options) {
        if (searchQuery.isEmpty()) {
            options
        } else {
            options.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Button(
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(vertical = 1.dp),
            colors = ButtonColors(
                containerColor = dropdownGrey,
                contentColor = lightGrayBorder,
                disabledContainerColor = dropdownGrey,
                disabledContentColor = textBrown
            ),
            border = BorderStroke(width = 0.9.dp, lightGrayBorder)
        ) {
            if (stateToUpdate != null && stateToUpdate.value.isNotEmpty()) {
                Text(text = stateToUpdate.value)
            } else {
                Text(text = selectedOption)
            }

            Icon(
                painter = painterResource(id = R.drawable.down),
                contentDescription = "dropdown",
                modifier = Modifier
                    .width(30.dp)
                    .height(20.dp),
                tint = textBrown
            )
        }

        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    searchQuery = "" // Clear search when dismissing dropdown
                },
                modifier = Modifier
                    .border(1.dp, primeGreen)
                    .background(Color.White)
                    .heightIn(max = 300.dp)
                    .width(220.dp) // Fixed width for consistency
            ) {
                // Conditionally show search field
                if (isSearchable) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            //.height(45.dp)// Reduced to 75% of full width
                            .padding(8.dp)
                            .align(Alignment.Start), // Center the smaller text field
                        placeholder = { Text("Search...") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Show scrollable list of items
                if (filteredOptions.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No matching options") },
                        onClick = { /* Do nothing */ },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // Use vertically scrollable Column instead
                    Column(
                        modifier = Modifier
                            .heightIn(max = 250.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        filteredOptions.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                    searchQuery = "" // Clear search on selection
                                    onSelect(option)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            )

                            // Add divider after each item except the last one
                            if (index < filteredOptions.size - 1) {
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}