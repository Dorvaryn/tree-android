package com.garden.thefiletree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class fileListActivity extends FragmentActivity
        implements fileListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        if (findViewById(R.id.file_detail_container) != null) {
            mTwoPane = true;
            ((fileListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.file_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(fileCodeMirrorFragment.ARG_ITEM_ID, id);
            fileCodeMirrorFragment fragment = new fileCodeMirrorFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.file_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, fileDetailActivity.class);
            detailIntent.putExtra(fileCodeMirrorFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
