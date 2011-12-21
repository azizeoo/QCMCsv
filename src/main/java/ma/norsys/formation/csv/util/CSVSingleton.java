package ma.norsys.formation.csv.util;

import static ma.norsys.formation.csv.constants.IConstants.FALSE;
import static ma.norsys.formation.csv.constants.IConstants.SEPARTOR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ma.norsys.formation.entities.Question;
import ma.norsys.formation.entities.Questionnaire;
import ma.norsys.formation.entities.Response;
import ma.norsys.formation.entities.Subscriber;
import ma.norsys.formation.entities.Topic;

import org.apache.log4j.Logger;

/**
 * Cette classe permet le traitement des fichiers .csv
 * 
 */
public class CSVSingleton {
	// logger pour la trace d'execution
	static final Logger LOGGER = Logger.getLogger(CSVSingleton.class);
	private static CSVSingleton instance = null;

	public static CSVSingleton getInstance() {
		if (null == instance) {
			instance = new CSVSingleton();

		}
		return instance;
	}

	/**
	 * Méthode pour lecture d'un fichier .csv ligne par ligne
	 * 
	 * @param file
	 * @return (List<String>) une liste des lignes
	 */
	public List<String> readFile(File file) {

		List<String> result = new ArrayList<String>();

		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			for (String line = bufferedReader.readLine(); null != line; line = bufferedReader
					.readLine()) {
				result.add(line);
			}
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			LOGGER.debug("FileNotFoundException " + e.getMessage());
		} catch (IOException e) {
			LOGGER.debug("IOException " + e.getMessage());
		}

