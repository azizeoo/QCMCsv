package ma.norsys.formation.csv.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import ma.norsys.formation.entities.Question;

import org.junit.Before;
import org.junit.Test;
import static ma.norsys.formation.csv.constants.IConstants.*;

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
		final int nombreLigne = 4;

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
		final File fileQuestion = singleton.getResource(FILE_NAME_QUESTION_TEST);
		final File fileResponse = singleton.getResource(FILE_NAME_RESPONSE_TEST);
		List<String> linesQuestion = singleton.readFile(fileQuestion);
		List<String> linesResponse = singleton.readFile(fileResponse);
		List<Question> listeQuestion = (List<Question>)singleton.listQuestions(linesQuestion,linesResponse);
		assertNotNull(listeQuestion);
		assertEquals(nombre, listeQuestion.size());
	}

}
