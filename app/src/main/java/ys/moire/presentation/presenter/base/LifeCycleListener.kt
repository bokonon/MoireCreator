package ys.moire.presentation.presenter.base

/**
 * LifeCycleListener.
 */

internal interface LifeCycleListener {

    fun onCreate()

    fun onResume()

    fun onPause()

    fun onDestroy()
}
