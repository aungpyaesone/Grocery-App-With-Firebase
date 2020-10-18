package com.padc.grocery.mvp.presenters.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.models.GroceryModelImpl
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.mvp.presenters.AbstractBasePresenter
import com.padc.grocery.mvp.presenters.MainPresenter
import com.padc.grocery.mvp.views.MainView
import com.padc.grocery.networks.auth.AuthManager
import com.padc.grocery.networks.auth.AuthenticationManagerImpl

class MainPresenterImpl : MainPresenter,AbstractBasePresenter<MainView>() {
    private val mGroceryModel = GroceryModelImpl
    private var mChosenGroceryForFileUpload : GroceryVO? = null
    private val mAuthModel: AuthManager = AuthenticationManagerImpl

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

    private fun getUserNameAndShowInView() {
       val userName = mAuthModel.getUserName()
       mView.showUserName(userName)
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
        getUserNameAndShowInView()
        mView.displayToolbarTitle(mGroceryModel.getAppNameFromRemoteConfig())
        mView.displayViewType(mGroceryModel.getViewType())

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