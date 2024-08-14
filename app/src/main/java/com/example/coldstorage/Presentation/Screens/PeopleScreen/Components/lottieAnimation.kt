package com.example.coldstorage.Presentation.Screens.PeopleScreen.Components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.coldstorage.R
import kotlinx.coroutines.delay

@Composable
fun LottieAnimationCheck() {
    // Load the Lottie animation

    var startAnimation by remember{
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        delay(800L)
        startAnimation = true
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animationcheck)) // Replace with your file name
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1 ,
        isPlaying = startAnimation     // or a specific number of iterations

    )

    // LottieAnimation Composable
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(200.dp) // Adjust size as needed
    )
}


