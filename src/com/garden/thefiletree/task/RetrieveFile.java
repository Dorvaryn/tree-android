package com.garden.thefiletree.task;

import android.os.AsyncTask;
import android.util.Log;

import com.garden.thefiletree.TheFileTreeApp;
import com.garden.thefiletree.TreeAPI;
import com.garden.thefiletree.callbacks.TreeTaskCallbacks;

public class RetrieveFile extends AsyncTask<String, Void, Void>{
	
	private TreeTaskCallbacks fragment;
	private TreeAPI api;
	
	public RetrieveFile(TreeTaskCallbacks fragment) {
		try {
			this.fragment = (TreeTaskCallbacks) fragment;
	    }
	    catch (ClassCastException e) {
	        throw new ClassCastException(fragment.toString() + " must implement " + TreeTaskCallbacks.class.getName());
	    }
		this.api = TheFileTreeApp.getApi();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		Log.d("tree", params[0]);
		TheFileTreeApp.getApi().treeGetFile(params[0]);
		if(params[1].equalsIgnoreCase("nav")){
			TheFileTreeApp.setCurrentFilePath(api.getFile().getPath());
		}
      	return null;
	}
	
	public TreeAPI getAPICompleted() {
		return api;
	}

	@Override
	protected void onPostExecute(Void unused) {
		Log.d("tree", "File Retrived");
		fragment.onTaskCompleted();
	}
}