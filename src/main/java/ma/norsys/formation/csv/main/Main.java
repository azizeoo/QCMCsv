package ma.norsys.formation.csv.main;

import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_RESPONSE_MAIN;
import static ma.norsys.formation.csv.constants.IConstants.FILE_NAME_QUESTION_MAIN;

import java.io.File;
import java.util.List;

import ma.norsys.formation.csv.util.CSVSingleton;
import ma.norsys.formation.dao.IDaoQuestion;
import ma.norsys.formation.dao.Impl.DaoQuestionImpl;
import ma.norsys.formation.entities.Question;

public class Main {
	private static CSVSingleton singleton;
	private static IDaoQuestion iDaoQuestion;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		singleton = CSVSingleton.getInstance();
		iDaoQuestion = new DaoQuestionImpl();
		File fileQuestion = singleton.getResource(FILE_NAME_QUESTION_MAIN);
		File fileResponse = singleton.getResource(FILE_NAME_RESPONSE_MAIN);
		if(fileQuestion.exists() && fileResponse.exists()){
			List<String> linesQ = singleton.readFile(fileQuestion);
			List<String> linesR = singleton.readFile(fileResponse);
			List<Question> listeQuestion = (List<Question>)singleton.listQuestions(linesQ,linesR);
			
			for(Question question: listeQuestion){
				iDaoQuestion.addQuestion(question);
			}
		}
		
	}

}
