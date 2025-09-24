package TriviaQuestions;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieTriviaQuestionFactory factory = new MovieTriviaQuestionFactory();
		MovieTriviaQuestion question = factory.buildQuestion();
		question.displayPrompt();
		while(!question.isSubmitted()) {
			System.out.println("listening");
		}
		System.out.println("The submitted answer is: " + question.isCorrectAnswer());
	}

}
