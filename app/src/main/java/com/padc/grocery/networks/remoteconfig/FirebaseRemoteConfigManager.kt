package com.padc.grocery.networks.remoteconfig

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object FirebaseRemoteConfigManager {
    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSetting = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSetting)
    }

    fun setupRemoteConfigWithDefaultValues(){
        val defaultValues: Map<String,Any> = hashMapOf(
            "mainScreenAppBarTitle" to "Grocery-App",
            "viewType" to 0
        )
        remoteConfig.setDefaultsAsync(defaultValues)
    }

    fun fetchRemoteConfig(){
        remoteConfig.fetch()
            .addOnCompleteListener {task ->
                if(task.isSuccessful)
                {
                    Log.d("Firebase","Firebase remote config fetch success")
                    remoteConfig.activate().addOnCompleteListener{
                        Log.d("Firebase","Firebase RemoteConfig activated")
                    }
                }else{
                    Log.d("Firebase","firebase remote config fetch failed")
                }
            }
    }

    fun getToolbarName() : String {
        return remoteConfig.getValue("mainScreenAppBarTitle").asString()
    }

    fun getViewType() : Int{
        return remoteConfig.getValue("viewType").asLong().toInt()
    }
}