package de.cacodaemon.openmct;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DownloadTest extends BaseActivity {

	/**
	 * Async task for downloading a file.
	 * 
	 */
	private class Download extends AsyncTask<URL, Void, Boolean> {
		@Override
		protected Boolean doInBackground(URL... urls) {
			try {
				String name = getBaseName(urls[0].getFile());

				InputStream is = urls[0].openStream();
				OutputStream os = openFileOutput(name, Context.MODE_PRIVATE);
				int c;
				while ((c = is.read()) != -1) {
					os.write(c);
				}
				is.close();
				os.close();
			} catch (Exception e) {
				Log.v("OpenMCT", e.getMessage());
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean done) {
			if (done) {
				showToastLong(R.string.download_done);
				finish();
			} else {
				showToastLong(R.string.download_error);
				getButton(R.id.buttonDownloadTest).setEnabled(true);
				getEditText(R.id.editTextUrl).setEnabled(true);
				getProgressBar(R.id.progressBarDownload).setVisibility(
						View.INVISIBLE);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_test);
		getButton(R.id.buttonDownloadTest).setOnClickListener(
				onButtonDownloadClick);
	}

	/**
	 * This listener start the async downlading task and disables some gui
	 * elements.
	 */
	View.OnClickListener onButtonDownloadClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			URL url;
			TextView textViewUrl = getEditText(R.id.editTextUrl);
			try {
				showToastLong(R.string.download_start);
				url = new URL(textViewUrl.getText().toString());
				getButton(R.id.buttonDownloadTest).setEnabled(false);
				textViewUrl.setEnabled(false);
				getProgressBar(R.id.progressBarDownload).setVisibility(
						View.VISIBLE);
				new Download().execute(url);
			} catch (MalformedURLException e) {
				showToastLong(R.string.download_error_url_format);
			}
		}
	};

	/**
	 * Gets the basename form the given file name.
	 * 
	 * @param fileName
	 * @return base filename
	 */
	protected static String getBaseName(String fileName) {
		int dot = fileName.lastIndexOf(".");
		int sep = fileName.lastIndexOf("/");
		return fileName.substring(sep + 1, dot);
	}
}
