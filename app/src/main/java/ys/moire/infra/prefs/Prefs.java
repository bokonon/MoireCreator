package ys.moire.infra.prefs;

/**
 * Prefs.
 */

public interface Prefs {

    void put(final String key, final String value);

    void put(final String key, final int value);

    String get(final String key, final String defValue);

    int get(final String key, final int defValue);
}
