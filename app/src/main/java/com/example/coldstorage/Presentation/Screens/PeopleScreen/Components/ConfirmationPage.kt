package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmationPageForOrder(onPrevious:()-> Unit, onContinue:()->Unit){

Column(modifier = Modifier.padding(horizontal = 10.dp)) {
    Text(text = "Confirmation",  fontSize = 30.sp, fontWeight = FontWeight.Bold)
}

    Spacer(modifier = Modifier.padding(20.dp))
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Row {
            Text(text = "Total bags :",  fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = "1200" , fontSize = 16.sp, fontWeight = FontWeight.Medium, )
        }
        Spacer(modifier = Modifier.padding(10.dp))

        TwoRowTable()
        Spacer(modifier = Modifier.padding(14.dp))

        Row {
            Text(text = "Address",  fontSize = 16.sp, fontWeight = FontWeight.Medium)
            //Text(text = "1200" , fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Kindly Place the Goods in the following : ",  fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray)

       AddressTable()
        Spacer(modifier = Modifier.padding(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Surface(modifier = Modifier
                .padding(vertical = 20.dp)
                .background(Color.Red)
                .clickable { onPrevious() }) {
                Text(text = "Previous" , modifier = Modifier
                    .border(1.dp, Color.Red, RoundedCornerShape(10.dp))
                    .background(Color.Red, RoundedCornerShape(10.dp))
                    .width(100.dp)
                    .height(40.dp)
                    .wrapContentSize(align = Alignment.Center)
                    , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
                )            }
            Surface(modifier = Modifier
                .padding(20.dp)
                .background(Color.Red)
                .clickable { onContinue() }) {
                Text(text = "Confirm" , modifier = Modifier
                    .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                    .background(Color.Green, RoundedCornerShape(10.dp))
                    .width(100.dp)
                    .height(40.dp)
                    .wrapContentSize(align = Alignment.Center)
                    , style = TextStyle(textAlign = TextAlign.Center, textDirection = TextDirection.Content)
                )            }

        }

    }

}