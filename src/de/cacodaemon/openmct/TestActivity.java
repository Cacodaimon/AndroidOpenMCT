package de.cacodaemon.openmct;

import de.cacodaemon.openmct.gui.SteppedGuiBuilder;
import de.cacodaemon.openmct.structure.JsonLoader;
import de.cacodaemon.openmct.structure.Question;
import de.cacodaemon.openmct.structure.Test;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TestActivity extends BaseActivity {
	protected Test test;
	protected SteppedGuiBuilder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		ImageButton buttonNext = getImageButton(R.id.imageButtonNext);
		buttonNext.setOnClickListener(onButtonNextClick);
		ImageButton buttonPervious = getImageButton(R.id.imageButtonPervious);
		buttonPervious.setOnClickListener(onButtonPerviousClick);
		Button buttonHint = getButton(R.id.buttonHint);
		buttonHint.setOnClickListener(onButtonHintClick);
		getButton(R.id.buttonFinish).setOnClickListener(onButtonFinishClick);

		String fileName = getIntent().getStringExtra("FILE_NAME");
        try {
        	JsonLoader loader = new JsonLoader(openFileInput(fileName));
			loader.load();

			builder = new SteppedGuiBuilder(test = loader.getTest(),
					getApplicationContext(),
					getLinearLayout(R.id.linearLayoutTest),
					buttonNext,
					buttonPervious,
					buttonHint);
			builder.build();
			setTitle(getTitle() + " - " + test.getTitle());
		} catch (Exception e) {
			Log.v("OpenMCT", e.getMessage());
			showToastLong(R.string.test_load_error);
			finish();
		}
	}

	OnClickListener onButtonNextClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (builder.setCurrent(builder.getCurrent() + 1)) {
				builder.build();
			}
		}

	};

	OnClickListener onButtonPerviousClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (builder.setCurrent(builder.getCurrent() - 1)) {
				builder.build();
			}
		}

	};

	OnClickListener onButtonFinishClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(TestActivity.this,
					EvaluateTestActivity.class);
			intent.putExtra("Test", test);
			startActivity(intent);
		}

	};

	OnClickListener onButtonHintClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Question currentQuestion = test.getQuestions().get(
					builder.getCurrent());
			showToastLong(currentQuestion.getHint());
		}

	};

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		Test test = (Test) savedInstanceState.getSerializable("test");

		if (test == null) {
			return;
		}

		this.test = test;
		builder = new SteppedGuiBuilder(test, getApplicationContext(),
				getLinearLayout(R.id.linearLayoutTest),
				getImageButton(R.id.imageButtonNext),
				getImageButton(R.id.imageButtonPervious),
				getButton(R.id.buttonHint));
		builder.setCurrent(savedInstanceState.getInt("builder"));
		builder.build();

	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return test;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putSerializable("test", test);
		savedInstanceState.putInt("builder", builder.getCurrent());
	}
}
