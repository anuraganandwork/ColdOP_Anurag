package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AssignLocation(onContinue:()->Unit, onClick:()->Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    val chamber = remember{
        mutableStateOf("")
    }
    val floor = remember{
        mutableStateOf("")
    }
    val row = remember{
        mutableStateOf("")
    }
    val rack = remember{
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Assign location" , fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Close, contentDescription ="Close sheet " ,
                modifier = Modifier.clickable {
                    onClick()
                })
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Column {
            Text(text = "Current Reciept Number : ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.padding(10.dp))
            
            Text(text = "Enter Location Details", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = "Set the respective location in your cold")
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Chamber", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = chamber.value,
                    onValueChange = { text -> chamber.value = text },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Floor", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = floor.value,
                    onValueChange = { text -> chamber.value = text },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Row", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = row.value,
                    onValueChange = { text -> chamber.value = text },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Rack", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                BasicTextField(
                    value = rack.value,
                    onValueChange = { text -> chamber.value = text },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(
                            BorderStroke(1.dp, SolidColor(Color.Gray)), // Default border color
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 5.dp)
                        .width(134.dp)
                        .height(40.dp),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Surface(modifier = Modifier
                    .padding(10.dp)
                    .background(Color.Green)
                    .clickable {
                        onClick()
                    }
                ) {
                    Text(text = "Previous" , modifier = Modifier
                        .background(Color.Green)
                        .padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Surface(modifier = Modifier
                    .padding(10.dp)
                    .background(Color.Green)
                    .clickable {
  // Todo
                    }
                ) {
                    Text(text = "Continue" , modifier = Modifier
                        .background(Color.Green)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .clickable { onContinue() })
                }
            }

        }
    }
}