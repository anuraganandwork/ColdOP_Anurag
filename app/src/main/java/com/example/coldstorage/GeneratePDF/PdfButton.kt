//package com.example.coldstorage.GeneratePDF
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.pdf.PdfDocument
//import android.os.Build
//import android.os.Environment
//import android.view.View
//import android.widget.Toast
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.Dp
//import androidx.core.content.FileProvider
//import java.io.File
//import java.io.FileOutputStream
//
//@Composable
//fun PdfButton(
//    content: @Composable () -> Unit,
//    context: Context,
//    contentWidth: Dp,
//    contentHeight: Dp,
//    density: Density
//) {
//    val view = ComposeView(context).apply {
//        setContent { content() }
//    }
//    Button(onClick = {
//        try {
//            createPdfFromComposeView(
//                view = view,
//                context = context,
//                width = contentWidth,
//                height = contentHeight,
//                density = density
//            )
//            //openPdf(context, file)
//
//        } catch (e: Exception) {
//            Toast.makeText(context, "Error generating PDF: ${e.message}", Toast.LENGTH_LONG).show()
//        }
//    }) {
//        Text(text = "Generate PDF")
//    }
//}
//
//@android.support.annotation.RequiresApi(Build.VERSION_CODES.KITKAT)
//@android.support.annotation.RequiresApi(Build.VERSION_CODES.KITKAT)
//@android.support.annotation.RequiresApi(Build.VERSION_CODES.KITKAT)
//@android.support.annotation.RequiresApi(Build.VERSION_CODES.KITKAT)
//fun createPdfFromComposeView(
//    view: View,
//    context: Context,
//    width: Dp,
//    height: Dp,
//    density: Density
//) {
//    val widthPx = with(density) { width.toPx().toInt() }
//    val heightPx = with(density) { height.toPx().toInt() }
//
//    // Create a bitmap of the view
//    val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
//    val canvas = Canvas(bitmap)
//    view.draw(canvas)
//
//    // Create PDF Document
//    val pdfDocument = PdfDocument()
//    val pageInfo = PdfDocument.PageInfo.Builder(widthPx, heightPx, 1).create()
//    val page = pdfDocument.startPage(pageInfo)
//    page.canvas.drawBitmap(bitmap, 0f, 0f, null)
//    pdfDocument.finishPage(page)
//
//    // Save the PDF
//    val pdfFile = File(
//        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
//        "example.pdf"
//    )
//    try {
//        FileOutputStream(pdfFile).use { outputStream ->
//            pdfDocument.writeTo(outputStream)
//        }
//        Toast.makeText(context, "PDF saved at: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()
//    } catch (e: Exception) {
//        Toast.makeText(context, "Error saving PDF: ${e.message}", Toast.LENGTH_LONG).show()
//    } finally {
//        pdfDocument.close()
//    }
//}
//
//fun createBitmapFromComposeView(context: Context, content: @Composable () -> Unit): Bitmap {
//    val composeView = ComposeView(context).apply {
//        setContent {
//            content()
//        }
//    }
//
//    // Measure and layout
//    composeView.measure(
//        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//    )
//    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)
//
//    // Create Bitmap
//    val bitmap = Bitmap.createBitmap(
//        composeView.measuredWidth,
//        composeView.measuredHeight,
//        Bitmap.Config.ARGB_8888
//    )
//    val canvas = Canvas(bitmap)
//    composeView.draw(canvas)
//    return bitmap
//}
//fun openPdf(context: Context, file: File) {
//    try {
//        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(uri, "application/pdf")
//            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
//        }
//        context.startActivity(intent)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_SHORT).show()
//    }
//}