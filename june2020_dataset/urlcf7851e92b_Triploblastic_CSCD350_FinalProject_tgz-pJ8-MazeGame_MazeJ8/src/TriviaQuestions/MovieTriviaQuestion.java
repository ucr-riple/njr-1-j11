package TriviaQuestions;

import java.awt.Dialog.ModalityType;
import java.util.Random;

import MazeGame.Maze;

public class MovieTriviaQuestion {
	private MovieTriviaQuestionPrompt prompt = null;
	private MovieTriviaAnswerSet answerSet = null;
	
	public MovieTriviaQuestion() {
		this.prompt = new MovieTriviaQuestionPrompt();
		this.answerSet = new MovieTriviaAnswerSet();
	}
	
	public MovieTriviaQuestionPrompt getPrompt() {return this.prompt;}
	public MovieTriviaAnswerSet getAnswerSet() {return this.answerSet;}

	public void setPrompt(MovieTriviaQuestionPrompt prompt) {this.prompt = prompt;}
	public void setAnswerSet(MovieTriviaAnswerSet answerSet) {this.answerSet = answerSet;}

	public void displayPrompt() {
		try {
			prompt.setAlwaysOnTop(true);
			//prompt.setModalityType(ModalityType.DOCUMENT_MODAL);
			prompt.setVisible(true);
			prompt.setTitle("Answer this!");
			
			/*Maze.mainWindow.setContentPane(prompt.getContentPane());
			Maze.mainWindow.repaint();
			System.out.println("Calling thread: " + Thread.currentThread());*/
		} 
		catch (Exception e) {e.printStackTrace();}
	}
	
	public void closePrompt() {
		prompt.dispose();
		/*Maze.getInstance().setBoardContent();*/
		
	}
	
	public boolean isSubmitted() {return prompt.isSubmitted();}

	public boolean isCorrectAnswer() {
		return answerSet.getAnswer().equals(prompt.getSelection());
	}
	
}
