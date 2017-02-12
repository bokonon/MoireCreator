package ys.moire.ui.view.other;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ys.moire.R;

/**
 * OtherFragment.
 */

public class OtherFragment extends Fragment {

    public OtherFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);

        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.addHeaderView(new View(getActivity()), null, false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = "";
                switch (i) {
                    // header
                    case 0:
                        // NOP
                        break;
                    case 1:
                        url = "http://bokonon.html.xdomain.jp/etc/privacy_policy.html";
                        break;
                    case 2:
                        url = "file:///android_asset/license.html";
                        break;
                    case 3:
                        url = "file:///android_asset/version.html";
                        break;
                    default :
                        break;
                }
                moveToContents(url);
            }
        });

        // create adapter
        String[] items = getResources().getStringArray(R.array.other_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        return view;
    }

    private void moveToContents(@NonNull String url) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.container, OtherContentsFragment.newInstance(url))
                .commit();
    }
}
