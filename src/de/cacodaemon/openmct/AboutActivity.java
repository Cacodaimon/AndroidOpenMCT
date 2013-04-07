package de.cacodaemon.openmct;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		getImageView(R.id.imageViewForkMe).setOnClickListener(
				onForkMeOnGitHubClick);
	}

	/**
	 * This listener gets called when prssed on the fork me on github image and
	 * opens the github url for this project.
	 */
	OnClickListener onForkMeOnGitHubClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.about_github)));
			startActivity(browserIntent);
		}
	};
}
