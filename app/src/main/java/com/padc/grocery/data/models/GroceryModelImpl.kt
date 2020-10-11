package com.padc.grocery.data.models

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.networks.FirebaseApi
import com.padc.grocery.networks.RealTimeCloudFireStoreApiImpl
import com.padc.grocery.networks.RealTimeDatabaseFirebaseApiImpl

object GroceryModelImpl : GroceryModel {
   // override var mFirebaseApi: FirebaseApi = RealTimeDatabaseFirebaseApiImpl
    override var mFirebaseApi: FirebaseApi = RealTimeCloudFireStoreApiImpl


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
}