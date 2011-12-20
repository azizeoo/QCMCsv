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
import ma.norsys.formation.entities.Response;
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
	 * Méthode pour retourner la liste des questions
	 * 
	 * @param linesTopic
	 * @param linesQuestion
	 * @param linesResponse
	 * @return (List<Question>) liste des questions
	 */
	public List<Question> listQuestions(List<String> linesTopic,
			List<String> linesQuestion, List<String> linesResponse) {
		boolean indiceR = true;
		boolean indiceQ = true;
		boolean indiceT = true;
		List<Response> listeReponse = null;
		List<Question> listQuestion = new ArrayList<Question>();
		LOGGER.debug("listQuestions(List<String> " + linesTopic.size()
				+ ",List<String> " + linesQuestion.size() + ", List<String> "
				+ linesResponse.size() + ")");

		for (String lineQ : linesQuestion) {
			String[] stringsQ = lineQ.split(SEPARTOR);
			if (indiceQ) {
				indiceQ = false;
				continue;
			}
			Question question = getQuestion(stringsQ);
			// int sizeLinesResponse = linesResponse.size() - 1;
			indiceR = true;
			listeReponse = new ArrayList<Response>();
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
			indiceT = true;
			for (String lineT : linesTopic) {
				String[] stringsT = lineT.split(SEPARTOR);
				if (indiceT) {
					indiceT = false;
					continue;
				}
				Topic topic = getTopic(stringsT);
				if (question.getTopic().getIdTopic() == Long
						.valueOf(stringsT[0])) {
					question.setTopic(topic);
					break;
				}
			}
			question.setLesReponses(listeReponse);
			listQuestion.add(question);
		}

		LOGGER.debug("listQuestions() return (taille)" + listQuestion.size());
		return listQuestion;

	}

	/**
	 * Méthode pour retourner la liste des topics
	 * 
	 * @param linesTopic
	 * @return (List<Topic>) liste des topics
	 */
	public List<Topic> listTopics(List<String> linesTopic) {
		LOGGER.debug("listTopics(List<String> " + linesTopic.size() + ")");
		boolean indiceT = true;
		List<Topic> listTopic = new ArrayList<Topic>();
		for (String lineT : linesTopic) {
			String[] stringsT = lineT.split(SEPARTOR);
			if (indiceT) {
				indiceT = false;
				continue;
			}
			listTopic.add(getTopic(stringsT));
		}
		LOGGER.debug("listTopics() return (taille)" + listTopic.size());
		return listTopic;

	}

	/**
	 * Méthode pour générer un objet question à partir d'une ligne donnée
	 * 
	 * @param line
	 * @return (Question) question retournée
	 */
	private Question getQuestion(String[] line) {
		Question question = new Question();
		question.setTopic(new Topic(Long.valueOf(line[0]), null));
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
}
