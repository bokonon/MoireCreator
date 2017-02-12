package ys.moire.ui.base;

/**
 * Presenter.
 */

public interface Presenter<V extends MvpView> {

    void onCreate(V mvpView);

    void onResume();

    void onPause();

    void onDestroy();
}
