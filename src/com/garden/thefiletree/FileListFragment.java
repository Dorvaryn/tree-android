package com.garden.thefiletree;

import android.R;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    	public void setFragment(FileListFragment fragment);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }

		@Override
		public void setFragment(FileListFragment fragment) {		
		}
    };

    public FileListFragment() {
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	if(treeGetDirTask == null){
    		treeGetDirTask = new RetrieveFile(this);
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(),"start");
    	}
    }
    
    public void onTaskCompleted() {
    	TreeAPI api = treeGetDirTask.getAPICompleted();
    	if(api.isDirectory()){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		        setListAdapter(new ArrayAdapter<String>(getActivity(),
		                R.layout.simple_list_item_activated_1,
		                R.id.text1,
		                api.getDir().getContent()));
		    }else {
		    	setListAdapter(new ArrayAdapter<String>(getActivity(),
		                R.layout.simple_list_item_1,
		                R.id.text1,
		                api.getDir().getContent()));
		    }
    	}else{
    		mCallbacks.onItemSelected(api.getTextFile().getPath());
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
        String clicked = (String) getListAdapter().getItem(position);
        String newPath = TheFileTreeApp.getCurrentFilePath()+"/"+clicked;
        if(treeGetDirTask == null){
        	treeGetDirTask = new RetrieveFile(this);
        	treeGetDirTask.execute(newPath,"nav");
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
    		treeGetDirTask.execute(TheFileTreeApp.getCurrentFilePath(),"nav");
    	}
	}
}
