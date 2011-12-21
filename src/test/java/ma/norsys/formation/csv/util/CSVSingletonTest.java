package ma.norsys.formation.csv.util;

import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_QUESTION_TEST;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_RESPONSE_TEST;
import static ma.norsys.formation.csv.constants.IConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import ma.norsys.formation.entities.Questionnaire;
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
	public void getListTopicsTest() throws Exception {
		// Param
		final int nombre = 1;
		final File fileQuestion = singleton
				.getResource(FILE_NAME_QUESTION_TEST);
		final File fileResponse = singleton
				.getResource(FILE_NAME_RESPONSE_TEST);
		final File fileTopic = singleton.getResource(FILE_NAME_TOPIC_TEST);
		List<String> linesQuestion = singleton.readFile(fileQuestion);
		List<String> linesResponse = singleton.readFile(fileResponse);
		List<String> linesTopic = singleton.readFile(fileTopic);
		List<Topic> listeTopic = (List<Topic>) singleton.getListTopics(
				linesTopic, linesQuestion, linesResponse);
		assertNotNull(listeTopic);
		assertEquals(nombre, listeTopic.size());
	}

	@Test
	public void getListQuestionnairesTest() throws Exception {
		// Param
		final int nombre = 1;
		final File fileQuestionnaire = singleton
				.getResource(FILE_NAME_QUESTIONNAIRE_TEST);
		final File fileSubscriber = singleton
				.getResource(FILE_NAME_SUBSCRIBER_TEST);

		List<String> linesQuestionnaire = singleton.readFile(fileQuestionnaire);
		List<String> linesSubscriber = singleton.readFile(fileSubscriber);

		List<Questionnaire> listeQuestionnaire = (List<Questionnaire>) singleton
				.getListQuestionnaires(linesQuestionnaire, linesSubscriber);
		assertNotNull(listeQuestionnaire);
		assertEquals(nombre, listeQuestionnaire.size());
	}
}
