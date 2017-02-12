package ys.moire.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ys.moire.R;

/**
 * OtherContentsFragment.
 */

public class OtherContentsFragment extends Fragment {

    private static final String BUNDLE_KEY_CONTENT_URL = "contentUrl";

    public static OtherContentsFragment newInstance(final String url) {
        OtherContentsFragment f = new OtherContentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_CONTENT_URL, url);
        f.setArguments(bundle);
        return f;
    }

    public OtherContentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_contents, container, false);

        String url = getArguments().getString(BUNDLE_KEY_CONTENT_URL);
        WebView webView = (WebView)view.findViewById(R.id.web_view);
        webView.loadUrl(url);

        return view;
    }

}
