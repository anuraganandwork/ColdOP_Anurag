package com.example.coldstorage.Presentation.Navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val label: String,
    val icon : ImageVector,
    val route : String,
   val  number:Int
)
