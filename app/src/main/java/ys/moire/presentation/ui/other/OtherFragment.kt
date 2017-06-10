package ys.moire.presentation.ui.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import ys.moire.R

/**
 * OtherFragment.
 */
class OtherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_other, container, false)

        val listView = view.findViewById(R.id.listView) as ListView
        listView.addHeaderView(View(activity), null, false)
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            var url = ""
            when (i) {
            // header
                0 -> {
                }
                1 -> url = "http://bokonon.html.xdomain.jp/etc/privacy_policy.html"
                2 -> url = "file:///android_asset/license.html"
                3 -> url = "file:///android_asset/version.html"
                else -> {
                }
            }// NOP
            moveToContents(url)
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
