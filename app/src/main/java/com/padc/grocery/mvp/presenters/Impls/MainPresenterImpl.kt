package com.padc.grocery.mvp.presenters.Impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.models.GroceryModelImpl
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.mvp.presenters.AbstractBasePresenter
import com.padc.grocery.mvp.presenters.MainPresenter
import com.padc.grocery.mvp.views.MainView

class MainPresenterImpl : MainPresenter,AbstractBasePresenter<MainView>() {
    private val mGroceryModel = GroceryModelImpl
    private var mChosenGroceryForFileUpload : GroceryVO? = null

    override fun onTapAddGrocery(groceryVO: GroceryVO, bitmap: Bitmap) {
       // mChosenGroceryForFileUpload = groceryVO
        mGroceryModel.uploadImageAndUpdateGrocery(groceryVO,bitmap)
    }


    override fun onPhotoTaken(bitmap: Bitmap){
        mChosenGroceryForFileUpload?.let {
            mGroceryModel.uploadImageAndUpdateGrocery(it,bitmap)
        }
    }

    override fun onTabImageView() {
        mView.openGallery()
    }

    override fun onUiReady(owner: LifecycleOwner) {
        mGroceryModel.getGroceries(
            onSuccess = {
                mView.showGroceryData(it)
            },
            onFaiure = {
                mView.showErrorMessage(it)
            }
        )
    }

    override fun onTapDeleteGrocery(name: String) {
        mGroceryModel.removeGrocery(name)
    }

    override fun onTapEditGrocery(name: String, description: String, amount: Int,image: String) {
      mView.showGroceryDialog(name,description,amount.toString(),image)
    }

    override fun onTapFileUpload(grocery: GroceryVO,tapFrom: Int) {
        mChosenGroceryForFileUpload = grocery
        mView.openGallery()
    }
}