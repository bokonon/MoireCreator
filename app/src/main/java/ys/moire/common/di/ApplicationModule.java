package ys.moire.common.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ys.moire.MoireApplication;
import ys.moire.domain.usecase.LoadMoireUseCase;
import ys.moire.domain.usecase.SaveMoireUseCase;
import ys.moire.infra.prefs.Prefs;
import ys.moire.infra.prefs.PrefsImpl;
import ys.moire.presentation.presenter.main.MainPresenter;
import ys.moire.presentation.presenter.preferences.PreferencesPresenter;

/**
 * dagger application module.
 */
@Module
public class ApplicationModule {

    private final MoireApplication application;

    public ApplicationModule(MoireApplication application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    Prefs providePrefs(final Context context) {
        return new PrefsImpl(context);
    }

    @Provides
    LoadMoireUseCase provideLoadMoireUseCase(final Prefs prefs) {
        return new LoadMoireUseCase(prefs);
    }

    @Provides
    SaveMoireUseCase provideSaveMoireUseCase(final Prefs prefs) {
        return new SaveMoireUseCase(prefs);
    }

    @Provides
    MainPresenter provideMainPresenter(final Context context,
                                       final LoadMoireUseCase loadMoireUseCase) {
        return new MainPresenter(context, loadMoireUseCase);
    }

    @Provides
    PreferencesPresenter providePreferencesPresenter(final LoadMoireUseCase loadMoireUseCase,
                                                     final SaveMoireUseCase saveMoireUseCase) {
        return new PreferencesPresenter(loadMoireUseCase, saveMoireUseCase);
    }

}
