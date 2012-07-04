package com.garden.thefiletree;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garden.thefiletree.api.TreeFile;
import com.garden.thefiletree.fragments.RetrieveHomeTaskFragment;
import com.garden.thefiletree.fragments.RetrieveHomeTaskFragment.CallbacksHome;
import com.garden.thefiletree.task.PrefetchFiles;

public class HomeActivity extends FragmentActivity implements CallbacksHome {

	private RetrieveHomeTaskFragment fragmentHome;
	private ProgressDialog dialogProgress;
	private boolean dialogPending = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		final TheFileTreeApp app = (TheFileTreeApp) getApplication();

		FragmentManager fm = getSupportFragmentManager();
		fragmentHome = (RetrieveHomeTaskFragment) fm
				.findFragmentByTag("TaskHome");
		if (fragmentHome == null) {
			fragmentHome = new RetrieveHomeTaskFragment();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(fragmentHome, "TaskHome");
			ft.commit();
			fm.executePendingTransactions();
		}

		if (savedInstanceState != null) {
			dialogPending = savedInstanceState.getBoolean("dialogPending");
		}

		Button btHome = (Button) findViewById(R.id.buttonHome);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (app.getYourHome() != null) {
					TheFileTreeApp.setCurrentDirPath(app.getYourHome());
					Intent detailIntent = new Intent(HomeActivity.this,
							FileListActivity.class);
					startActivity(detailIntent);
				} else {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							HomeActivity.this);

					alert.setTitle("The File Tree");
					alert.setMessage("Enter your home");

					// Set an EditText view to get user input
					final EditText input = new EditText(HomeActivity.this);
					alert.setView(input);

					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String value = input.getText().toString();
									dialogProgress = ProgressDialog.show(
											HomeActivity.this, "",
											"Loading. Please wait...", true);
									dialogPending = true;
									fragmentHome.treeGetFile(value);
								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Canceled.
								}
							});
					alert.show();
				}
			}
		};
		btHome.setOnClickListener(listener);

		OnClickListener rootListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				TheFileTreeApp.setCurrentDirPath("");
				Intent detailIntent = new Intent(HomeActivity.this,
						FileListActivity.class);
				startActivity(detailIntent);
			}
		};
		Button btRoot = (Button) findViewById(R.id.buttonRoot);
		btRoot.setOnClickListener(rootListener);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean("dialogPending", dialogPending);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (dialogPending) {
			dialogProgress = ProgressDialog.show(this, "",
					"Loading. Please wait...", true);
		}
	}

	@Override
	protected void onPause() {
		if (dialogProgress != null) {
			dialogProgress.dismiss();
		}
		super.onPause();
	}

	@Override
	public void onLoadOver(TreeFile file) {
		if (dialogPending = true && dialogProgress != null) {
			dialogProgress.cancel();
			dialogPending = false;
		}
		if (TheFileTreeApp.getApi().getFile() != null
				&& TheFileTreeApp.getApi().getFile().getContent() != null) {
			((TheFileTreeApp) getApplication()).setYourHome(file.getPath());
			TheFileTreeApp.setCurrentDirPath(file.getPath());
			Intent detailIntent = new Intent(this, FileListActivity.class);
			startActivity(detailIntent);
		} else {
			Toast.makeText(HomeActivity.this, "Error this is not a valid Path",
					Toast.LENGTH_LONG).show();
		}
	}
}
