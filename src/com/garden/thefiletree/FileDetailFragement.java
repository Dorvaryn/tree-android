package com.garden.thefiletree;

import com.garden.thefiletree.callbacks.TreeTaskCallbacks;
import com.garden.thefiletree.task.RetrieveFile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FileDetailFragement extends Fragment implements TreeTaskCallbacks{

    public static final String ARG_ITEM_ID = "item_id";

    private RetrieveFile treeGetDirTask;
    
    private String mItem;
    
    private TextView rootView;

    public FileDetailFragement() {
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
    	if(mItem != null && treeGetDirTask == null){
    		treeGetDirTask = new RetrieveFile(this);
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(),"start");
    	}
        rootView = (TextView) inflater.inflate(R.layout.fragment_file_detail, container, false);
        return rootView;
    }

	@Override
	public void onTaskCompleted() {
		TreeAPI api = treeGetDirTask.getAPICompleted();
		if(api.isText()){
			((TextView) rootView.findViewById(R.id.file_detail)).setText(api.getTextFile().getContent());
		}else {
			((TextView) rootView.findViewById(R.id.file_detail)).setText("Sorry Binary Files not yet Supported");
		}
		treeGetDirTask = null;
	}
}
