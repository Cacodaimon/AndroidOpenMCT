package de.cacodaemon.openmct;

import de.cacodaemon.openmct.structure.Test;
import de.cacodaemon.openmct.structure.TestEvaluator;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class EvaluateTestActivity extends BaseActivity {
	protected Test test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluate_test);

		TestEvaluator evaluator = new TestEvaluator();
		evaluator.setTest(getTest());
		evaluator.evaluate();

		getTextView(R.id.textViewPassTest).setText(evaluator.toString());
		getRatingBar(R.id.ratingBarTest).setRating(
				evaluator.getPercentagePassed() / 100.0f * 5.0f);
		setTitle(getTitle() + " - " + getTest().getTitle());

		getButton(R.id.buttonEvaluateTryAgain).setOnClickListener(
				onTryAgainClick);
	}

	protected Test getTest() {
		if (test != null) {
			return test;
		}

		if (!getIntent().hasExtra("Test")) {
			showToastLong("Error loading test");
			finish();
		}

		return test = (Test) getIntent().getSerializableExtra("Test");
	}

	View.OnClickListener onTryAgainClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
}
