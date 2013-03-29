package de.cacodaemon.openmct.structure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.*;

import android.util.Log;

/**
 * Loads a Test by the given JSON stream.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 * 
 */
public class JsonLoader implements ITestLoader {
	protected InputStream stream;
	protected Test test;

	public JsonLoader(InputStream stream) throws IOException {
		this.stream = stream;
	}

	public Test getTest() {
		return test;
	}

	public void load() throws Exception {
		String jsonString = streamToString(this.stream);
		try {
			JSONObject jsonTest = new JSONObject(jsonString);

			this.test = new Test(jsonTest.getString("title"),
					jsonTest.getString("description"));

			JSONArray jsonQuestions = jsonTest.getJSONArray("questions");

			for (int i = 0; i < jsonQuestions.length(); i++) {
				test.getQuestions().add(loadQuestion(jsonQuestions.getJSONObject(i)));

			}

		} catch (JSONException e) {
			Log.v("OpenMCT", e.getMessage());
		}
	}

	protected Question loadQuestion(JSONObject jsonQuestion) throws JSONException {
		Question question = new Question(
				jsonQuestion.getString("title"),
				jsonQuestion.getString("text"),
				new ArrayList<String>(), new ArrayList<Boolean>(),
				jsonQuestion.optString("answerDescription"),
				jsonQuestion.optString("hint"));

		JSONArray options = jsonQuestion.getJSONArray("options");
		for (int j = 0; j < options.length(); j++) {
			question.getOptions().add(options.getString(j));
		}

		JSONArray rightAnswers = jsonQuestion
				.getJSONArray("rightAnswers");
		for (int j = 0; j < rightAnswers.length(); j++) {
			question.getRightAnswers().add(rightAnswers.getBoolean(j));
			question.getUserAnswers().add(false);
		}
		
		return question;
	}
	
	/**
	 * Helper, converting a FileInputStream into a string.
	 * 
	 * @param stream
	 * @return The converted string.
	 * @throws IOException
	 */
	private String streamToString(InputStream stream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(stream));

		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		return sb.toString();
	}
}
