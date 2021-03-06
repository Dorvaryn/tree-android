package com.garden.thefiletree.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garden.thefiletree.TheFileTreeApp;
import com.garden.thefiletree.TreeAdapter;
import com.garden.thefiletree.api.TreeAPI;
import com.garden.thefiletree.api.TreeFile;
import com.garden.thefiletree.callbacks.FragmentReload;
import com.garden.thefiletree.callbacks.TreeTaskCallbacks;
import com.garden.thefiletree.task.RetrieveFile;

public class FileListFragment extends ListFragment implements TreeTaskCallbacks, FragmentReload{

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private RetrieveFile treeGetDirTask;

    public interface Callbacks {

        public void onItemSelected(String id);
    	public void setFragment(Fragment fragment);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }

		@Override
		public void setFragment(Fragment fragment) {		
		}
    };

    public FileListFragment() {
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	if(treeGetDirTask == null){
    		treeGetDirTask = new RetrieveFile(this);
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentDirPath(),"start");
    	}
    }
    
    public void onTaskCompleted() {
    	TreeAPI api = treeGetDirTask.getAPICompleted();
    	if(api.getFile().isDirectory()){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		        setListAdapter(new TreeAdapter(getActivity(),
		        		android.R.layout.simple_list_item_activated_1,
		                android.R.id.text1,
		                api.getDir().getContent()));
		    }else {
		    	setListAdapter(new TreeAdapter(getActivity(),
		    			android.R.layout.simple_list_item_1,
		    			android.R.id.text1,
		                api.getDir().getContent()));
		    }
    	}else {
    		mCallbacks.onItemSelected(api.getFile().getPath());
    	}
    	treeGetDirTask = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
        mCallbacks.setFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        TreeFile clicked = (TreeFile) getListAdapter().getItem(position);
        if(treeGetDirTask == null){
        	treeGetDirTask = new RetrieveFile(this);
        	treeGetDirTask.execute(clicked.getPath(),"nav");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

	@Override
	public void reloadFile() {
		if(treeGetDirTask == null){
    		treeGetDirTask = new RetrieveFile(this);
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentDirPath(),"nav");
    	}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
