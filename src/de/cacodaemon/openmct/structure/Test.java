package de.cacodaemon.openmct.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a multiple choice test.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 * 
 */
public class Test implements Serializable {
	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6301189383918269321L;

	/**
	 * The test title.
	 */
	protected final String title;

	/**
	 * A description text.
	 */
	protected final String description;

	/**
	 * The test author (optional).
	 */
	protected final String author;

	/**
	 * A url assigned to the test (optional).
	 */
	protected final String url;

	/**
	 * A list of questions.
	 */
	protected final List<Question> questions;

	public Test(String title, String description, String author, String url,
			List<Question> questions) {
		super();
		this.title = title;
		this.description = description;
		this.author = author;
		this.url = url;
		this.questions = questions;
	}

	public Test(String title, String description) {
		this(title, description, null, null, new ArrayList<Question>());
	}

	/**
	 * Gets the title.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the test description text.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the author of the test or null.
	 * 
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Gets the url assigned to the test.
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the test questions.
	 * 
	 * @return
	 */
	public List<Question> getQuestions() {
		return questions;
	}
}
