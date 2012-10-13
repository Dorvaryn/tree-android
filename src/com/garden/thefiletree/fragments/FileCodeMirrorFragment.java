package com.garden.thefiletree.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.garden.thefiletree.R;
import com.garden.thefiletree.TheFileTreeApp;
import com.garden.thefiletree.api.JavascriptFileInterface;
import com.garden.thefiletree.api.TreeAPI;
import com.garden.thefiletree.api.TreeTextFile;
import com.garden.thefiletree.callbacks.FragmentReload;
import com.garden.thefiletree.callbacks.TreeTaskCallbacks;
import com.garden.thefiletree.fragments.FileListFragment.Callbacks;
import com.garden.thefiletree.task.RetrieveFile;

public class FileCodeMirrorFragment extends Fragment implements
		TreeTaskCallbacks, FragmentReload {

	private WebView web;

	private RetrieveFile treeGetDirTask;
	private Callbacks mCallbacks = sDummyCallbacks;
	
	JavascriptFileInterface fileInterface;

	public FileCodeMirrorFragment() {
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}

		@Override
		public void setFragment(Fragment fragment) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_codemirror,
				container, false);
		web = (WebView) rootView.findViewById(R.id.web);
		if (TheFileTreeApp.getCurrentFilePath() != null
				&& treeGetDirTask == null) {
			treeGetDirTask = new RetrieveFile(this);
			treeGetDirTask
					.execute(TheFileTreeApp.getCurrentFilePath(), "start");
		} else {
			// tvDetail.setText("An error Has occured");
		}
		return rootView;
	}

	@Override
	public void onTaskCompleted() {
		TreeAPI api = treeGetDirTask.getAPICompleted();
		if (api.getFile().isText()) {
			fileInterface = new JavascriptFileInterface(api.getTextFile());
			web.getSettings().setJavaScriptEnabled(true);
			web.addJavascriptInterface(fileInterface,"File");
			web.loadUrl("file:///android_asset/pencil.html");
		} else {
			// tvDetail.setText("Sorry Binary Files not yet Supported");
		}
		treeGetDirTask = null;
	}

	@Override
	public void reloadFile() {
		treeGetDirTask = new RetrieveFile(this);
		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(), "start");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if ((activity instanceof Callbacks)) {
			mCallbacks = (Callbacks) activity;
			mCallbacks.setFragment(this);
		}
	}

	@Override
	public void reset() {
		TheFileTreeApp.setCurrentFilePath("");
	}


}
