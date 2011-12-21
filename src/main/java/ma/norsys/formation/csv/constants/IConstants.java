package ma.norsys.formation.csv.constants;

/**
 * L'interface {@link IConstants} permet de rassembler toutes les constantes
 * utilisées dans l'application.
 */
public interface IConstants {
	public static final String FILE_NAME_RESPONSE_TEST = "src/test/resources/qcm_response.csv";
	public static final String FILE_NAME_QUESTION_TEST = "src/test/resources/qcm_question.csv";
	public static final String FILE_NAME_TOPIC_TEST = "src/test/resources/qcm_topic.csv";
	public static final String FILE_NAME_QUESTIONNAIRE_TEST = "src/test/resources/qcm_questionnaire.csv";
	public static final String FILE_NAME_SUBSCRIBER_TEST = "src/test/resources/qcm_subscriber.csv";
	/**
	 * Constante qui contient le séparateur utilisé '¤' dans le fichier .csv
	 */
	public static final String SEPARTOR = "¤";
	/**
	 * Constante qui contient 'false'
	 */
	public static final String FALSE = "false";

	/**
	 * Constante qui contient le chemin relatif du fichier DB_QCM_QUESTION.csv
	 */
	public static final String FILE_NAME_QUESTION_MAIN = "src/main/resources/DB_QCM_QUESTION.csv";

	/**
	 * Constante qui contient le chemin relatif du fichier DB_QCM_RESPONSE.csv
	 */
	public static final String FILE_NAME_RESPONSE_MAIN = "src/main/resources/DB_QCM_RESPONSE.csv";
	/**
	 * Constante qui contient le chemin relatif du fichier DB_QCM_TOPIC.csv
	 */
	public static final String FILE_NAME_TOPIC_MAIN = "src/main/resources/DB_QCM_TOPIC.csv";
	/**
	 * Constante qui contient le chemin relatif du fichier DB_QCM_QUESTIONNAIRE.csv
	 */
	public static final String FILE_NAME_QUESTIONNAIRE_MAIN = "src/main/resources/DB_QCM_QUESTIONNAIRE.csv";
	/**
	 * Constante qui contient le chemin relatif du fichier DB_QCM_SUBSCRIBER.csv
	 */
	public static final String FILE_NAME_SUBSCRIBER_MAIN = "src/main/resources/DB_QCM_SUBSCRIBER.csv";

}
