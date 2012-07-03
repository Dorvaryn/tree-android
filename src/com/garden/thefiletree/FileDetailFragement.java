package com.garden.thefiletree;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.garden.thefiletree.FileListFragment.Callbacks;
import com.garden.thefiletree.callbacks.FragmentReload;
import com.garden.thefiletree.callbacks.TreeTaskCallbacks;
import com.garden.thefiletree.task.RetrieveFile;

public class FileDetailFragement extends Fragment implements TreeTaskCallbacks, FragmentReload{

    public static final String ARG_ITEM_ID = "item_id";

    private RetrieveFile treeGetDirTask;
    private Callbacks mCallbacks = sDummyCallbacks;
    private String mItem;
    
    private TextView tvDetail;

    public FileDetailFragement() {
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
        if (getArguments().containsKey(ARG_ITEM_ID)) {
        	mItem = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_file_detail, container, false);
    	tvDetail = (TextView) rootView.findViewById(R.id.file_detail);
    	if(TheFileTreeApp.getCurrentFilePath() != null && treeGetDirTask == null){
    		treeGetDirTask = new RetrieveFile(this);
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(),"start");
    	}else{
    		tvDetail.setText("An error Has occured");
    	}
        return rootView;
    }

	@Override
	public void onTaskCompleted() {
		TreeAPI api = treeGetDirTask.getAPICompleted();
		if(api.getFile().isText()){
			tvDetail.setText(api.getTextFile().getContent());
		}else{
			tvDetail.setText("Sorry Binary Files not yet Supported");
		}
		treeGetDirTask = null;
	}

	@Override
	public void reloadFile() {
		treeGetDirTask = new RetrieveFile(this);
		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(),"start");
	}

	@Override
	public void reset() {
		tvDetail.setText("");
		TheFileTreeApp.setCurrentFilePath("");
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
}
