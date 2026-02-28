package com.hieu10.vendoza

import android.app.Application
import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.APIClient

class VendozaApp : Application() {
    lateinit var tokenManager: TokenManager
        private set

    override fun onCreate() {
        super.onCreate()
        tokenManager = TokenManager(this)
        APIClient.init(tokenManager)
    }
}