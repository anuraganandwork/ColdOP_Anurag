package com.example.coldstorage.Presentation.Navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.FarmersSide.AllScreenFarmer
import com.example.coldstorage.Presentation.FarmersSide.FarmerHomeScreen
import com.example.coldstorage.Presentation.FarmersSide.Marketplace.Marketplace

@Composable
fun FarmerBottomNav(){

    val navHostController = rememberNavController()

    val bottomBarItems= listOf(
        NavigationItem(label = "Dashboard",
            icon = Icons.Default.Home,
            route = AllScreenFarmer.Homepage.name,
            number =  0),

        NavigationItem(label = "Market",
            icon = Icons.Default.Face,
            route = AllScreenFarmer.Marketplace.name,
            number =  1)
,
        NavigationItem(label = "Dashboard",
            icon = Icons.Default.AddCircle,
            route = AllScreenFarmer.Analytics.name,
            number =  2) ,

        NavigationItem(label = "Dashboard",
            icon = Icons.Default.Settings,
            route = AllScreenFarmer.Settings.name,
            number =  3)


    )


    var selectedItem by remember{ mutableStateOf(bottomBarItems[0]) }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    Log.d("navbackstack" , navBackStackEntry.toString())

    val currentRoute  = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEach { bottombaritem ->
                    NavigationBarItem(selected = selectedItem == bottombaritem,
                        onClick = { navHostController.navigate(bottombaritem.route)
                                  selectedItem = bottomBarItems[bottombaritem.number]
                                  },
                        icon = { Icon(imageVector = bottombaritem.icon, contentDescription = "")},
                        label = { Text(text = bottombaritem.label) }
                        )



                }
            }
        }
    ) {
    NavHost(navController = navHostController, startDestination = AllScreenFarmer.Homepage.name , modifier = Modifier.padding(it)){

        //graph will be here
        composable(route = AllScreenFarmer.Homepage.name){
            FarmerHomeScreen(navHostController)
        }

        composable(route = AllScreenFarmer.Marketplace.name){
            Marketplace(navHostController)
        }

    }
    }
}