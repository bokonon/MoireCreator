package ys.moire;

import android.app.Application;

import lombok.Getter;
import ys.moire.common.di.ApplicationComponent;
import ys.moire.common.di.ApplicationModule;
import ys.moire.common.di.DaggerApplicationComponent;

/**
 * Application class.
 */

public class MoireApplication extends Application {

    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

}
