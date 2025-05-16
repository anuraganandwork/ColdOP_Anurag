package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.DataLayer.Api.FarmerDataSave
import com.example.coldstorage.ui.theme.primeGreen

@Composable
fun ColdOpAlertMessage( title:String? , message :String? , acceptAction:()->Unit , rejectAction:()->Unit){
    AlertDialog(onDismissRequest = { rejectAction() }, confirmButton = {} ,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title?:"Alert",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                // Add close icon here
                IconButton(
                    onClick = {
                       rejectAction()
                    },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close dialog",
                        tint = Color.Gray
                    )
                }
            }
        }   ,     text = { Text(text = message?:"N/A")

            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = {
                       acceptAction()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = primeGreen,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                   Text(text = "OK")
                }


            }
        }
    )
}


