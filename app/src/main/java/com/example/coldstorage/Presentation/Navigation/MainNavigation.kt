package com.example.coldstorage.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coldstorage.Presentation.Screens.OnboardingScreens.pickYourRole

@Composable
fun Nav(navHostContorller : NavHostController){
 NavHost(navController = navHostContorller, startDestination = Sections.roleSelectionScreen.route ){

     composable(route = Sections.roleSelectionScreen.route){
         pickYourRole{roles ->
             val destination = when(roles){
                 Roles.Farmer -> Sections.Farmer.route
                 Roles.ColdStoreOwnere -> Sections.StoreOwner.route
             }
        navHostContorller.navigate(destination)
         }
     }

     composable(route= Sections.StoreOwner.route){

            bottomNav()


     }

     composable(route= Sections.Farmer.route){
         FarmerSection()
     }

 }
}