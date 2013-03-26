package de.cacodaemon.openmct.structure;

/**
 * Interface for classes loading a <code>Test</code>.
 * 
 * @author Guido Krömer <mail@cacodaemon.de>
 */
public interface ITestLoader {
	/**
	 * Loads a Test.
	 * 
	 * @throws Exception
	 */
	void load() throws Exception;

	/**
	 * Gets the loaded Test.
	 * 
	 * @return
	 */
	Test getTest();
}
