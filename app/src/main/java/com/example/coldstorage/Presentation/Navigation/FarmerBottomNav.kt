package com.example.coldstorage.Presentation.Navigation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.FarmersSide.AllScreenFarmer
import com.example.coldstorage.Presentation.FarmersSide.FarmerHomeScreen
import com.example.coldstorage.Presentation.FarmersSide.Marketplace.Marketplace
import com.example.coldstorage.R

@Composable
fun FarmerBottomNav(){

    val navHostController = rememberNavController()

    val bottomBarItems= listOf(
        NavigationItem(
            label = "Dashboard",
            icon = R.drawable.coldophome,
            route = AllScreenFarmer.Homepage.name,
            number =  0,
            selectedIcon =R.drawable.coldophome ),

        NavigationItem(
            label = "Market",
            icon = R.drawable.coldophome,
            route = AllScreenFarmer.Marketplace.name,
            number =  1,
            R.drawable.coldophome)
,
        NavigationItem(
            label = "Dashboard",
            icon = R.drawable.coldophome,
            route = AllScreenFarmer.Analytics.name,
            number =  2,
            R.drawable.coldophome) ,

        NavigationItem(
            label = "Dashboard",
            icon = R.drawable.coldophome,
            route = AllScreenFarmer.Settings.name,
            number =  3,
            R.drawable.coldophome)


    )


    var selectedItem by remember{ mutableStateOf(bottomBarItems[0]) }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    Log.d("navbackstack" , navBackStackEntry.toString())

    val currentRoute  = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar() {
                bottomBarItems.forEach { bottombaritem ->
                    NavigationBarItem(selected = selectedItem == bottombaritem,
                        onClick = { navHostController.navigate(bottombaritem.route)
                                  selectedItem = bottomBarItems[bottombaritem.number]
                                  },
                        icon = { Icon(painter = painterResource(id =  bottombaritem.icon), contentDescription = "" , modifier = Modifier.size(26.dp))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Unspecified,
                            indicatorColor = Color.Transparent
                        )

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