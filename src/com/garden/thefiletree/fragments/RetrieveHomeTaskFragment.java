package com.garden.thefiletree.fragments;

import android.app.Activity;
import android.support.v4.app.ListFragment;

import com.garden.thefiletree.TheFileTreeApp;
import com.garden.thefiletree.api.TreeFile;
import com.garden.thefiletree.callbacks.TreeTaskCallbacks;
import com.garden.thefiletree.task.RetrieveFile;

public class RetrieveHomeTaskFragment extends ListFragment implements TreeTaskCallbacks{

    private CallbacksHome mCallbacks = sDummyCallbacks;
    private RetrieveFile treeGetDirTask;

    public interface CallbacksHome {
		void onLoadOver(TreeFile file);
    }

    private static CallbacksHome sDummyCallbacks = new CallbacksHome() {

		@Override
		public void onLoadOver(TreeFile file) {		
		}
    };
    
    public void onTaskCompleted() {
    	mCallbacks.onLoadOver(TheFileTreeApp.getApi().getFile());
    	treeGetDirTask = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof CallbacksHome)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (CallbacksHome) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    public void treeGetFile(String path) {
        if(treeGetDirTask == null){
        	treeGetDirTask = new RetrieveFile(this);
        	treeGetDirTask.execute(path,"nav");
        }
    }
    
}
