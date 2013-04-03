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
			if (isQuestionRight(question)) {
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

	public boolean isQuestionRight(Question question) {
		int i = 0;
		int rightAnswers = 0;
		int passedAnswers = 0;
		
		for (Boolean answer : question.getRightAnswers()) {
			rightAnswers++;
			if (question.getUserAnswers().get(i) == answer) {
				passedAnswers++;
			}
			i++;
		}
		
		return rightAnswers == passedAnswers;
	}
	
	
	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return String
				.format("Total number of questions: %d, number of passed questions: %d, percentage passed: %d%%",
						sumQuestions, sumQuestionsPassed, getPercentagePassed());
	}
}
