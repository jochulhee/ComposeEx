package com.one4u.composeex

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}