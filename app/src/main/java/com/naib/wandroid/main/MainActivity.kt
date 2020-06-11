package com.naib.wandroid.main

import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreateContentView(root: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_main, root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val disposable: Disposable = fromCallable<Drawable> {
//            val size: Int = resources.getDimensionPixelSize(R.dimen.dp_32)
//            Glide.with(this)
//                .asDrawable()
//                .circleCrop()
//                .load("https://lh3.googleusercontent.com/proxy/_iqDKWlQUJX24_iaOWSyL2EBJoKlrnzZrwsMQFrPP_EcnsaC-zKdU0cN2jgqHtpHXAVSyWujrnJKPGDdcbGHlwGpgQ")
//                .submit(size, size)
//                .get()
//        }
//            .compose(bindLifecycleAndSchedule())
//            .subscribe {
//                toolbar.navigationIcon = it
//            }
        toolbar.navigationIcon = getDrawable(R.drawable.ic_android_black_24dp)
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
    }
}
