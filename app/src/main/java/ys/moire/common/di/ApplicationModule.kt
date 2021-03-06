package ys.moire.common.di

import android.content.Context

import dagger.Module
import dagger.Provides
import ys.moire.MoireApplication
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.domain.usecase.SaveMoireUseCase
import ys.moire.infra.prefs.Prefs
import ys.moire.infra.prefs.PrefsImpl
import ys.moire.infra.storage.PrefsDao
import ys.moire.presentation.presenter.main.MainPresenter
import ys.moire.presentation.presenter.preferences.PreferencesPresenter

/**
 * dagger application module.
 */
@Module
class ApplicationModule(private val application: MoireApplication) {

    @Provides
    internal fun provideContext(): Context = application.applicationContext

    @Provides
    internal fun providePrefs(context: Context): Prefs = PrefsImpl(context)

    @Provides
    internal fun providePrefsDao(prefs: Prefs): PrefsDao = PrefsDao(prefs)

    @Provides
    internal fun provideLoadMoireUseCase(prefsDao: PrefsDao): LoadMoireUseCase =
            LoadMoireUseCase(prefsDao)

    @Provides
    internal fun provideSaveMoireUseCase(prefsDao: PrefsDao): SaveMoireUseCase =
            SaveMoireUseCase(prefsDao)

    @Provides
    internal fun provideMainPresenter(context: Context,
                                      loadMoireUseCase: LoadMoireUseCase): MainPresenter =
            MainPresenter(context, loadMoireUseCase)

    @Provides
    internal fun providePreferencesPresenter(loadMoireUseCase: LoadMoireUseCase,
                                             saveMoireUseCase: SaveMoireUseCase): PreferencesPresenter =
            PreferencesPresenter(loadMoireUseCase, saveMoireUseCase)

}
