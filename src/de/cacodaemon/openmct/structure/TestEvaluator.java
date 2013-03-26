package de.cacodaemon.openmct.structure;

import android.annotation.SuppressLint;

/**
 * A simple <code>Test</code> evaluator.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public class TestEvaluator implements ITestEvaluator {
	protected Test test;
	protected int sumQuestions;
	protected int sumQuestionsPassed;

	public void setTest(Test test) {
		this.test = test;
		sumQuestions = test.getQuestions().size();
	}

	public void evaluate() {
		for (Question question : test.getQuestions()) {
			int i = 0;
			int questionRightAnswers = 0;
			int questionPassedAnswers = 0;
			for (Boolean answer : question.getRightAnswers()) {
				questionRightAnswers++;
				if (question.getUserAnswers().get(i) == answer) {
					questionPassedAnswers++;
				}
				i++;
			}

			if (questionRightAnswers == questionPassedAnswers) {
				sumQuestionsPassed++;
			}
		}
	}

	public int getSumQuestions() {
		return sumQuestions;
	}

	public int getSumQuestionsPassed() {
		return sumQuestionsPassed;
	}

	public int getPercentagePassed() {
		return (int) Math
				.floor(((double) sumQuestionsPassed / (double) sumQuestions) * 100.0);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return String
				.format("Total number of questions: %d, number of passed questions: %d, percentage passed: %d%%",
						sumQuestions, sumQuestionsPassed, getPercentagePassed());
	}
}
