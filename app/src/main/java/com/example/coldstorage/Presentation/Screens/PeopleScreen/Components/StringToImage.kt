package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import android.content.Context
import com.example.coldstorage.ui.theme.primeGreen

//fun stringToImage(inputText: String): Bitmap {
//    // Define dimensions and text style
//    var textSize = 18f // Size of the text
//    val padding = 16 // Padding around the text
//    val lineSpacing = 5 // Space between lines
//
//    // Paint to style the text
//    val paint = Paint().apply {
//        color = android.graphics.Color.BLACK
//        textSize = textSize
//        typeface = Typeface.DEFAULT_BOLD
//        isAntiAlias = true
//    }
//
//    // Calculate the width and height of the image
//    val textLines = inputText.split("\n")
//    val maxTextWidth = textLines.maxOf { paint.measureText(it) }.toInt()
//    val totalHeight = (textLines.size * (textSize + lineSpacing)).toInt() + padding * 2
//
//    // Create a bitmap to draw the text
//    val bitmap = Bitmap.createBitmap(
//        maxTextWidth + padding * 2, // Width
//        totalHeight, // Height
//        Bitmap.Config.ARGB_8888
//    )
//
//    // Create a canvas to draw on the bitmap
//    val canvas = Canvas(bitmap)
//    canvas.drawColor(android.graphics.Color.WHITE) // Set background color
//
//    // Draw each line of text
//    var yOffset = padding + textSize // Initial Y-offset for the first line
//    for (line in textLines) {
//        canvas.drawText(line, padding.toFloat(), yOffset, paint)
//        yOffset += textSize + lineSpacing // Move Y-offset for the next line
//    }
//
//    return bitmap
//}

fun stringToImage(
    context:Context,
    text: String,
    backgroundResId: Int,  // Resource ID for background image
    width: Int = 400,
    height: Int = 400
): Bitmap {
    // Load the background image
    val backgroundBitmap = BitmapFactory.decodeResource(context.resources, backgroundResId)
    val scaledBackground = Bitmap.createScaledBitmap(backgroundBitmap, width, height, false)

    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 18f // Adjust text size
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
    }

    // Create a blank bitmap with the given width and height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Draw the background image
    canvas.drawBitmap(scaledBackground, 0f, 0f, null)

    // Set padding and start drawing text
    val xOffset = 20f // Padding from left
    var yOffset = 40f // Padding from top

    // Split the string into lines and draw text
    val lines = text.split("\n")
    lines.forEach { line ->
        canvas.drawText(line, xOffset, yOffset, paint)
        yOffset += 24f // Adjust line height to make text tighter
    }

    return bitmap
}
