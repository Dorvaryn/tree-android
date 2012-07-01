package com.garden.thefiletree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class FileDetailActivity extends FragmentActivity {

	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_detail);
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(FileCodeMirrorFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FileCodeMirrorFragment.ARG_ITEM_ID));
            //FileCodeMirrorFragment fragment = new FileCodeMirrorFragment();
            FileDetailFragement fragment = new FileDetailFragement();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.file_detail_container, fragment)
                    .commit();
        }
    }

    @Override
	public void onBackPressed() {
    	int lastSlash = TheFileTreeApp.getCurrentFilePath().lastIndexOf('/');
		TheFileTreeApp.setCurrentFilePath(TheFileTreeApp.getCurrentFilePath().substring(0, lastSlash));
		super.onBackPressed();
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, FileListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
