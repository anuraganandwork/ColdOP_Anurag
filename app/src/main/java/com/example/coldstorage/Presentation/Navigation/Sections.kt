package com.example.coldstorage.Presentation.Navigation

sealed class Sections(val route : String) {

    object  roleSelectionScreen : Sections("RoleSelection")
    object StoreOwner : Sections("StoreOwner")

    object Farmer : Sections("Farmer")

}