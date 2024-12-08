package com.example.coldstorage.Presentation.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.Auth.FarmerQuickAddInputForm
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.Dashboard
import com.example.coldstorage.Presentation.Screens.OfflineScreen.Offline
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingOrderSuccess
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingSecondScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingStockScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.People
import com.example.coldstorage.Presentation.Screens.PeopleScreen.farmerDetailedScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.storeOrRetrieve
import com.example.coldstorage.Presentation.Screens.SettingScreen.Setting
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomNav(navControllerMain: NavController){
/// because it is a different section , it will have  a different navcontroller
    val navHostController = rememberNavController()
   val  viewmodel: FunctionStoreOwner = hiltViewModel()
    val items = listOf(
        NavigationItem(
            label = "Home",
            icon = Icons.Default.Home,
            route = AllScreens.Dashboard.name,
            number =  0
        ),
        NavigationItem(
            label = "People",
            icon = Icons.Default.Person,
            route = AllScreens.People.name,
            number = 1
        ),
        NavigationItem(
            label = "Dashboard",
            icon = Icons.Default.Warning,
            route = AllScreens.Offline.name,
            number = 2
        ),
        NavigationItem(
            label = "Setting",
            icon = Icons.Default.Settings,
            route = AllScreens.Setting.name,
            number  = 3,
        )
    )
    var selectedItem by remember { mutableStateOf(items[0]) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
      bottomBar = {
          if (currentRoute != AllScreens.StoreOrRetrieve.name){
          NavigationBar {
          items.forEach { navigationItem ->
          NavigationBarItem(
                      selected = selectedItem == navigationItem, // Doubt
                      onClick = { navHostController.navigate(route = navigationItem.route)
                                selectedItem = items[navigationItem.number]},
                      icon ={ Icon(imageVector = navigationItem.icon, contentDescription = "")},
                      label = {Text(text = navigationItem.label)})
}
          } }
      }
    ) {
        NavHost(navController = navHostController, startDestination = AllScreens.Dashboard.name , modifier = Modifier.padding(it) ){

            composable(route = AllScreens.Dashboard.name){
                Dashboard(navHostController)
            }

            composable(route = AllScreens.People.name ){
                People(navHostController)
            }

            composable(route = AllScreens.Offline.name){
                Offline()
            }

            composable(route= AllScreens.Setting.name){
                Setting(navControllerMain)
            }

            composable(route = AllScreens.FarmerDetailedScreen.name+"/{accountNumber}",
                arguments = listOf(navArgument("accountNumber"){
                    type= NavType.StringType
                }) ){
                val accNum = it.arguments!!.getString("accountNumber")
                farmerDetailedScreen(accNum!!, navHostController)
            }
            composable(route = AllScreens.QuickAddFarmer.name ,
                enterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 500) // Adjust duration to make it slower
                ) },
                exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth } },
                popEnterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 500)
                ) },
                popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } }){
                FarmerQuickAddInputForm(navHostController)
            }

            composable(route= AllScreens.StoreOrRetrieve.name+"/{accountNumber}" ,
                arguments = listOf(navArgument("accountNumber"){
                    type = NavType.StringType
                })
            ){
                val accNum = it.arguments!!.getString("accountNumber")

                storeOrRetrieve(accNum!! , navHostController)
            }

            composable(
                route = AllScreens.OutgoingStockScreen.name + "/{fromDaybook}/{accountNumber}",
                arguments = listOf(
                    navArgument("fromDaybook") {
                        type = NavType.BoolType
                       },
                    navArgument("accountNumber") { type = NavType.StringType
                       }
                )
            ) { backStackEntry ->
                val fromDaybook = backStackEntry.arguments?.getBoolean("fromDaybook") ?: false
                val accNum = backStackEntry.arguments?.getString("accountNumber") ?: ""
                OutgoingStockScreen(fromDaybook = fromDaybook, accNum = accNum, viewmodel = viewmodel, navController = navHostController)
            }


            composable(route = AllScreens.OutgoingSecondScreen.name + "/{accountNumber}" ,
                arguments = listOf(navArgument("accountNumber"){
                    type = NavType.StringType
                })
            ){
                val accNum = it.arguments!!.getString("accountNumber")

                OutgoingSecondScreen(accNum!! , viewmodel, navHostController)
            }

            composable(route = AllScreens.OutgoingScreenSuccess.name,
                enterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 500) // Adjust duration to make it slower
                ) },
                exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth } },
                popEnterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 500)
                ) },
                popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } }){
                OutgoingOrderSuccess(viewmodel = viewmodel , navHostController)
            }






        }
    }
}