package com.naib.wandroid

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.persistence.KV

class WanApplication : Application(), Application.ActivityLifecycleCallbacks {
    private var activities: MutableList<Activity> = mutableListOf()
    lateinit var topActivity: Activity

    companion object {
        var instance: WanApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KV.initialize(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
        activities.add(activity)
        topActivity = activity
    }
}