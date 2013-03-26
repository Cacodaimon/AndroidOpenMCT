package de.cacodaemon.openmct.structure;

/**
 * Interface for classes evaluating a <code>Test</code>.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public interface ITestEvaluator {
	/**
	 * Sets the <code>Test</code> to evaluate.
	 * 
	 * @param test
	 */
	void setTest(Test test);

	/**
	 * Evaluates the given test.
	 * 
	 * @param test
	 */
	void evaluate();

	/**
	 * Gets the total count of question in the given test.
	 * 
	 * @return
	 */
	int getSumQuestions();

	/**
	 * Gets the count of passed of question in the given test.
	 * 
	 * @return
	 */
	int getSumQuestionsPassed();

	/**
	 * Gets the percentage of passed question.
	 * 
	 * @return
	 */
	int getPercentagePassed();
}
