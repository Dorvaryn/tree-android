package com.garden.thefiletree.task;

import android.os.AsyncTask;

import com.garden.thefiletree.api.TreeAPI;

public class PrefetchFiles extends AsyncTask<String, Void, Void>{

	private TreeAPI api;
	
	public PrefetchFiles(TreeAPI api) {
		this.api = api;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		if(params[0] != null){
			api.treeGetFile(params[0]);	
		}
		api.treeGetFile("");
      	return null;
	}
}