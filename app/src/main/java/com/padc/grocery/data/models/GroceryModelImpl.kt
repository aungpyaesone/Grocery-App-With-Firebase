package com.padc.grocery.data.models

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.networks.FirebaseApi
import com.padc.grocery.networks.RealTimeCloudFireStoreApiImpl
import com.padc.grocery.networks.RealTimeDatabaseFirebaseApiImpl
import com.padc.grocery.networks.auth.AuthManager
import com.padc.grocery.networks.auth.AuthenticationManagerImpl
import com.padc.grocery.networks.remoteconfig.FirebaseRemoteConfigManager

object GroceryModelImpl : GroceryModel {
   // override var mFirebaseApi: FirebaseApi = RealTimeDatabaseFirebaseApiImpl
    override var mFirebaseApi: FirebaseApi = RealTimeCloudFireStoreApiImpl

    override var mfirebaseRemoteConfigManager: FirebaseRemoteConfigManager = FirebaseRemoteConfigManager

    override fun getGroceries(onSuccess: (List<GroceryVO>) -> Unit, onFaiure: (String) -> Unit) {
        mFirebaseApi.getGroceries(onSuccess,onFaiure)
    }

    override fun addGrocery(name: String, description: String, amount: Int,image : String) {
        mFirebaseApi.addGrocery(name,description,amount,image)
    }

    override fun removeGrocery(name: String) {
        mFirebaseApi.deleteGrocery(name)
    }

    override fun editGrocery(name: String, description: String, amount: Int,image: String) {
       mFirebaseApi.addGrocery(name,description,amount,image)
    }

    override fun uploadImageAndUpdateGrocery(grocery: GroceryVO, image: Bitmap) {
        mFirebaseApi.uploadImageAndEditGrocery(image,grocery)
    }

    override fun setupRemoteConfigWithDefaultValues() {
        mfirebaseRemoteConfigManager.setupRemoteConfigWithDefaultValues()
    }

    override fun fetchRemoteConfig() {
        mfirebaseRemoteConfigManager.fetchRemoteConfig()
    }

    override fun getAppNameFromRemoteConfig() :String {
        return mfirebaseRemoteConfigManager.getToolbarName()
    }

    override fun getViewType(): Int {
        return mfirebaseRemoteConfigManager.getViewType()
    }

}