package com.example.coldstorage

import android.annotation.SuppressLint
import android.app.NativeActivity
import android.os.Bundle
import android.util.Log
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
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Api.StoreAdminFormData
import com.example.coldstorage.Presentation.Navigation.Nav
import com.example.coldstorage.Presentation.Navigation.bottomNav
import com.example.coldstorage.ui.theme.ColdStorageTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coldOpApi:ColdOpApi
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {

            try{var response =   coldOpApi.registerStoreAdmin(
                StoreAdminFormData(
                    name = "Developer Singh",
                    personalAddress = "Amritsar",
                    mobileNumber = "9672422856",
                    coldStorageName = "Khan cold ",
                    coldStorageAddress = "Pakistan",
                    coldStorageContactNumber = "1234567890",
                    password = "123456",
                    imageUrl = "https://images.fineartamerica.com/images/artworkimages/mediumlarge/3/indian-farmer-kisan-ekta-sukhpal-grewal.jpg",
                    isVerified = true
                )
            )

                Log.d("FrontendStore" , "StoreAdminSaved"+response.body())

            } catch(E:Exception){
                Log.d("FrontendStore", "HHHHHHhh"+E)
            }



        }

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

//1136