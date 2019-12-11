package com.crysberry

import android.content.Context
import android.content.Intent
import com.crysberry.ui.lounch.SplashActivity
import com.crysberry.ui.main.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class SplashScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

}