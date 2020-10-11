package com.padc.grocery.mvp.presenters

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.delegates.GroceryViewItemActionDelegate
import com.padc.grocery.mvp.views.MainView

interface MainPresenter : BasePresenter<MainView>, GroceryViewItemActionDelegate {
   // fun onTapAddGrocery(name: String, description: String, amount: Int,bitmap: Bitmap)
    fun onTapAddGrocery(groceryVO: GroceryVO,bitmap: Bitmap)

    fun onPhotoTaken(bitmap: Bitmap)

    fun onTabImageView(tapFrom: Int)
}
