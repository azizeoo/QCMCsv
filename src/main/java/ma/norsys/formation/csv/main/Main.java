package ma.norsys.formation.csv.main;

import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_QUESTION_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_RESPONSE_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_TOPIC_MAIN;

import java.io.File;
import java.util.List;

import ma.norsys.formation.csv.util.CSVSingleton;
import ma.norsys.formation.dao.DaoTopicImpl;
import ma.norsys.formation.dao.IDaoTopic;
import ma.norsys.formation.entities.Topic;

public class Main {
	private static CSVSingleton singleton;
	private static IDaoTopic iDaoTopic;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		singleton = CSVSingleton.getInstance();
		iDaoTopic = new DaoTopicImpl();
		File fileQuestion = singleton.getResource(FILE_NAME_QUESTION_MAIN);
		File fileResponse = singleton.getResource(FILE_NAME_RESPONSE_MAIN);
		File fileTopic = singleton.getResource(FILE_NAME_TOPIC_MAIN);
//		if (fileTopic.exists()) {
//			List<String> linesT = singleton.readFile(fileTopic);
			// List<Topic> listeTopic = (List<Topic>)
			// singleton.listTopics(linesT);
			// for (Topic topic : listeTopic) {
			// iDaoQuestion.addTopic(topic);
			// }
//		}
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

	}

}
