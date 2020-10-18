package com.padc.grocery.data.models

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.networks.FirebaseApi
import com.padc.grocery.networks.remoteconfig.FirebaseRemoteConfigManager

interface GroceryModel {

    var mFirebaseApi : FirebaseApi

    var mfirebaseRemoteConfigManager : FirebaseRemoteConfigManager

    fun getGroceries(onSuccess: (List<GroceryVO>) -> Unit, onFaiure: (String) -> Unit)

    fun addGrocery(name: String ,description : String, amount: Int,image:String)

    fun removeGrocery(name: String)

    fun editGrocery(name: String, description: String, amount: Int,image: String)

    fun uploadImageAndUpdateGrocery(grocery : GroceryVO, image : Bitmap)

    fun setupRemoteConfigWithDefaultValues()

    fun fetchRemoteConfig()

    fun getAppNameFromRemoteConfig():String

    fun getViewType() : Int

}