		return result;
	}

	/**
	 * Méthode pour recuperer le chemin du fichier .csv
	 * 
	 * @param fileName
	 * @return (File) chemin du fichier
	 */
	public File getResource(String fileName) {
		LOGGER.debug("getResource(String " + fileName + ")");
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		LOGGER.debug("getResource() return " + file + "");
		return file;
	}

	/**
	 * Méthode pour retourner la liste des topics
	 * 
	 * @param linesTopic
	 * @return (List<Topic>) liste des topics
	 */

	public List<Topic> getListTopics(List<String> linesTopic,
			List<String> linesQuestion, List<String> linesResponse) {
		LOGGER.debug("getListTopics(List<String> " + linesTopic.size()
				+ ",List<String> " + linesQuestion.size() + ", List<String> "
				+ linesResponse.size() + ")");
		boolean indiceT = true;
		List<Question> listQuestion = null;
		List<Topic> listTopic = new ArrayList<Topic>();
		for (String lineT : linesTopic) {
			String[] stringsT = lineT.split(SEPARTOR);
			if (indiceT) {
				indiceT = false;
				continue;
			}
			Topic topic = getTopic(stringsT);
			listQuestion = getListQuestionsByTopic(topic, linesQuestion,
					linesResponse);
			topic.setLesQuestions(listQuestion);
			listTopic.add(topic);
		}

		LOGGER.debug("getListTopics() return (taille)" + listQuestion.size());
		return listTopic;

	}
	/**
	 * Méthode pour retourner la liste des questionnaires
	 * 
	 * @param linesTopic
	 * @return (List<Questionnaire>) liste des questionnaire
	 */

	public List<Questionnaire> getListQuestionnaires(List<String> linesQuestionnaire, List<String> linesSubscriber){
		LOGGER.debug("getListQuestionnaires(List<String> " + linesQuestionnaire.size()+ ",List<String> " + linesSubscriber.size() + ")");
		boolean indiceQ = true;
		List<Subscriber> listSubscriber = null;
		List<Questionnaire> listQuestionnaires = new ArrayList<Questionnaire>();
		for (String lineQ : linesQuestionnaire) {
			String[] stringsQ = lineQ.split(SEPARTOR);
			if (indiceQ) {
				indiceQ = false;
				continue;
			}
			Questionnaire questionnaire = getQuestionnaire(stringsQ);
			listSubscriber = getListSubscribersByQuestionnaire(questionnaire, linesSubscriber);
			questionnaire.setSubscribers(listSubscriber);
			listQuestionnaires.add(questionnaire);
		}
		LOGGER.debug("getListQuestionnaires() return (taille)" + listQuestionnaires.size());
		return listQuestionnaires;
		
	}
	/**
	 * Méthode pour générer un objet question à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Question) question retournée
	 */
	private Question getQuestion(String[] line) {
		Question question = new Question();
		question.setIdQuestion(Long.valueOf(line[1]));
		question.setLibelle(line[2]);
		LOGGER.debug("taille " + line.length);
		return question;
	}

	/**
	 * Méthode pour générer un objet réponse à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Response) réponse retournée
	 */
	private Response getResponse(String[] line) {
		Response response = new Response();
		response.setIdR(Long.valueOf(line[1]));
		response.setLibelle(line[2]);
		response.setCorrect(FALSE.equals(line[3]) ? false : true);
		LOGGER.debug("taille " + line.length);
		return response;
	}

	/**
	 * Méthode pour générer un objet topic à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Topic) topic retournée
	 */
	private Topic getTopic(String[] line) {
		Topic topic = new Topic();
		topic.setIdTopic(Long.valueOf(line[0]));
		topic.setLibelle(line[1]);
		LOGGER.debug("taille " + line.length);
		return topic;
	}
	/**
	 * Méthode pour générer un objet questionnaire à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Questionnaire) questionnaire retourné
	 */
	private Questionnaire getQuestionnaire(String[] line) {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setIdQuestionnaire(Long.valueOf(line[0]));
		questionnaire.setTopic(new Topic(Long.valueOf(line[1]), null));
		LOGGER.debug("taille " + line.length);
		return questionnaire;
	}
	/**
	 * Méthode pour générer un objet Subscriber à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Subscriber) subscriber retourné
	 */
	private Subscriber getSubscriber(String[] line) {
		Subscriber subscriber = new Subscriber();
		subscriber.setIdSubscriber(Long.valueOf(line[1]));
		subscriber.setFirstName(line[2]);
		subscriber.setLastName(line[3]);
		subscriber.setEmail(line[4]);
		LOGGER.debug("taille " + line.length);
		return subscriber;
	}	
	/**
	 * Méthode pour récupérer le chemin absolu d'un fichier .csv
	 * 
	 * @param fileName
	 * @return (String) chemin absolu
	 */
	private String getResourcePath(String fileName) {
		LOGGER.debug("getResourcePath(String " + fileName + ")");
		final File file = new File("");
		final String dossierPath = file.getAbsolutePath() + File.separator
				+ fileName;
		LOGGER.debug("getResourcePath() return " + dossierPath + "");
		return dossierPath;
	}

	/**
	 * Méthode permet de récuperer la liste des réponses par question
	 * 
	 * @param question
	 * @param linesResponse
	 * @return (List<Response>) liste des réponses
	 */
	private List<Response> getListReponsesByQuestion(Question question,
			List<String> linesResponse) {
		List<Response> listeReponse = new ArrayList<Response>();
		boolean indiceR = true;
		for (String lineR : linesResponse) {
			String[] stringsR = lineR.split(SEPARTOR);
			if (indiceR) {
				indiceR = false;
				continue;
			}
			if (question.getIdQuestion() == Long.valueOf(stringsR[0])) {
				listeReponse.add(getResponse(stringsR));
			}
		}
		return listeReponse;
	}

	/**
	 * Méthode permet de récuperer la liste des questions par topic
	 * 
	 * @param topic
	 * @param linesQuestion
	 * @param linesResponse
	 * @return (List<Question>) liste des question
	 */
	private List<Question> getListQuestionsByTopic(Topic topic,
			List<String> linesQuestion, List<String> linesResponse) {
		List<Response> listeReponse;
		List<Question> listeQuestion = new ArrayList<Question>();
		boolean indiceQ = true;
		for (String lineQ : linesQuestion) {
			String[] stringsQ = lineQ.split(SEPARTOR);
			if (indiceQ) {
				indiceQ = false;
				continue;
			}
			Question question = getQuestion(stringsQ);
			listeReponse = getListReponsesByQuestion(question, linesResponse);
			if (topic.getIdTopic() == Long.valueOf(stringsQ[0])) {
				question.setLesReponses(listeReponse);
				listeQuestion.add(question);
			}
		}
		return listeQuestion;
	}
	/**
	 * Méthode permet de récuperer la liste des Subscriber par questionnaire
	 * 
	 * @param questionnaire
	 * @param linesSubscriber
	 * @return (List<Subscriber>) liste des Subscribers
	 */
	private List<Subscriber> getListSubscribersByQuestionnaire(Questionnaire questionnaire, List<String> linesSubscriber) {
		List<Subscriber> listeSubscriber = new ArrayList<Subscriber>();
		boolean indiceS = true;
		for (String lineS : linesSubscriber) {
			String[] stringsS = lineS.split(SEPARTOR);
			if (indiceS) {
				indiceS = false;
				continue;
			}
			if (questionnaire.getIdQuestionnaire() == Long.valueOf(stringsS[0])) {
				listeSubscriber.add(getSubscriber(stringsS));
			}
		}
		return listeSubscriber;
	}
	
}
