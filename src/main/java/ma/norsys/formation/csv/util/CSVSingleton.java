package ma.norsys.formation.csv.util;

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

import org.apache.log4j.Logger;
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
	 * Méthode pour lecture d'un fichier
	 * 
	 * @param file
	 * @return
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

	public File getResource(String fileName) {
		LOGGER.debug("getResource(String " + fileName + ")");
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		LOGGER.debug("getResource() return " + file + "");
		return file;
	}

	public List<Question> listQuestions(List<String> linesQuestion,
			List<String> linesResponse) {
		int indiceR = -1;
		int indiceQ = -1;
		List<Response> listeReponse = null;
		List<Question> listQuestion = new ArrayList<Question>();
		LOGGER.debug("listQuestions(List<String> " + linesQuestion.size()
				+ ",List<String> " + linesResponse.size() + ")");
		int sizeLinesQuestion = linesQuestion.size()-1;
		for (String lineQ : linesQuestion) {
			String[] stringsQ = lineQ.split(SEPARTOR);
			indiceQ++;
			if (0 == indiceQ || sizeLinesQuestion == indiceQ) {
				continue;
			}
			Question question = getQuestion(stringsQ);
			int sizeLinesResponse = linesResponse .size()-1;
			indiceR = -1;
			listeReponse = new ArrayList<Response>();
			for (String lineR : linesResponse) {
				String[] stringsR = lineR.split(SEPARTOR);
				indiceR++;
				if (0 == indiceR || sizeLinesResponse== indiceR) {
					continue;
				}
				if (question.getIdQuestion() == Long.valueOf(stringsR[0])) {
					listeReponse.add(getResponse(stringsR));
				}
			}
			question.setLesReponses(listeReponse);
			listQuestion.add(question);
		}
		LOGGER.debug("listQuestions() return (taille)" + listQuestion.size());
		return listQuestion;

	}

	private static Question getQuestion(String[] line) {
		Question question = new Question();
		question.setIdQuestion(Long.valueOf(line[0]));
		question.setLibelle(line[1]);
		LOGGER.debug("taille " + line.length);
		return question;
	}

	private static Response getResponse(String[] line) {
		Response response = new Response();
		response.setIdR(Long.valueOf(line[1]));
		response.setLibelle(line[2]);
		response.setCorrect("0".equals(line[3]) ? false:true);
		LOGGER.debug("taille " + line.length);
		return response;
	}

	private static String getResourcePath(String fileName) {
		LOGGER.debug("getResourcePath(String " + fileName + ")");
		final File file = new File("");
		final String dossierPath = file.getAbsolutePath() + File.separator
				+ fileName;
		LOGGER.debug("getResourcePath() return " + dossierPath + "");
		return dossierPath;
	}
}
