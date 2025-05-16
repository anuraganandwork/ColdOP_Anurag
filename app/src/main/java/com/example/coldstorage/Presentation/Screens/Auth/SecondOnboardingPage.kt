import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.Presentation.Navigation.Sections
import com.example.coldstorage.R
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Define the UI Colors as specified
val BackgroundDark = Color(0xFFF5F4F5)
val PrimaryGreen = Color(0xFF23C45E)
val TextPrimaryColor = Color(0xFF333333)
val TextFieldBorderColor = Color(0xFF23C45E)
val TextFieldBackgroundColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SecondOnboardingPage(navController: NavController, viewmodel: AuthViewmodel , mobileNumber:String , password : String) {
    val context = LocalContext.current
    var coldStorageName by remember { mutableStateOf("") }
    var coldStorageAddress by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var storageCapacity by remember { mutableStateOf("") }

    // Image upload states
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var logoFileName by remember { mutableStateOf("") }
    var showImageUploadOptions by remember { mutableStateOf(false) }

    // Bag sizes format
    var rationFormat by remember { mutableStateOf("Ration") }
    var seedFormat by remember { mutableStateOf("Seed") }
    var no12Format by remember { mutableStateOf("No. 12") }
    var goliFormat by remember { mutableStateOf("Goli") }
    var cutTokFormat by remember { mutableStateOf("Cut & Tok") }
   var  showRegistrationMessage by  remember { mutableStateOf(false) }
    val isLoading by viewmodel.storeOwnerRegistrationLoader.collectAsState()
    val registrationResult by viewmodel.storeOwnerRegistrationResult.collectAsState()
    val registrationMessage by viewmodel.storeRegistrationMessage.collectAsState()
    LaunchedEffect(registrationResult) {
        if(registrationResult == true){
            navController.navigate(Sections.StoreOwner.route)
        }
    }
    LaunchedEffect(viewmodel.logInStatus.value ){
        if(viewmodel.logInStatus.value == "Success"){
            Log.d("ErrorLogIn",viewmodel.logInStatus.value)
            navController.navigate(Sections.StoreOwner.route)}
    }
    LaunchedEffect(registrationMessage) {
        if(registrationMessage.isNotEmpty()){
            showRegistrationMessage = true
        }
    }

    var hasStoragePermission by remember { mutableStateOf(false) }

    // Create temporary image file for camera
    val photoUri = remember {
        mutableStateOf<Uri?>(null)
    }

    // Check storage permission
    LaunchedEffect(Unit) {
        hasStoragePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request permission launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasStoragePermission = isGranted
    }

    // Gallery image picker
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            logoFileName = getFileNameFromUri(context, it) ?: "image.jpg"
        }
    }

    // Simulate button loading




    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Setup your Cold Storage",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryColor,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Cold Storage Name
            FormField(
                label = "Cold Storage Name",
                value = coldStorageName,
                onValueChange = { coldStorageName = it },
                placeholder = "Enter Cold Storage Name",
                modifier = Modifier.fillMaxWidth()
            )

            // Cold Storage Address
            FormField(
                label = "Cold Storage Address",
                value = coldStorageAddress,
                onValueChange = { coldStorageAddress = it },
                placeholder = "Enter Cold Storage Address Here",
                modifier = Modifier.fillMaxWidth()
            )

            // Contact Number
            FormField(
                label = "Cold Storage Business Contact Number",
                value = contactNumber,
                onValueChange = { contactNumber = it },
                placeholder = "Enter Mobile Number Here",
                keyboardType = KeyboardType.Phone,
                modifier = Modifier.fillMaxWidth()
            )

            // Storage Capacity
            FormField(
                label = "Cold Storage Capacity (Number Of Bags)",
                value = storageCapacity,
                onValueChange = { storageCapacity = it },
                placeholder = "Enter Total Bags Your Cold Storage Can Store",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth()
            )

            // Logo Upload
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Upload Cold Storage Logo (For Receipt Prints)",
                    fontSize = 16.sp,
                    color = TextPrimaryColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(TextFieldBackgroundColor)
                            .border(
                                width = 1.dp,
                                color = TextFieldBorderColor.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (!hasStoragePermission) {
                                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                } else {
                                    galleryLauncher.launch("image/*")
                                }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedImageUri != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(selectedImageUri),
                                    contentDescription = "Selected Logo",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    text = logoFileName,
                                    color = TextPrimaryColor,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f)
                                )
                            }
                        } else {
                            Text(
                                text = "Choose file...",
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = TextFieldBorderColor.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (!hasStoragePermission) {
                                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                } else {
                                    galleryLauncher.launch("image/*")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.upload),
                            contentDescription = "Upload",
                            tint = PrimaryGreen
                        )
                    }
                }

                AnimatedVisibility(visible = selectedImageUri != null) {
                    Text(
                        text = "Remove file",
                        fontSize = 14.sp,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable {
                                selectedImageUri = null
                                logoFileName = ""
                            }
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = Color.Gray.copy(alpha = 0.3f)
            )

            // Bag Sizes Format Section
            Text(
                text = "Set Bag Sizes Format (Optional)",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ration Format
            BagSizeFormatRow(
                label = "Ration",
                value = rationFormat,
                onValueChange = { rationFormat = it }
            )

            // Seed Format
            BagSizeFormatRow(
                label = "Seed",
                value = seedFormat,
                onValueChange = { seedFormat = it }
            )

            // No. 12 Format
            BagSizeFormatRow(
                label = "No. 12",
                value = no12Format,
                onValueChange = { no12Format = it }
            )

            // Goli Format
            BagSizeFormatRow(
                label = "Goli",
                value = goliFormat,
                onValueChange = { goliFormat = it }
            )

            // Cut & Tok Format
            BagSizeFormatRow(
                label = "Cut & Tok",
                value = cutTokFormat,
                onValueChange = { cutTokFormat = it }
            )

            // Finish Setup Button with loading state
            Spacer(modifier = Modifier.height(24.dp))

            LoadingButton(
                text = "Finish Setup",
                isLoading = isLoading,
                onClick = {
                    try {
                        viewmodel.registerStoreOwner(StoreAdminFormData(
                            "Anurag",
                            "personalAddress",
                            mobileNumber,
                            coldStorageName,
                            coldStorageAddress,
                            contactNumber,
                            password,
                            "",
                            true,
                            true




                        )  )
                    }catch (e: Exception){
                        Log.d("Finish setup" , e.message.toString())
                    }


                          },
                modifier = Modifier.fillMaxWidth()
            )

            if (showRegistrationMessage) {
                AlertDialog(
                    onDismissRequest = { showRegistrationMessage = false },
                    title = {
                        Text(text = "Registration Message")
                    },
                    text = {
                        Text(text = registrationMessage.toString())
                    },
                    confirmButton = {
                        Button(onClick = { showRegistrationMessage = false }) {
                            Text("OK")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = TextPrimaryColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TextFieldBorderColor,
                unfocusedBorderColor = TextFieldBorderColor.copy(alpha = 0.5f),
                focusedContainerColor = TextFieldBackgroundColor,
                unfocusedContainerColor = TextFieldBackgroundColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}

@Composable
fun BagSizeFormatRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = TextPrimaryColor,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TextFieldBorderColor,
                unfocusedBorderColor = TextFieldBorderColor.copy(alpha = 0.5f),
                focusedContainerColor = TextFieldBackgroundColor,
                unfocusedContainerColor = TextFieldBackgroundColor
            ),
            singleLine = true
        )
    }
}

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "buttonLoadingAnimation")
    val rotationAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnimation"
    )

    // Button animation
    val buttonScale by rememberInfiniteTransition(label = "buttonScaleAnimation").animateFloat(
        initialValue = 1f,
        targetValue = if (isLoading) 1.02f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "buttonScaleAnim"
    )

    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier
            .height(56.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
            contentColor = Color.White
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Utility function to get file name from URI
fun getFileNameFromUri(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(uri, null, null, null, null)

    return cursor?.use {
        val nameIndex = it.getColumnIndex("_display_name")
        if (nameIndex >= 0 && it.moveToFirst()) {
            it.getString(nameIndex)
        } else {
            // Fallback: extract from path
            uri.path?.substringAfterLast('/')
        }
    }
}

