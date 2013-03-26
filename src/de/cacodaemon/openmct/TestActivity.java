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

public class TestActivity extends BaseActivity {
	protected Test test;
	protected SteppedGuiBuilder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		getImageButton(R.id.imageButtonNext).setOnClickListener(
				onButtonNextClick);
		getImageButton(R.id.imageButtonPervious).setOnClickListener(
				onButtonPerviousClick);
		getButton(R.id.buttonFinish).setOnClickListener(onButtonFinishClick);
		getButton(R.id.buttonHint).setOnClickListener(onButtonHintClick);

		String fileName = getIntent().getStringExtra("FILE_NAME");
		JsonLoader loader = null;
		try {
			loader = new JsonLoader(openFileInput(fileName));
			loader.load();

			builder = new SteppedGuiBuilder(test = loader.getTest(),
					getApplicationContext(),
					getLinearLayout(R.id.linearLayoutTest),
					getImageButton(R.id.imageButtonNext),
					getImageButton(R.id.imageButtonPervious),
					getButton(R.id.buttonHint));
			builder.build();
			setTitle(getTitle() + " - " + test.getTitle());
		} catch (Exception e) {
			Log.v("OpenMCT", e.getMessage());
			e.printStackTrace();
		}

	}

	OnClickListener onButtonNextClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.v("OpenMCT", (builder == null) + "NULL?");
			Log.v("OpenMCT", builder.getCurrent() + "CURR");
			if (builder.setCurrent(builder.getCurrent() + 1)) {
				builder.build();
			}
		}

	};

	OnClickListener onButtonPerviousClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.v("OpenMCT", (builder == null) + "NULL?");
			Log.v("OpenMCT", builder.getCurrent() + "CURR");
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
