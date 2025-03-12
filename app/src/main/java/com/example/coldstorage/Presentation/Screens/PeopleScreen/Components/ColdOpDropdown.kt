package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.coldstorage.R
import com.example.coldstorage.ui.theme.dropdownGrey
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.primeGreen
import com.example.coldstorage.ui.theme.textBrown

@Composable
fun ColdOpDropDown(stateToUpdate: State<String>?, label: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(label) }

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
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .border(1.dp, primeGreen)
                    .background(Color.White)
                    .heightIn(max = 300.dp)
            ) {
                // Instead of using LazyColumn inside DropdownMenu, use the built-in scrolling
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                            onSelect(option)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    )

                    // Add divider after each item except the last one
                    if (index < options.size - 1) {
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
//11:00

