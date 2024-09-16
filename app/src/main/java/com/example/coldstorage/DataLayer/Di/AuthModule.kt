package com.example.coldstorage.DataLayer.Auth

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Headers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val AUTH_PREFS = "AuthPrefs"
        private const val COOKIE_PREFS = "CookiePrefs"
        private const val AUTH_TOKEN_KEY = "AUTH_TOKEN"
        private const val COOKIE_KEY = "COOKIE"
    }

    fun saveAuthToken(token: String) {
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTH_TOKEN_KEY, token)
            .apply()
    }

    fun getAuthToken(): String {
        Log.d("TokenWork",context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
            .getString(AUTH_TOKEN_KEY, "") ?: "")
        return context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
            .getString(AUTH_TOKEN_KEY, "") ?: ""
    }

    fun saveCookies(cookies: Headers) {
        context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(COOKIE_KEY, cookies.joinToString(";"))
            .apply()
    }

    fun getCookie(): String {
        Log.d("CookiesWork" ,context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
            .getString(COOKIE_KEY, "") ?: "")
        return context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
            .getString(COOKIE_KEY, "") ?: ""
    }

    fun isLoggedIn(): Boolean {
        return getAuthToken().isNotEmpty() || getCookie().isNotEmpty()
    }

    fun clearAuth() {
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).edit().clear().apply()
        context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE).edit().clear().apply()
    }
}