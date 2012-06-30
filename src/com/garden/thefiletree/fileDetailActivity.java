package com.garden.thefiletree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class fileDetailActivity extends FragmentActivity {

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
            arguments.putString(fileCodeMirrorFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(fileCodeMirrorFragment.ARG_ITEM_ID));
            fileCodeMirrorFragment fragment = new fileCodeMirrorFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.file_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, fileListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
