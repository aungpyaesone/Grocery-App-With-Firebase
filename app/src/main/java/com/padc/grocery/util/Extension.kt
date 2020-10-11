package com.padc.grocery.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.padc.grocery.R
import java.io.ByteArrayInputStream


fun String.convertToBitMap():Bitmap?{
    try{
        val byte = Base64.decode(this,Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(byte))
        return bitmap
    }catch (e: Exception){
        e.message
        return null
    }
}

fun ImageView.load(uri: Uri){
    Glide.with(context)
        .asBitmap()
        .load(uri)
        .placeholder(R.drawable.ic_baseline_image_24)
        .into(this)
}

