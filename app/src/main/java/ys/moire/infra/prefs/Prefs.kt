package ys.moire.infra.prefs

/**
 * Prefs.
 */

interface Prefs {

    fun put(key: String, value: String)

    fun put(key: String, value: Int)

    operator fun get(key: String, defValue: String): String?

    operator fun get(key: String, defValue: Int): Int
}
