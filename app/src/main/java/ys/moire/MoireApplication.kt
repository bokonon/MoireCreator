package ys.moire

import android.support.multidex.MultiDexApplication
import ys.moire.common.di.ApplicationComponent
import ys.moire.common.di.ApplicationModule
import ys.moire.common.di.DaggerApplicationComponent

/**
 * Application class.
 */

class MoireApplication : MultiDexApplication() {

    companion object {
        @JvmStatic lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

    }

}
