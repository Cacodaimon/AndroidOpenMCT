package de.cacodaemon.openmct.gui;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.cacodaemon.openmct.R;
import de.cacodaemon.openmct.structure.Question;
import de.cacodaemon.openmct.structure.Test;

/**
 * Builds a gui for the given Test. The gui gets build just for the current
 * question.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 * 
 */
public class SteppedGuiBuilder {
	/**
	 * The test.
	 */
	protected Test test;
	/**
	 * The current question index.
	 */
	protected int current;
	/**
	 * The context used for building the gui.
	 */
	protected Context context;
	/**
	 * Layout container for the gui elements.
	 */
	protected LinearLayout layout;
	/**
	 * The onClick finish listener used by the button build in
	 * <code>buildFooter</code>.
	 */
	protected OnClickListener onFinishClick;
	/**
	 * View which should be disabled if the last question of the test is selected.
	 */
	protected View next;
	/**
	 * View which should be disabled if the first question of the test is selected.
	 */
	protected View previous;
	/**
	 * View which should be hidden if the current question has no hint text.
	 */
	protected View hint;
	/**
	 * A simple counter used for generating unique id's. Should not be used
	 * directly, use <code>getId()</code> instead.
	 */
	private static int idCounter;

	public SteppedGuiBuilder(Test test, Context context, LinearLayout layout,
			View next, View previous, View hint) {
		this.test = test;
		this.context = context;
		this.layout = layout;
		this.next = next;
		this.previous = previous;
		this.hint = hint;
		setCurrent(-1);
	}

	/**
	 * Builds the gui.
	 */
	public void build() {
		layout.removeAllViews();

		if (current < 0) {
			buildWelcome();
			return;
		}

		buildQuestion();
	}

	/**
	 * Sets the current question to build.
	 * 
	 * @param index
	 * @return true if index was valid, otherwise false.
	 */
	public boolean setCurrent(int index) {
		if (index < -1) {
			return false;
		}

		if (index > test.getQuestions().size() - 1) {
			return false;
		}

		current = index;

		next.setEnabled(current < test.getQuestions().size() - 1);
		previous.setEnabled(current > -1);
		return true;
	}

	public int getCurrent() {
		return current;
	}

	/**
	 * Builds the question header including title and description. The header
	 * will be appended to <code>layout</code>.
	 */
	protected void buildWelcome() {
		TextView title = new TextView(context);
		title.setText(test.getTitle());
		title.setTextAppearance(context, R.style.TestTitle);
		layout.addView(title);

		TextView description = new TextView(context);
		description.setText(test.getDescription());
		description.setTextAppearance(context, R.style.TestDescription);
		layout.addView(description);
		
        hint.setVisibility(View.INVISIBLE);
	}

	/**
	 * Builds the gui elements needed for the given question. The question will
	 * be appended to <code>layout</code>.
	 * 
	 * @param question
	 */
	protected void buildQuestion() {
		Question question = test.getQuestions().get(current);
		hint.setVisibility(question.getHint().equals("") ? View.INVISIBLE
				: View.VISIBLE);
		
		buildQuestionHeader(question);

		switch (question.getType()) {
		case SINGLE:
			buildSingleAnswerQuestion(question);
			break;
		case MULTIPLE:
		case TRICK:
			buildMultipleAnswerQuestion(question);
			break;
		}
	}

	/**
	 * Builds the question header including the question title and text. The
	 * question header will be appended to <code>layout</code>.
	 * 
	 * @param question
	 */
	protected void buildQuestionHeader(Question question) {
		TextView questionTitle = new TextView(context);
		questionTitle.setText(question.getTitle());
		questionTitle.setTextAppearance(context, R.style.QuestionTitle);
		layout.addView(questionTitle);

		TextView questionText = new TextView(context);
		questionText.setText(question.getText());
		questionText.setTextAppearance(context, R.style.QuestionText);
		layout.addView(questionText);
	}

	/**
	 * Builds a question with a single answer option. The question will be
	 * appended to <code>layout</code>.
	 * 
	 * @param question
	 */
	protected void buildSingleAnswerQuestion(Question question) {
		RadioGroup groupOptions = new RadioGroup(context);

		int i = 0;
		int currentId = 0;
		for (String option : question.getOptions()) {
			RadioButton radioOption = new RadioButton(context);
			radioOption.setText(option);
			radioOption.setTextAppearance(context, R.style.QuestionOption);
			radioOption.setId(currentId = getId());
			radioOption.setOnCheckedChangeListener(checkBoxListener);
			radioOption.setTag(new QuestionTag<Integer>(question, i));
			groupOptions.addView(radioOption);

			if (question.getUserAnswers().get(i)) {
				groupOptions.check(currentId);
			}
			i++;
		}
		layout.addView(groupOptions);
	}

	/**
	 * Builds a question with multiple answer options. The question will be
	 * appended to <code>layout</code>.
	 * 
	 * @param question
	 */
	protected void buildMultipleAnswerQuestion(Question question) {
		int i = 0;
		for (String option : question.getOptions()) {
			CheckBox checkBox = new CheckBox(context);
			checkBox.setText(option);
			checkBox.setTextAppearance(context, R.style.QuestionOption);
			checkBox.setChecked(question.getUserAnswers().get(i));
			checkBox.setOnCheckedChangeListener(checkBoxListener);
			checkBox.setTag(new QuestionTag<Integer>(question, i++));
			layout.addView(checkBox);
		}
	}

	/**
	 * The <code>OnCheckedChangeListener</code> sets the question user answers.
	 */
	OnCheckedChangeListener checkBoxListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			@SuppressWarnings("unchecked")
			QuestionTag<Integer> questionTag = (QuestionTag<Integer>) buttonView
					.getTag();
			questionTag.getQuestion().getUserAnswers()
					.set(questionTag.getTag(), isChecked);
		}
	};

	/**
	 * Generates a id for the given element.
	 * 
	 * @return A new id.
	 */
	private int getId() {
		return idCounter++;
	}
}
