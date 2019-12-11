package com.crysberry.ui.lounch

import com.crysberry.Screens
import com.crysberry.ui.bases.BaseActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class SplashActivity : BaseActivity()  {



    private val nav = SupportAppNavigator(this, -1)

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(nav)
        router.navigateTo(Screens.MainScreen())
    }


}
