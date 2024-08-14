package com.example.coldstorage

import android.annotation.SuppressLint
import android.app.NativeActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.Navigation.Nav
import com.example.coldstorage.Presentation.Navigation.bottomNav
import com.example.coldstorage.ui.theme.ColdStorageTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColdStorageTheme {
                // A surface container using the 'background' color from the theme
               // Nav()
                //val viewModelStoreOwner: ViewModelStoreOwner? = LocalViewModelStoreOwner.current

                val  navController: NavHostController = rememberNavController()
                //bottomNav(navController)

                    Nav(navHostContorller = navController)

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColdStorageTheme {
        Greeting("Anurag")
    }
}