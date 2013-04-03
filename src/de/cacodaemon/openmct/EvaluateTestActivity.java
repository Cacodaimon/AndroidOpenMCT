package de.cacodaemon.openmct;

import java.util.List;

import de.cacodaemon.openmct.structure.Question;
import de.cacodaemon.openmct.structure.Test;
import de.cacodaemon.openmct.structure.TestEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

		try {
			List<Question> questions = getTest().getQuestions();
			ListView listView = getListView(R.id.listViewTestResult);

			listView.setAdapter(new ArrayAdapter<Question>(
					getApplicationContext(),
					android.R.layout.simple_list_item_checked,
					android.R.id.text1, questions) {
				public boolean isEnabled(int position) {
					return false;
				}
			});

			for (int position = 0; position < questions.size(); position++) {
				listView.setItemChecked(position, evaluator
						.isQuestionRight((Question) listView
								.getItemAtPosition(position)));
			}
		} catch (Exception e) {
			Log.v("OpenMCT", e.toString());
		}
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
