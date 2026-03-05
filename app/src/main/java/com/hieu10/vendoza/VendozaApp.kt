package com.hieu10.vendoza

import android.app.Application
import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.ApiClient

class VendozaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val tokenManager = TokenManager(applicationContext)
        ApiClient.initialize(tokenManager)
    }
}