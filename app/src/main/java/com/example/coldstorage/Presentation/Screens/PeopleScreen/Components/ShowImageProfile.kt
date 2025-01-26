package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import java.lang.Math.abs

@Composable
fun LetterAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = Dp(50f)
) {
    // Generate consistent color from name
    val color = generateColorFromName(name)

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.first().uppercase(),
            color = Color.White,
            fontSize = size.value.times(0.5).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Helper function to generate color
fun generateColorFromName(name: String): Color {
    val hash = abs(name.hashCode())
    return Color(
        red = (hash % 256).toFloat() / 256f,
        green = ((hash / 256) % 256).toFloat() / 256f,
        blue = ((hash / 65536) % 256).toFloat() / 256f,
        alpha = 1f
    )
}
