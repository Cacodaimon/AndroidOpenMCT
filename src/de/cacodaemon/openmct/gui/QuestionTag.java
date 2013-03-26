package de.cacodaemon.openmct.gui;

import de.cacodaemon.openmct.structure.Question;

/**
 * Helper class for the <code>GuiBuilder</code>.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 * 
 */
public class QuestionTag<T> {
	/**
	 * The question object.
	 */
	protected Question question;
	/**
	 * A tag for the question object.
	 */
	protected T tag;

	public QuestionTag(Question question, T tag) {
		this.question = question;
		this.tag = tag;
	}

	/**
	 * Gets the <code>Question</code>.
	 * 
	 * @return
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Sets the <code>Question</code>.
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Gets the tag.
	 */
	public T getTag() {
		return tag;
	}

	/**
	 * Sets the tag.
	 */
	public void setTag(T tag) {
		this.tag = tag;
	}
}
