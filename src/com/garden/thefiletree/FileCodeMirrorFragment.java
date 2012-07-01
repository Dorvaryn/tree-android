package com.garden.thefiletree;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class FileCodeMirrorFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    String mText = "default";
    
    String mItem;

    public FileCodeMirrorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_codemirror, container, false);

		final WebView web = (WebView) rootView.findViewById(R.id.web);
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl("file:///android_asset/cmold/webkit/home.html");
		web.addJavascriptInterface(new JavascriptInterface(getActivity()), "AndroidCode");
        return rootView;
    }
    
    public class JavascriptInterface {

		private Context mCtx;

		JavascriptInterface(Context ctx) {

			mCtx = ctx;
		}

		public void toastIt(String text) {

			Toast.makeText(mCtx, text, Toast.LENGTH_LONG).show();
		}
		
		public void setText(String text) {
			
			mText = text;
		}
	}
}
