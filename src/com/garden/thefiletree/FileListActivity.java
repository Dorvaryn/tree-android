package com.garden.thefiletree;

import com.garden.thefiletree.fragments.FileCodeMirrorFragment;
import com.garden.thefiletree.fragments.FileDetailFragement;
import com.garden.thefiletree.fragments.FileListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FileListActivity extends FragmentActivity
        implements FileListFragment.Callbacks {

    private boolean mTwoPane;
    private FileListFragment fragmentList;
    private FileDetailFragement fragementDetail;
    
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
    	if(!TheFileTreeApp.getCurrentDirPath().equalsIgnoreCase("")){
			int lastSlash = TheFileTreeApp.getCurrentDirPath().lastIndexOf('/');
			TheFileTreeApp.setCurrentDirPath(TheFileTreeApp.getCurrentDirPath().substring(0, lastSlash));
			if(mTwoPane && fragementDetail != null){
				fragementDetail.reset();
			}
			fragmentList.reloadFile();
    	}else{
    		super.onBackPressed();
    	}
	}

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            //FileCodeMirrorFragment fragment = new FileCodeMirrorFragment();
            FileCodeMirrorFragment fragment = new FileCodeMirrorFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.file_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, FileCodeMirrorActivity.class);
            startActivity(detailIntent);
        }
    }

	@Override
	public void setFragment(Fragment fragment) {
		if(fragment instanceof FileListFragment){
			this.fragmentList = (FileListFragment) fragment;
		}else if(fragment instanceof FileDetailFragement){
			this.fragementDetail = (FileDetailFragement) fragment;
		}
		
	}
}
