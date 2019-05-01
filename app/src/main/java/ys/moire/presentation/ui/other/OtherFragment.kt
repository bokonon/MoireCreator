package ys.moire.presentation.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import ys.moire.R
import ys.moire.presentation.ui.base.BaseFragment

/**
 * OtherFragment.
 */
class OtherFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_other, container, false)

        val listView = view.findViewById<ListView>(R.id.listView)
        listView.addHeaderView(View(activity), null, false)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            var url = ""
            var contentType = ""
            when (i) {
            // header
                0 -> {}
                1 -> {
                    url = "http://bokonon.html.xdomain.jp/privacy_policy.html"
                    contentType = "privacy policy"
                }
                2 -> {
                    url = "file:///android_asset/license.html"
                    contentType = "license"
                }
                3 -> {
                    url = "file:///android_asset/version.html"
                    contentType = "version"
                }
                else -> {}
            }// NOP
            moveToContents(url)
            postLogEvent(contentType)
        }

        // create adapter
        val items = resources.getStringArray(R.array.other_items)
        val adapter = ArrayAdapter(
                activity, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        return view
    }

    private fun moveToContents(url: String) {
        activity.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.container, OtherContentsFragment.newInstance(url))
                .commit()
    }
}
