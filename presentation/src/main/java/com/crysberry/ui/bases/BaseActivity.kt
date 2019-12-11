package com.crysberry.ui.bases

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.crysberry.AppEvents
import com.crysberry.R
import com.cryssberry.core.internal.LocalStorage
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.configuration
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject lateinit var storage: LocalStorage
    @Inject lateinit var events: AppEvents
    @Inject lateinit var router: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder

    protected open val layoutId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        adjustFontScale(Configuration(configuration))
        super.onCreate(savedInstanceState)

        if (layoutId != 0) {
            setContentView(layoutId)
        }
    }

    private fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale != 1f) {
            configuration.fontScale = 1f

            val metrics = resources.displayMetrics
            windowManager.defaultDisplay.getMetrics(metrics)

            metrics.scaledDensity = configuration.fontScale * metrics.density
            baseContext.resources.updateConfiguration(configuration, metrics)
        }
    }

    protected open fun currentFragment(): Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)

    protected inline fun <reified T> currentFragmentParametrized(): T = currentFragment() as T

    protected inline fun <reified T> currentFragment(action: T.() -> Unit) {
        (currentFragment() as? T)?.let(action)
    }




}





