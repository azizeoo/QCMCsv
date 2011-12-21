package ma.norsys.formation.csv.main;

import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_QUESTION_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_RESPONSE_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_TOPIC_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.*;

import java.io.File;
import java.util.List;

import ma.norsys.formation.csv.util.CSVSingleton;
import ma.norsys.formation.dao.IDaoQuestionnaire;
import ma.norsys.formation.dao.IDaoTopic;
import ma.norsys.formation.entities.Questionnaire;
import ma.norsys.formation.entities.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:Main.xml");
		Main p = context.getBean(Main.class);
		p.start(args);
	}

	@Autowired
	private IDaoTopic iDaoTopic;
	@Autowired
	private IDaoQuestionnaire iDaoQuestionnaire;
	
	private void start(String[] args) {
		CSVSingleton singleton = CSVSingleton.getInstance();
		
		File fileQuestion = singleton.getResource(FILE_NAME_QUESTION_MAIN);
		File fileResponse = singleton.getResource(FILE_NAME_RESPONSE_MAIN);
		File fileTopic = singleton.getResource(FILE_NAME_TOPIC_MAIN);
		File fileQuestionnaire = singleton.getResource(FILE_NAME_QUESTIONNAIRE_MAIN);
		File fileSubscriber = singleton.getResource(FILE_NAME_SUBSCRIBER_MAIN);
		
		// pour insérer les topics, les questions et leurs réponse
		if (fileQuestion.exists() && fileResponse.exists()
				&& fileTopic.exists()) {
			List<String> linesQ = singleton.readFile(fileQuestion);
			List<String> linesR = singleton.readFile(fileResponse);
			List<String> linesT = singleton.readFile(fileTopic);
			List<Topic> listeTopic = (List<Topic>) singleton.getListTopics(
					linesT, linesQ, linesR);
			for (Topic topic : listeTopic) {
				iDaoTopic.addTopic(topic);
			}
		}
		
		// pour insérer les subscriber et les questionnaires
		if (fileQuestionnaire.exists() && fileSubscriber.exists()) {
			
			List<String> linesQe = singleton.readFile(fileQuestionnaire);
			List<String> linesS = singleton.readFile(fileSubscriber);

			List<Questionnaire> listeQuestionnaire = (List<Questionnaire>) singleton.getListQuestionnaires(linesQe, linesS);
			for (Questionnaire questionnaire : listeQuestionnaire) {
				iDaoQuestionnaire.addQuestionnaire(questionnaire);
			}
		}

	}
}
