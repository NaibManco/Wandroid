package com.naib.wandroid

import android.app.Application
import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.persistence.KV

class WanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KV.initialize(this);
    }
}