package ys.moire.presentation.ui.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import ys.moire.R

/**
 * OtherContentsFragment.
 */
class OtherContentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_other_contents, container, false)

        val url = arguments.getString(BUNDLE_KEY_CONTENT_URL)
        val webView = view.findViewById(R.id.web_view) as WebView
        webView.loadUrl(url)

        return view
    }

    companion object {

        private val BUNDLE_KEY_CONTENT_URL = "contentUrl"

        fun newInstance(url: String): OtherContentsFragment {
            val f = OtherContentsFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_CONTENT_URL, url)
            f.arguments = bundle
            return f
        }
    }

}
