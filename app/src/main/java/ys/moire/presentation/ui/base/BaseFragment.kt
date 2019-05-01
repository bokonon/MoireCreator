package ys.moire.presentation.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics

open class BaseFragment: Fragment() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
    }

    public fun postLogEvent(contentType: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}