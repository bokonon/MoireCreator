package ys.moire.common.di

import dagger.Component
import ys.moire.presentation.ui.main.MainActivity
import ys.moire.presentation.ui.preferences.PreferencesActivity

/**
 * dagger application component.
 */
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(preferencesActivity: PreferencesActivity)
}
