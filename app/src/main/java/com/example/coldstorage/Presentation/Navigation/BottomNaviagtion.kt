package com.example.coldstorage.Presentation.Navigation

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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.Auth.FarmerQuickAddInputForm
import com.example.coldstorage.Presentation.Screens.Auth.StoreAdminRegistrationForm
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.Dashboard
import com.example.coldstorage.Presentation.Screens.OfflineScreen.Offline
import com.example.coldstorage.Presentation.Screens.PeopleScreen.People
import com.example.coldstorage.Presentation.Screens.PeopleScreen.farmerDetailedScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.storeOrRetrieve
import com.example.coldstorage.Presentation.Screens.SettingScreen.Setting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomNav(){
/// because it is a different section , it will have  a different navcontroller
    val navHostController = rememberNavController()

    val items = listOf(
        NavigationItem(
            label = "Dashboard",
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
            label = "Offline",
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
                Dashboard()
            }

            composable(route = AllScreens.People.name ){
                People(navHostController)
            }

            composable(route = AllScreens.Offline.name){
                Offline()
            }

            composable(route= AllScreens.Setting.name){
                Setting()
            }

            composable(route = AllScreens.FarmerDetailedScreen.name+"/{accountNumber}",
                arguments = listOf(navArgument("accountNumber"){
                    type= NavType.StringType
                }) ){
                val accNum = it.arguments!!.getString("accountNumber")
                farmerDetailedScreen(accNum!!, navHostController)
            }
            composable(route = AllScreens.QuickAddFarmer.name){
                FarmerQuickAddInputForm()
            }

            composable(route= AllScreens.StoreOrRetrieve.name+"/{accountNumber}" ,
                arguments = listOf(navArgument("accountNumber"){
                    type = NavType.StringType
                })
            ){
                val accNum = it.arguments!!.getString("accountNumber")

                storeOrRetrieve(accNum!!)
            }


        }
    }
}