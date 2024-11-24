package com.example.coldstorage.Presentation.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.Auth.CustomLoginPage
import com.example.coldstorage.Presentation.Screens.Auth.FarmerQuickAddInputForm
import com.example.coldstorage.Presentation.Screens.Auth.StoreAdminRegistrationForm
import com.example.coldstorage.Presentation.Screens.OnboardingScreens.pickYourRole
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(navHostContorller : NavHostController){
    val authViewmodel : AuthViewmodel = hiltViewModel()
   val isLoggedIn= authViewmodel.isLoggedIn.collectAsState()
    NavHost(navController = navHostContorller, startDestination = if(isLoggedIn.value) Sections.StoreOwner.route else Sections.roleSelectionScreen.route ){

     composable(route = Sections.roleSelectionScreen.route){
         pickYourRole{roles ->
             val destination = when(roles){
                 Roles.Farmer -> Sections.Farmer.route
                 Roles.ColdStoreOwnere -> Sections.StoreOwner.route
             }
        navHostContorller.navigate(AllScreens.LogIn.name)
         }
     }

     composable(route= Sections.StoreOwner.route){

            bottomNav(navHostContorller)


     }

     composable(route= Sections.Farmer.route){
         FarmerBottomNav()
     }

     composable(route = AllScreens.StoreAdminRegistrationForm.name){
         StoreAdminRegistrationForm(navController = navHostContorller )
     }



     composable(route = AllScreens.LogIn.name){

         CustomLoginPage(navController = navHostContorller)
     }

 }
}

//1159