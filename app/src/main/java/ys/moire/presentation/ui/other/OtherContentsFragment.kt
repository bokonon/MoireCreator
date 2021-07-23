package ys.moire.presentation.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import ys.moire.R
import ys.moire.presentation.ui.base.BaseFragment

/**
 * OtherContentsFragment.
 */
class OtherContentsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_other_contents, container, false)

        val url = arguments?.getString(BUNDLE_KEY_CONTENT_URL)
        val webView = view.findViewById<WebView>(R.id.web_view)
        url?.let {
            webView.loadUrl(url)
        }

        return view
    }

    companion object {

        private const val BUNDLE_KEY_CONTENT_URL = "contentUrl"

        fun newInstance(url: String): OtherContentsFragment {
            val f = OtherContentsFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_CONTENT_URL, url)
            f.arguments = bundle
            return f
        }
    }

}
