package ma.norsys.formation.csv.util;

import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_QUESTION_TEST;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_RESPONSE_TEST;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_TOPIC_TEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import ma.norsys.formation.entities.Question;
import ma.norsys.formation.entities.Topic;

import org.junit.Before;
import org.junit.Test;

public class CSVSingletonTest {
	CSVSingleton singleton;

	@Before
	public void setUp() throws Exception {
		singleton = CSVSingleton.getInstance();
	}

	@Test
	public void siSingletonNEstPasNullTest() {
		assertNotNull(singleton);
	}

	@Test
	public void getResourceTest() {
		// Param
		final String fileName = FILE_NAME_QUESTION_TEST;

		// Appel
		File file = singleton.getResource(fileName);

		// Test
		// On sait que le fichier existe bien puisque c'est avec lui qu'on
		// travaille depuis le début.
		assertTrue(file.exists());
	}

	@Test
	public void readFileTest() throws Exception {
		// Param
		final String fileName = FILE_NAME_QUESTION_TEST;

		// Result
		final int nombreLigne = 3;

		// Appel
		final File file = singleton.getResource(fileName);
		List<String> lines = singleton.readFile(file);

		// Test
		assertEquals(nombreLigne, lines.size());
	}

	@Test
	public void listQuestionsTest() throws Exception {
		// Param
		final int nombre = 2;
		final File fileQuestion = singleton
				.getResource(FILE_NAME_QUESTION_TEST);
		final File fileResponse = singleton
				.getResource(FILE_NAME_RESPONSE_TEST);
		final File fileTopic = singleton.getResource(FILE_NAME_TOPIC_TEST);
		List<String> linesQuestion = singleton.readFile(fileQuestion);
		List<String> linesResponse = singleton.readFile(fileResponse);
		List<String> linesTopic = singleton.readFile(fileTopic);
		List<Question> listeQuestion = (List<Question>) singleton
				.listQuestions(linesTopic, linesQuestion, linesResponse);
		assertNotNull(listeQuestion);
		assertEquals(nombre, listeQuestion.size());
	}
	
	@Test
	public void listTopicsTest() throws Exception {
		// Param
		final int nombre = 1;
		final File fileTopic = singleton.getResource(FILE_NAME_TOPIC_TEST);

		List<String> linesTopic = singleton.readFile(fileTopic);
		List<Topic> listeTopic = (List<Topic>) singleton.listTopics(linesTopic);
		assertNotNull(listeTopic);
		assertEquals(nombre, listeTopic.size());
	}
}
