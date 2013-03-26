package de.cacodaemon.openmct.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>Test</code> question.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 * 
 */
public class Question implements Serializable {
	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -7833057622071215913L;

	/**
	 * The question title.
	 */
	protected final String title;

	/**
	 * The question text.
	 */
	protected final String text;

	/**
	 * A optional text describing the right or wrong answers.
	 */
	protected final String answerDescription;

	/**
	 * A optional hint.
	 */
	protected final String hint;

	/**
	 * Possible answer options as list.
	 */
	protected final List<String> options;

	/**
	 * Answer options selected by the user.
	 */
	protected final List<Boolean> userAnswers;

	/**
	 * Possible answer options as list.
	 */
	protected final List<Boolean> rightAnswers;

	public Question(String title, String text, List<String> options,
			List<Boolean> rightAnswers, String answerDescription, String hint) {
		super();
		this.title = title;
		this.text = text;
		this.answerDescription = answerDescription;
		this.hint = hint;
		this.options = options;
		this.rightAnswers = rightAnswers;
		this.userAnswers = new ArrayList<Boolean>();

		for (int i = 0; i < rightAnswers.size(); i++) {
			this.userAnswers.add(false);
		}
	}

	public Question(String title, String text, List<String> options,
			List<Boolean> rightAnswers) {
		this(title, text, options, rightAnswers, "", "");
	}

	/**
	 * Gets the question title.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the question text.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the hint text.
	 * 
	 * @return
	 */
	public String getHint() {
		return hint;
	}

	/**
	 * Gets the answer description text.
	 * 
	 * @return
	 */
	public String getAnswerDescription() {
		return answerDescription;
	}

	/**
	 * Gets the possible answer options.
	 * 
	 * @return
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * Gets a list of the right answers.
	 * 
	 * @return
	 */
	public List<Boolean> getRightAnswers() {
		return rightAnswers;
	}

	/**
	 * Gets a list of the right answers.
	 * 
	 * @return
	 */
	public List<Boolean> getUserAnswers() {
		return userAnswers;
	}

	/**
	 * Determines if the question has one
	 * 
	 * @return
	 */
	public QuestionType getType() {
		int answerCount = 0;
		for (Boolean answer : rightAnswers) {
			if (!answer) {
				continue;
			}
			answerCount++;
		}

		switch (answerCount) {
		case 0:
			return QuestionType.TRICK;
		case 1:
			return QuestionType.SINGLE;
		default:
			return QuestionType.MULTIPLE;
		}
	}
}
