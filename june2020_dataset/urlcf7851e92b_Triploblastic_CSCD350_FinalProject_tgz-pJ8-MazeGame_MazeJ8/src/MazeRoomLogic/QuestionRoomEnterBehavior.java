package MazeRoomLogic;

import java.util.Random;

import javax.swing.SwingUtilities;

import MazeGame.Maze;
import TriviaQuestions.*;

public class QuestionRoomEnterBehavior implements MazeRoomEnterBehavior {

	private static MovieTriviaQuestionFactory questionFactory = new MovieTriviaQuestionFactory();

	@Override
	public boolean enter() {
		MovieTriviaQuestion question = questionFactory.buildQuestion();
		question.displayPrompt();
		while (!question.isSubmitted()) {
			try{
				Thread.sleep(10);
			} catch(Exception e) {
				
			}
		}
		question.closePrompt();
		boolean isCorrect = question.isCorrectAnswer();

		System.out.println("\nReturned " + isCorrect);
		return isCorrect;
	}

}
