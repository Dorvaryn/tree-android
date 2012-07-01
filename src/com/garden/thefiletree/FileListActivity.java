package com.garden.thefiletree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FileListActivity extends FragmentActivity
        implements FileListFragment.Callbacks {

    private boolean mTwoPane;
    private FileListFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        if (findViewById(R.id.file_detail_container) != null) {
            mTwoPane = true;
            ((FileListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.file_list))
                    .setActivateOnItemClick(true);
        }
    }
    
    @Override
	public void onBackPressed() {
    	if(!TheFileTreeApp.getCurrentFilePath().equalsIgnoreCase("")){
			int lastSlash = TheFileTreeApp.getCurrentFilePath().lastIndexOf('/');
			TheFileTreeApp.setCurrentFilePath(TheFileTreeApp.getCurrentFilePath().substring(0, lastSlash));
			fragment.reloadFile();
    	}else {
    		super.onBackPressed();
    	}
	}

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(FileCodeMirrorFragment.ARG_ITEM_ID, id);
            //FileCodeMirrorFragment fragment = new FileCodeMirrorFragment();
            FileDetailFragement fragment = new FileDetailFragement();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.file_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, FileDetailActivity.class);
            detailIntent.putExtra(FileCodeMirrorFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

	@Override
	public void setFragment(FileListFragment fragment) {
		this.fragment = fragment;
	}
}
