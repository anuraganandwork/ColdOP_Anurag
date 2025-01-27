package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coldstorage.R
import com.example.coldstorage.ui.theme.dropdownGrey
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.textBrown

@Composable
fun ColdOpDropDown(label: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(label) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Button(onClick = { expanded = !expanded } ,
            shape = RoundedCornerShape(10.dp) ,
    modifier = Modifier.padding(vertical = 1.dp),
             colors= ButtonColors(
                containerColor = dropdownGrey ,
                contentColor = lightGrayBorder,
                disabledContainerColor = dropdownGrey, disabledContentColor = textBrown
            ) , border = BorderStroke(width = .9.dp, lightGrayBorder)
            ) {
            
            Text(text = selectedOption)
            Icon(painter = painterResource(id = R.drawable.down) ,
                 contentDescription ="dropdown" ,modifier = Modifier.width(30.dp).height(20.dp),
                tint = textBrown)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option)} , onClick = {
                        selectedOption = option
                        expanded = false
                        onSelect(option)}
                )
            }
        }
    }
}

//11:00