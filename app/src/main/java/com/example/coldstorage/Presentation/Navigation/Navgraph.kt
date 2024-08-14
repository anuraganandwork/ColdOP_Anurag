package com.example.coldstorage.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coldstorage.Presentation.Screens.AllScreens
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.Dashboard
import com.example.coldstorage.Presentation.Screens.OfflineScreen.Offline
import com.example.coldstorage.Presentation.Screens.PeopleScreen.People
import com.example.coldstorage.Presentation.Screens.SettingScreen.Setting

//@Composable
//fun Nav(){
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = AllScreens.Dashboard.name ){
//
//        composable(route = AllScreens.Dashboard.name){
//           Dashboard()
//        }
//
//        composable(route = AllScreens.People.name ){
//            People()
//        }
//
//        composable(route = AllScreens.Offline.name){
//            Offline()
//        }
//
//        composable(route= AllScreens.Setting.name){
//            Setting()
//        }
//    }
//}