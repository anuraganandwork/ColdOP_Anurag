package com.example.coldstorage.DataLayer.Di

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.coldstorage.DataLayer.Api.ColdOpApi
import com.example.coldstorage.DataLayer.Auth.AuthManager
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.SelectedCellData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton



class AuthInterceptor @Inject constructor( @ApplicationContext private  val context: Context) : Interceptor {



    @SuppressLint("LongLogTag")
    override fun intercept(chain: Interceptor.Chain): Response {

        val sharedPreference = context.getSharedPreferences("AuthToken" , Context.MODE_PRIVATE)

        val originalRequest = chain.request()
        val token = sharedPreference.getString("auth_token", null)
        //val cookie = authManager.getCookie()
        //val newRequestBuilder = originalRequest.newBuilder()
//        if (token != null) {
//            if (token.isNotEmpty()) {
//                newRequestBuilder.header("Authorization", "Bearer $token")
//            }
//        }

        Log.d("token from shared preference ", ""+token)
        if(token.isNullOrEmpty()){
            return chain.proceed(originalRequest)
        }
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)

//        if (cookie.isNotEmpty()) {
//            newRequestBuilder.header("Cookie", cookie)
//        }


    }

//    private fun getAuthToken(): String {
//        val prefs = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
//        return prefs.getString("AUTH_TOKEN", "") ?: ""
//    }

    @SuppressLint("SuspiciousIndentation")
    public fun saveToken(token: String) {
        //val prefs = context.getSharedPreferences("CookiePrefs", Context.MODE_PRIVATE)
        val sharedPreference = context.getSharedPreferences("AuthToken" , Context.MODE_PRIVATE)

        val editor = sharedPreference.edit()
            editor.putString("auth_token", token)

        editor.apply()
    }
    val sharedPrefStoreId = context.getSharedPreferences("ColdStore_ID", Context.MODE_PRIVATE)

    public fun saveStoreId(id:String){

        val editor = sharedPrefStoreId.edit()
        editor.putString("cold_store_token" , id)
        editor.apply()
    }

    public fun getStore_id(key:String):String{
       val id = if( sharedPrefStoreId.getString("cold_store_token", "DEFAULT") != null) {
           return sharedPrefStoreId.getString("cold_store_token", "DEFAULT")!!
       }
        else{
           return  "Not found the store id"
        }

    }

    public fun clearStore_id(){
        sharedPrefStoreId.edit().clear().apply()
    }

    val sharedPrefSelectedStock = context.getSharedPreferences("VoucherNumber" , Context.MODE_PRIVATE)
    public fun saveSelectedOrder(myValues: List<String> , myIndexes:List<String>){
        val concatenatedValues = myValues.joinToString(",") // Join values using a comma
        val concatenatedValues2 = myIndexes.joinToString(",") // Join values using a comma
        Log.d("XXXXxxxxxx" , concatenatedValues)

        sharedPrefSelectedStock.edit().putString("voucher_number" ,concatenatedValues ).apply()
        sharedPrefSelectedStock.edit().putString("indexSelected" , concatenatedValues2).apply()
    }

    public fun getSelectedOrder():List<String>{
        if(sharedPrefSelectedStock.getString("voucher_number" , "DEFAULT") != null){
            val concatenatedValues = sharedPrefSelectedStock.getString("voucher_number", "DEFAULT")
            val myValues = concatenatedValues?.split(",") ?: listOf()
            Log.d("XXXXSelectedStock" , myValues.toString())
            return myValues
        }
        else {
            return listOf()
        }
    }

    public fun getSelectedOrderIndex():List<String>{
        if(sharedPrefSelectedStock.getString("indexSelected" , "DEFAULT") != null){
            val concatenatedValues = sharedPrefSelectedStock.getString("indexSelected", "DEFAULT")

            val myValues = concatenatedValues?.split(",") ?: listOf()
            Log.d("XXXXSelectedIndex" , myValues.toString())

            return myValues
        }
        else {
            return listOf()
        }
    }


    public fun clearSelectedCell(){
        sharedPrefSelectedStock.edit().clear().apply()
    }
    public fun saveSelectedCellData(selectedCellData: List<SelectedCellData>) {
        val sharedPref = context.getSharedPreferences("SelectedCellData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        //
        // Convert SelectedCellData to JSON string
        val gson = Gson()
        val json = gson.toJson(selectedCellData)

        // Save JSON string in SharedPreferences
        editor.putString("selected_cell_data", json)
        //Log.d("sasasasasas" , json)
        editor.apply()
    }

    fun getSelectedCellData(): List<SelectedCellData> {
        val sharedPref = context.getSharedPreferences("SelectedCellData", Context.MODE_PRIVATE)
        val json = sharedPref.getString("selected_cell_data", null)

        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<SelectedCellData>>() {}.type
            return gson.fromJson(json, type)
        }

        // Return an empty list if no data is found
        return emptyList()
    }


    fun clearSelectedCellData() {
        val sharedPref = context.getSharedPreferences("SelectedCellData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Remove the selected cell data key
        editor.remove("selected_cell_data")
        editor.apply()
    }
}




@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {



        @Provides
        @Singleton
        fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
            return AuthManager(context)
        }

    //cookie saving
    @Singleton
    @Provides
    fun provideAuthInterceptor(@ApplicationContext context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }



    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder().baseUrl("https://coldop-backend-1gn6.onrender.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NmUxZjIyZDc4MmJiZDY3ZDM0NDY4MDUiLCJpYXQiOjE3MjYyMTkwMjQsImV4cCI6MTcyODgxMTAyNH0.5w02NCDoXrKJVR9fEHkcLQmpV_NZmoPAMIssCcyXGRQ

    @Singleton
    @Provides
    fun registerUser(retrofit: Retrofit):ColdOpApi{
        return retrofit.create(ColdOpApi::class.java)
    }



}

//920 , 35 mins
// galti save selected order mein ho rhi
//clean the list of selected
// outgoing data set on the final call just like incoming
// bs row is select ho rha and uss row k saare columns aa rhe hai
// outgoing data set
// dynamic number of textfields
// make cards with filtering

//check that quant to remove is less than currr on clicking Proceed and show on ui,
// solve people wala incoming
// solve bhar wala outgoing

//make accNUm a optional arguemnt or any other solution