package de.cacodaemon.openmct;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StartupActivity extends BaseActivity {
	protected String fileNameToDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

		ListView listView = getListView(R.id.listViewTests);
		fillTestList(listView);
		listView.setOnItemClickListener(onTestClickStart);
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(onTestClickDelete);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startup, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals(getString(R.string.menu_add))) {
			showAddActivity();
			return true;
		}
		if (item.getTitle().toString().equals(getString(R.string.menu_about))) {
			startActivity(new Intent(StartupActivity.this, AboutActivity.class));
			return true;
		}

		return true;
	}

	/**
	 * Displays the delete confirm dialog.
	 * 
	 * @param fileName
	 */
	protected void showDeleteDialog(String fileName) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("The test \"" + fileName + "\" will be deleted!");
		builder.setCancelable(true);
		builder.setPositiveButton("OK", onConfirmDeleteOkClick);
		builder.setNegativeButton("Cancel", onConfirmDeleteCancelClick);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	protected void showAddActivity() {
		Intent intent = new Intent(StartupActivity.this, DownloadTest.class);
		startActivityForResult(intent, 1);
	}

	/**
	 * Used by the DownloadTest Activity, refreshes the lists of available
	 * tests.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		fillTestList(getListView(R.id.listViewTests));
	}

	/**
	 * Refreshes the list of available tests.
	 * 
	 * @param listView
	 */
	protected void fillTestList(ListView listView) {
		listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				fileList()));
	}

	/**
	 * This listener gets called by the delete confimation dialog on success
	 * case and deletes the given test.
	 */
	DialogInterface.OnClickListener onConfirmDeleteOkClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (fileNameToDelete == null) {
				return;
			}

			deleteFile(fileNameToDelete);
			showToastLong(fileNameToDelete + getString(R.string.deleted_test));
			fileNameToDelete = null;
			fillTestList(getListView(R.id.listViewTests));
		}
	};

	/**
	 * This listener gets called by the delete confimation dialog, when the user
	 * canels the deletion process and is just a dummy .
	 */
	DialogInterface.OnClickListener onConfirmDeleteCancelClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	};

	/**
	 * This listener starts the selected test.
	 */
	OnItemClickListener onTestClickStart = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String fileName = getListView(R.id.listViewTests)
					.getItemAtPosition(position).toString();

			Intent intent = new Intent(StartupActivity.this, TestActivity.class);
			intent.putExtra("FILE_NAME", fileName);
			startActivity(intent);
		}
	};

	/**
	 * The long click listener show the deletion confirmation dialog.
	 */
	OnItemLongClickListener onTestClickDelete = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Object item = getListView(R.id.listViewTests).getItemAtPosition(
					position);
			showDeleteDialog(fileNameToDelete = item.toString());

			return true;
		}
	};
}
