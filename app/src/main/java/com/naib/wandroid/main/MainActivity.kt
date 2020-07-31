package com.naib.wandroid.main

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.TextUtils
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.BaseFragment
import com.naib.wandroid.main.architecture.ArchitectureFragment
import com.naib.wandroid.main.home.HomeFragment
import com.naib.wandroid.main.project.ProjectFragment
import com.naib.wandroid.main.question.QuestionFragment
import com.naib.wandroid.main.square.SquareFragment
import com.naib.wandroid.user.LoginActivity
import com.naib.wandroid.user.ProfileActivity
import com.naib.wandroid.user.data.UserInfoManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    private var fragments = mutableListOf<BaseFragment>(
        HomeFragment(),
        QuestionFragment(),
        SquareFragment(),
        ProjectFragment(),
        ArchitectureFragment()
    )

    private var currentTab = 0

    override fun onCreateContentView(root: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_main, root)

        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragments[0])
                .commit()
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        navView.selectedItemId = R.id.navigation_home
        navView.setOnNavigationItemSelectedListener {
            if (it.order == currentTab) {
                return@setOnNavigationItemSelectedListener true
            }
            toolbar.title = it.title
            val fragment = fragments[it.order]
            if (!supportFragmentManager.fragments.contains(fragment)) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .hide(fragments[currentTab])
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .hide(fragments[currentTab])
                    .commit()
            }
            currentTab = it.order
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val userInfo = UserInfoManager.getUserInfo()
                val size: Int = resources.getDimensionPixelSize(R.dimen.dp_32)
                if (userInfo == null || TextUtils.isEmpty(userInfo.icon)) {
                    Glide.with(this@MainActivity)
                        .asDrawable()
                        .circleCrop()
                        .load(R.drawable.ic_profile)
                        .submit(size, size)
                        .get()
                } else {
                    Glide.with(this@MainActivity)
                        .asDrawable()
                        .circleCrop()
                        .load(userInfo.icon)
                        .submit(size, size)
                        .get()
                }
            }.apply {
                toolbar.navigationIcon = this
                toolbar.navigationIcon?.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
            }
        }

        toolbar.setNavigationOnClickListener {
            if (UserInfoManager.getUserInfo() == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.title = getString(R.string.title_home)
    }
}
