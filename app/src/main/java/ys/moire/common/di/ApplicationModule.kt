package ys.moire.common.di

import android.content.Context

import dagger.Module
import dagger.Provides
import ys.moire.MoireApplication
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.domain.usecase.SaveMoireUseCase
import ys.moire.infra.prefs.Prefs
import ys.moire.infra.prefs.PrefsImpl
import ys.moire.presentation.presenter.main.MainPresenter
import ys.moire.presentation.presenter.preferences.PreferencesPresenter

/**
 * dagger application module.
 */
@Module
class ApplicationModule(private val application: MoireApplication) {

    @Provides
    internal fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    internal fun providePrefs(context: Context): Prefs {
        return PrefsImpl(context)
    }

    @Provides
    internal fun provideLoadMoireUseCase(prefs: Prefs): LoadMoireUseCase {
        return LoadMoireUseCase(prefs)
    }

    @Provides
    internal fun provideSaveMoireUseCase(prefs: Prefs): SaveMoireUseCase {
        return SaveMoireUseCase(prefs)
    }

    @Provides
    internal fun provideMainPresenter(context: Context,
                                      loadMoireUseCase: LoadMoireUseCase): MainPresenter {
        return MainPresenter(context, loadMoireUseCase)
    }

    @Provides
    internal fun providePreferencesPresenter(loadMoireUseCase: LoadMoireUseCase,
                                             saveMoireUseCase: SaveMoireUseCase): PreferencesPresenter {
        return PreferencesPresenter(loadMoireUseCase, saveMoireUseCase)
    }

}
