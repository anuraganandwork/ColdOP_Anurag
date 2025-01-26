package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageUploadComponent(onUpload:(url:String)->Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var base64Image by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Image picker launcher
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            // Convert to Base64
            val inputStream = context.contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()
            base64Image = bytes?.let { Base64.getEncoder().encodeToString(it) }
        }
    }

    Column {
        Button(onClick = { imageLauncher.launch("image/*") }) {
            Text("Select Image")
        }

        // Display selected image
        imageUri?.let { uri ->
            val painter = rememberAsyncImagePainter(uri)
            Image(painter = painter, contentDescription = "Selected Image")
        }

        // Send base64 to backend when needed
        Button(onClick = {
            base64Image?.let { encodedImage ->
                // Call your backend upload function here
               // uploadImageToBackend(encodedImage)
                onUpload(encodedImage)
            }
        }) {
            Text("Upload Image")
        }
    }
}
