package com.example.coldstorage.Presentation.Navigation

import FirstBottomSheet
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.SecondBottomSheet
import com.example.coldstorage.Presentation.Screens.OfflineScreen.Offline
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingOrderSuccess
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingSecondScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Outgoing.OutgoingStockScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.People
import com.example.coldstorage.Presentation.Screens.PeopleScreen.farmerDetailedScreen
import com.example.coldstorage.Presentation.Screens.PeopleScreen.storeOrRetrieve
import com.example.coldstorage.Presentation.Screens.SettingScreen.Setting
import com.example.coldstorage.R
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import okhttp3.internal.wait

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
            icon = R.drawable.coldophome
            ,
            route = AllScreens.Dashboard.name,
            number =  0,
            selectedIcon = R.drawable.filledcoldophome
        ),
        NavigationItem(
            label = "People",
            icon = R.drawable.coldoppeople,
            route = AllScreens.People.name,
            number = 1,
            selectedIcon = R.drawable.filledcoldoppeople


        ),
        NavigationItem(
            label = "Dashboard",
            icon = R.drawable.coldoppeichartt,
            route = AllScreens.Offline.name,
            number = 2,
            selectedIcon = R.drawable.filledcoldoppiechart

        ),
        NavigationItem(
            label = "Setting",
            icon = R.drawable.coldopsettings,
            route = AllScreens.Setting.name,
            number = 3,
            selectedIcon = R.drawable.coldopsettings

        )
    )
    var selectedItem by remember { mutableStateOf(items[0]) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
      bottomBar = {
          if (currentRoute != AllScreens.StoreOrRetrieve.name){
          NavigationBar(modifier = Modifier.drawBehind {
              drawLine(
                  color = Color.Black,
                  start = Offset(0f, 0f),
                  end = Offset(size.width, 0f),
                  strokeWidth = 2.dp.toPx()
              )
          }, containerColor = Color.White , tonalElevation = 7.dp) {
          items.forEach { navigationItem ->
          NavigationBarItem(
                      selected = selectedItem == navigationItem, // Doubt
                      onClick = { navHostController.navigate(route = navigationItem.route)
                                selectedItem = items[navigationItem.number]},
                      icon ={ Icon(painter = if(selectedItem == navigationItem) painterResource(id = navigationItem.selectedIcon ) else painterResource(id = navigationItem.icon), contentDescription = "" ,
                          modifier = Modifier
                              .background(
                                  color = if (selectedItem == navigationItem) primeGreen else Color.Transparent,
                                  shape = RoundedCornerShape(8.dp)
                              )
                              .padding(8.dp)
                              .size(33.dp)
                              ,
                          tint = if (selectedItem == navigationItem) Color.White else Color.Unspecified
                      )
                            },
              colors = NavigationBarItemDefaults.colors(
                  selectedIconColor = Color.Unspecified,
                  indicatorColor = Color.Transparent
              ),
              alwaysShowLabel = false)
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
                popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } }


            ){
                FarmerQuickAddInputForm(navHostController)
            }

            composable(route= AllScreens.StoreOrRetrieve.name+"/{accountNumber}/{totalIncoming}/{totalOutgoing}" ,
                arguments = listOf(navArgument("accountNumber"){
                    type = NavType.StringType
                } ,
                    navArgument("totalIncoming"){
                        type = NavType.StringType
                        nullable= true

                    } ,
                    navArgument("totalOutgoing"){
                        type = NavType.StringType
                        nullable = true

                    }
                    )

            ){
                val accNum = it.arguments!!.getString("accountNumber")
                val totalIncoming = it.arguments?.getString("totalIncoming")
                val totalOutgoing = it.arguments?.getString("totalOutgoing")

                storeOrRetrieve(accNum!! , totalIncoming ,totalOutgoing, navHostController)
            }

            composable(
                route = AllScreens.OutgoingStockScreen.name + "/{fromDaybook}/{accountNumber}",

                enterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 500) // Adjust duration to make it slower
                ) },
                exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth } },
                popEnterTransition = { slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 500)
                ) },
                popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } }

               , arguments = listOf(
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




            composable(route = AllScreens.FirstBottomSheetIncoming.name ,
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
                FirstBottomSheet(navController = navHostController, viewmodel =viewmodel )
            }

            composable(route = AllScreens.SecondBottomSheetIncoming.name
                ){
                SecondBottomSheet(
                   navController = navHostController,
                    viewmodel = viewmodel
                )
            }





        }
    }
}