package com.padc.grocery.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity
import java.io.ByteArrayOutputStream
import java.lang.Exception

fun Uri.convertUritoBitmap(context: Context): Bitmap?{
    try {
        if (Build.VERSION.SDK_INT >= 29) {
            val source: ImageDecoder.Source =
                ImageDecoder.createSource(context.contentResolver, this)
            val bitmap = ImageDecoder.decodeBitmap(source)
            return bitmap
            //  mPresenter.onTapAddGrocery(groceryVO = groceryVO, bitmap = bitmap)
        } else {
            val bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                this
            )
            return bitmap
            //  mPresenter.onTapAddGrocery(groceryVO,bitmap)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}