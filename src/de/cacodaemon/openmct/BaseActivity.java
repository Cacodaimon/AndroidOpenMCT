package de.cacodaemon.openmct;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the base activity which contains some useful helpers.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public abstract class BaseActivity extends FragmentActivity {
	public static final String PREFS_NAME = "de.cacodaemon.openmct";

	protected SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
	}

	/**
	 * Gets a <code>LinearLayout</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected LinearLayout getLinearLayout(int id) {
		return (LinearLayout) findViewById(id);
	}

	/**
	 * Gets a <code>ScrollView</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected ScrollView getScrollView(int id) {
		return (ScrollView) findViewById(id);
	}

	/**
	 * Gets a <code>Button</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected Button getButton(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * Gets a <code>ImageButton</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected ImageButton getImageButton(int id) {
		return (ImageButton) findViewById(id);
	}

	/**
	 * Gets a <code>TextView</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected TextView getTextView(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * Gets a <code>EditText</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected EditText getEditText(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * Gets a <code>RatingBar</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected RatingBar getRatingBar(int id) {
		return (RatingBar) findViewById(id);
	}

	/**
	 * Gets a <code>ImageView</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected ImageView getImageView(int id) {
		return (ImageView) findViewById(id);
	}
	
	/**
	 * Gets a <code>ListView</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected ListView getListView(int id) {
		return (ListView) findViewById(id);
	}

	/**
	 * Gets a <code>ProgressBar</code> by the given id.
	 * 
	 * @param id
	 * @return
	 */
	protected ProgressBar getProgressBar(int id) {
		return (ProgressBar) findViewById(id);
	}

	/**
	 * Show a toast for a long time.
	 * 
	 * @param id
	 */
	protected void showToastLong(int id) {
		showToastLong(getString(id));
	}

	/**
	 * Show a toast for a long time.
	 * 
	 * @param text
	 */
	protected void showToastLong(CharSequence text) {
		showToast(text, Toast.LENGTH_LONG);
	}

	/**
	 * Show a toast for a short time.
	 * 
	 * @param id
	 */
	protected void showToastShort(int id) {
		showToastShort(getString(id));
	}

	/**
	 * Show a toast for a short time.
	 * 
	 * @param text
	 */
	protected void showToastShort(CharSequence text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	/**
	 * Shows a toast for the given duration.
	 * 
	 * @param text
	 * @param duration
	 */
	private void showToast(CharSequence text, int duration) {
		Context context = getApplicationContext();
		Toast.makeText(context, text, duration).show();
	}
}
