package TriviaQuestions;
import java.sql.*;

/*Sets MovieTriviaQuestion prompt, answer & answerSet*/
public class MovieTriviaQuestionFactory  {
	private MovieTriviaDatabaseManager dbManager;
	
	public MovieTriviaQuestionFactory() {
		try {
			dbManager = new MovieTriviaDatabaseManager();
			dbManager.connectToDatabase();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public MovieTriviaQuestion buildQuestion() {
		
		MovieTriviaQuestion q = new MovieTriviaQuestion();
		MovieTriviaAnswerSet a = (MovieTriviaAnswerSet) q.getAnswerSet();
		MovieTriviaQuestionPrompt p = (MovieTriviaQuestionPrompt)q.getPrompt();
		
		try {  
			ResultSet rs = dbManager.getRandomTuple();
			a.setAnswer(rs.getString(3));
			setAnswerSet(a, rs);
			p.setPrompt(rs.getString(2));
			p.setRadioButtons(a.getAnswerSet());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return q;	
	}
	
	/*creates randomly ordered answerSet from what the data base query returned*/
	private static void setAnswerSet(MovieTriviaAnswerSet a, ResultSet rs) throws Exception {
		String[] answerSet = new String[4];
		for(int i = 0; i < 4; i++)
			answerSet[i] = rs.getString(i+3);
		a.setAnswerSet(answerSet);
		a.shuffleAnswerSet();
	}
	
}
