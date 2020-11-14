package com.padc.grocery.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MessageService : FirebaseMessagingService(){
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("newToken",p0)
    }
}