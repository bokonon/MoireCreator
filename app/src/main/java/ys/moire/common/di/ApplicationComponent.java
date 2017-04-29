package ys.moire.common.di;

import dagger.Component;
import ys.moire.presentation.ui.main.MainActivity;
import ys.moire.presentation.ui.preferences.PreferencesActivity;

/**
 * dagger application component.
 */
@Component (modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(PreferencesActivity preferencesActivity);
}
