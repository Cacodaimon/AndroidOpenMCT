/**
 * 
 */
package de.cacodaemon.openmct.structure.test;

import java.util.Arrays;

import de.cacodaemon.openmct.structure.ITestLoader;
import de.cacodaemon.openmct.structure.Question;
import de.cacodaemon.openmct.structure.Test;

/**
 * Mock loader for testing.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public class MockLoader implements ITestLoader {
	private Test test;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cacodaemon.openmct.structure.ITestLoader#load()
	 */
	@Override
	public void load() throws Exception {
		test = new Test("My First Test", "Description text,\n" + "Bla bla...");

		test.getQuestions().add(
				new Question("Title Foo", "Question Foo", Arrays
						.asList(new String[] { "Foo 1", "Foo 2", "Foo 3*",
								"Foo 4" }), Arrays.asList(false, false, true,
						false)));

		test.getQuestions().add(
				new Question("Title Bar", "Question Bar", Arrays
						.asList(new String[] { "Foo 1", "Foo 2*", "Foo 3*",
								"Foo 4" }), Arrays.asList(false, true, true,
						false)));

		test.getQuestions().add(
				new Question("Title Baz", "Question Baz", Arrays
						.asList(new String[] { "Foo 1*", "Foo 2", "Foo 3",
								"Foo 4*" }), Arrays.asList(true, false, false,
						true)));

		test.getQuestions().add(
				new Question("Title Bazinga", "Question Bazinga", Arrays
						.asList(new String[] { "Foo 1", "Foo 2", "Foo 3*",
								"Foo 4" }), Arrays.asList(false, false, true,
						false)));

		test.getQuestions().add(
				new Question("Title Bazinga", "Question Bazinga", Arrays
						.asList(new String[] { "Foo 1", "Foo 2*", "Foo 3",
								"Foo 4" }), Arrays.asList(false, true, false,
						false)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cacodaemon.openmct.structure.ITestLoader#getTest()
	 */
	@Override
	public Test getTest() {
		return test;
	}

}
