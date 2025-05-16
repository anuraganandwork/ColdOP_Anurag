package com.example.coldstorage.Presentation.Screens.Auth

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ui.theme.lightGrayBorder
import com.example.coldstorage.ui.theme.textBrown
import kotlinx.coroutines.delay

// Define the UI Colors
val PrimaryGreen = Color(0xFF23C45E)
val BackgroundDark = Color(0xFFF5F4F5)
val ErrorRed = Color(0xFFE53935)

@Composable
fun OnBoardingFirstPage(navController: NavController, viewmodel: AuthViewmodel) {
    var mobileNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // States collected from ViewModel
    val isOtpSent by viewmodel.isOtpSent.collectAsState()
    val isGetOtpLoading by viewmodel.isGetOtpLoading.collectAsState()
    val verificationResult by viewmodel.verificationResult.collectAsState()
    val isVerifyingOtp by viewmodel.isVerifyingOtp.collectAsState()

    // Password validation states
    var isPasswordValid by remember { mutableStateOf(true) }
    var passwordsMatch by remember { mutableStateOf(true) }
    var isContinueLoading by remember { mutableStateOf(false) }
    var otpErrorMessage by remember { mutableStateOf("") }

    // Effect to handle verification result changes
    LaunchedEffect(verificationResult) {
        when (verificationResult) {
            true -> {
                // Verification successful - show success message
                otpErrorMessage = ""
            }
            false -> {
                // Verification failed - show error message
                otpErrorMessage = "Verification failed. Please check your OTP and try again."
            }
            null -> {
                // Initial state or reset
                otpErrorMessage = ""
            }
        }
    }

    // Check password validity
    fun validatePasswords() {
        isPasswordValid = password.length >= 6
        passwordsMatch = password == confirmPassword
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Signup",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = textBrown,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "We could not find any registered account on your number, please set up your cold storage by filling in the details.",
                fontSize = 16.sp,
                color = lightGrayBorder,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Mobile Number Section
            FormSection(title = "Mobile Number") {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        placeholder = { Text("Enter Mobile Number") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        enabled = !isOtpSent // Disable once OTP is sent
                    )

                    LoadingButton(
                        text = if (isOtpSent) "Resend OTP" else "Get OTP",
                        isLoading = isGetOtpLoading,
                        onClick = {
                            viewmodel.sendOtp(mobileNumber)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    )
                }

                AnimatedVisibility(
                    visible = isOtpSent && !isGetOtpLoading,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "OTP Sent Successfully",
                        color = PrimaryGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            // Verify Mobile Number Section
            FormSection(title = "Verify Mobile Number") {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        placeholder = { Text("Enter OTP here to verify") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = isOtpSent && verificationResult != true, // Enable only after OTP is sent and not verified
                        isError = otpErrorMessage.isNotEmpty()
                    )

                    LoadingButton(
                        text = "Verify OTP",
                        isLoading = isVerifyingOtp,
                        onClick = {
                            viewmodel.resetVerification()
                            if (otp.isNotEmpty()) {

                                viewmodel.verifyMobile(mobileNumber, otp)
                            } else {
                                otpErrorMessage = "Please enter the OTP"
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp),
                        enabled = isOtpSent && otp.isNotEmpty() && verificationResult != true
                    )
                }

                // OTP Verification status message
                AnimatedVisibility(visible = otpErrorMessage.isNotEmpty()) {
                    Text(
                        text = otpErrorMessage,
                        color = ErrorRed,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.End
                    )
                }

                // Verification Success Message
                AnimatedVisibility(visible = verificationResult == true) {
                    Text(
                        text = "Verification successful!",
                        color = PrimaryGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            // Set New Password Section
            FormSection(title = "Set New Password") {
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        validatePasswords()
                    },
                    placeholder = { Text("Setup a strong password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = !isPasswordValid && password.isNotEmpty(),
                    enabled = verificationResult == true
                )

                if (!isPasswordValid && password.isNotEmpty()) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = ErrorRed,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            // Confirm New Password Section
            FormSection(title = "Confirm New Password") {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        validatePasswords()
                    },
                    placeholder = { Text("Confirm your strong password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = !passwordsMatch && confirmPassword.isNotEmpty(),
                    enabled = verificationResult == true
                )

                if (!passwordsMatch && confirmPassword.isNotEmpty()) {
                    Text(
                        text = "Passwords don't match",
                        color = ErrorRed,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Continue Button
            LoadingButton(
                text = "Continue",
                isLoading = isContinueLoading,
                onClick = {
                    validatePasswords()
                    if (verificationResult == true && isPasswordValid && passwordsMatch) {
                        isContinueLoading = true
                        // Short delay to show loading animation
                        try {
                            navController.navigate("${AllScreens.SecondOutgoingScreen.name}/$mobileNumber/$confirmPassword")
                        } catch (e: Exception) {
                            Log.d("Error on continue", e.message.toString())
                            isContinueLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                isFullWidthButton = true,
                enabled = verificationResult == true && isPasswordValid && passwordsMatch
            )
        }
    }
}

@Composable
fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textBrown,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFullWidthButton: Boolean = false,
    enabled: Boolean = true
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

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = PrimaryGreen,
        contentColor = Color.White,
        disabledContainerColor = PrimaryGreen.copy(alpha = 0.5f),
        disabledContentColor = Color.White.copy(alpha = 0.7f)
    )

    if (isFullWidthButton) {
        Button(
            onClick = { if (!isLoading) onClick() },
            modifier = modifier.height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = buttonColors,
            enabled = enabled && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = text, fontSize = 16.sp)
            }
        }
    } else {
        Button(
            onClick = { if (!isLoading) onClick() },
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            colors = buttonColors,
            enabled = enabled && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = text)
            }
        }
    }
}