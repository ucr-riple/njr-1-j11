package com.moblima.presentation;

import com.moblima.businesslogic.MovieBL;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType;
import com.moblima.util.ConsoleReader;

import java.util.Scanner;

public class MovieListing_CLI {
	private MovieBL movieBL;
	
	public MovieListing_CLI(){
		movieBL = new MovieBL();
	}
	
	public void createMovie(){
		int choice;
		String title,details,language, cast, director, opening, runtime;
		MovieRating rating=null;
		MovieType type=null;
		MovieStatus status=null;
		char isBlockbuster;
		boolean blockBuster;
		
		System.out.println();
		System.out.println("Create Movie");
		System.out.println("------------");
		System.out.print("Enter the Movie Title : ");
		title = ConsoleReader.readString();
		System.out.println();
		
		System.out.println("Movie Type");
		System.out.println("----------");
		System.out.println("1) DIGITAL");
		System.out.println("2) 3D");
		System.out.print("Choose the Movie Type: ");
		choice = ConsoleReader.readIntInput();
		if(choice>2 || choice<1){
			System.out.println("Error invalid choice!");
			return;
		}
		System.out.println();
		
		switch(choice){
			case 1:
				type = MovieType.DIGITAL;
				break;
				
			case 2:
				type = MovieType._3D;
				break;
		}
		
		System.out.println("Is this a Blockbuster movie? (Y/N)");
		isBlockbuster = ConsoleReader.readChar();
		if(isBlockbuster == 'Y')
			blockBuster = true;
		else if(isBlockbuster == 'N')
			blockBuster = false;
		else{
			System.out.println("Invalid Option!");
			return;
		}
		
		System.out.println("Movie Rating");
		System.out.println("------------");
		System.out.println("1) G");
		System.out.println("2) PG");
		System.out.println("3) PG13");
		System.out.println("4) NC16");
		System.out.println("5) M18");
		System.out.println("6) R21");
		System.out.print("Choose the Movie Rating: ");
		choice = ConsoleReader.readIntInput();
		if(choice>6 || choice<1){
			System.out.println("Invalid Choice!");
			return;
		}
		System.out.println();
		
		switch(choice){
			case 1:
				rating = MovieRating.G;
				break;
			
			case 2:
				rating = MovieRating.PG;
				break;
			
			case 3:
				rating = MovieRating.PG13;
				break;
			
			case 4:
				rating = MovieRating.NC16;
				break;
			
			case 5:
				rating = MovieRating.M18;
				break;
			
			case 6:
				rating = MovieRating.R21;
				break;
		}
		
		System.out.println("Movie Status");
		System.out.println("------------");
		System.out.println("1) COMING SOON");
		System.out.println("2) PREVIEW");
		System.out.println("3) NOW SHOWING");
		System.out.println("4) END OF SHOWING");
		System.out.print("Choose the Movie Status: ");
		choice = ConsoleReader.readIntInput();
		if(choice>4 || choice<1){
			System.out.println("Invalid Choice!");
			return;
		}
		System.out.println();
		
		switch(choice){
			case 1:
				status = MovieStatus.COMING_SOON;
				break;
				
			case 2:
				status = MovieStatus.PREVIEW;
				break;
				
			case 3:
				status = MovieStatus.NOW_SHOWING;
				break;
				
			case 4:
				status = MovieStatus.END_OF_SHOWING;
				break;
		}
		
		System.out.println("Enter the Movie Details: ");
		details = ConsoleReader.readString();
		System.out.print("Enter the Movie Language: ");
		language = ConsoleReader.readString();
		System.out.println("Enter the Movie Cast: ");
		cast = ConsoleReader.readString();
		System.out.print("Enter the Movie Director: ");
		director = ConsoleReader.readString();
		System.out.print("Enter the Movie Opening: ");
		opening = ConsoleReader.readString();
		System.out.print("Enter the Movie Runtime: ");
		runtime = ConsoleReader.readString();
		System.out.println();
		movieBL.createMovie(title, rating, type, blockBuster, status, language, cast, director, opening, runtime, details);
	}
	
	public void updateMovie(){
		int choice=0,choice2=0;
		int choice3, movieCode;
		String title,details,language, cast, director, opening, runtime;
		MovieRating rating=null;
		MovieType type=null;
		MovieStatus status=null;
		char isBlockbuster;
		boolean blockBuster;
		
		for(Movie m: movieBL.getMovies()){
			System.out.println(m.getMovieCode() + " " + m.getTitle());
		}
		
		System.out.println();
		System.out.println("Update Movie");
		System.out.println("------------");
		System.out.print("Enter the MovieCode (-1 to go back) : ");
		choice3 = ConsoleReader.readIntInput();
		System.out.println();
		if(choice3 == -1){
			return;
		}
		
		Movie movie = movieBL.getMovie(choice3);
		if(movie == null){
			System.out.println("Movie does not exist!");
			return;
		}
		movieCode = movie.getMovieCode();
		title = movie.getTitle();
		rating = movie.getRating();
		type = movie.getType();
		blockBuster = movie.isBlockBuster();
		status = movie.getStatus();
		language = movie.getLanguage();
		cast = movie.getCast();
		director = movie.getDirector();
		opening = movie.getOpening();
		runtime = movie.getRunTime();
		details = movie.getDetails();
		
		do {
			System.out.println(" 1) Movie Code :"+ movieCode);
			System.out.println(" 2) Movie Title :"+ title);
			System.out.println(" 3) Movie Type :"+ type);
			System.out.println(" 4) Blockbuster: " + blockBuster);
			System.out.println(" 5) Movie Rating :"+ rating);
			System.out.println(" 6) Movie Status :"+ status);
			System.out.println(" 7) Movie Details :"+ details);
			System.out.println(" 8) Movie Language :"+ language);
			System.out.println(" 9) Movie Cast :"+ cast);
			System.out.println("10) Movie Director :"+ director);
			System.out.println("11) Movie Opening :"+ opening);
			System.out.println("12) Movie Runtime :"+ runtime);
			System.out.println("13) Save Changes");
			System.out.print("Choose the attribute you want to update: ");
			choice = ConsoleReader.readIntInput();
			System.out.println();
			
			switch(choice){
				case 1:
					System.out.println("Enter the Movie Code : ");
					movieCode = ConsoleReader.readIntInput();
					System.out.println();
					break;
					
				case 2:
					System.out.println("Enter the Movie Title : ");
					title = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 3:
					System.out.println("Movie Type");
					System.out.println("----------");
					System.out.println("1) DIGITAL");
					System.out.println("2) 3D");
					System.out.print("Choose the Movie Type: ");
					choice2 = ConsoleReader.readIntInput();
					if(choice2>2 || choice2<1){
						System.out.println("Invalid Choice!");
						break;
					}
					System.out.println();
					
					switch(choice2){
						case 1:
							type = MovieType.DIGITAL;
							break;
							
						case 2:
							type = MovieType._3D;
							break;
					}
					break;
					
				case 4:
					System.out.println("Is this a Blockbuster movie? (Y/N)");
					isBlockbuster = ConsoleReader.readChar();
					if(isBlockbuster == 'Y')
						blockBuster = true;
					else if(isBlockbuster == 'N')
						blockBuster = false;
					else
						System.out.println("Invalid Option!");
					break;
					
				case 5:
					System.out.println("Movie Rating");
					System.out.println("------------");
					System.out.println("1) G");
					System.out.println("2) PG");
					System.out.println("3) PG13");
					System.out.println("4) NC16");
					System.out.println("5) M18");
					System.out.println("6) R21");
					System.out.print("Choose the Movie Rating: ");
					choice2 = ConsoleReader.readIntInput();
					if(choice2>6 || choice2<1){
						System.out.println("Invalid Choice!");
						break;
					}
					System.out.println();
					
					switch(choice2){
						case 1:
							rating = MovieRating.G;
							break;
							
						case 2:
							rating = MovieRating.PG;
							break;
							
						case 3:
							rating = MovieRating.PG13;
							break;
							
						case 4:
							rating = MovieRating.NC16;
							break;
							
						case 5:
							rating = MovieRating.M18;
							break;
							
						case 6:
							rating = MovieRating.R21;
							break;
					}
					break;
					
				case 6:
					System.out.println("Movie Status");
					System.out.println("------------");
					System.out.println("1) COMING SOON");
					System.out.println("2) PREVIEW");
					System.out.println("3) NOW SHOWING");
					System.out.println("4) END OF SHOWING");
					System.out.print("Choose the Movie Status: ");
					choice2 = ConsoleReader.readIntInput();
					if(choice2>4 || choice2<1){
						System.out.println("Invalid Choice!");
						break;
					}
					System.out.println();
					
					switch(choice2){
						case 1:
							status = MovieStatus.COMING_SOON;
							break;
							
						case 2:
							status = MovieStatus.PREVIEW;
							break;
							
						case 3:
							status = MovieStatus.NOW_SHOWING;
							break;
							
						case 4:
							status = MovieStatus.END_OF_SHOWING;
							break;
					}
					break;
					
				case 7:
					System.out.println("Enter the Movie Details : ");
					details = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 8:
					System.out.print("Enter the Movie Language : ");
					language = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 9:
					System.out.println("Enter the Movie Cast : ");
					cast = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 10:
					System.out.print("Enter the Movie Director: ");
					director = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 11:
					System.out.print("Enter the Movie Opening: ");
					opening = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 12:
					System.out.print("Enter the Movie Runtime: ");
					runtime = ConsoleReader.readString();
					System.out.println();
					break;
					
				case 13:
					System.out.println("Changes Saved!");
					System.out.println();
					break;
					
				default :
					System.out.println("Invalid choice");
					break;
			}
		}while(choice != 13);
		choice = 0;
		choice2 = 0;
		movieBL.updateMovie(movieCode,title,rating,type, blockBuster, status,language,cast,director,opening,runtime,details);
		System.out.println();
	}
	
	public void removeMovie(){
		int movieCode;
		
		for(Movie m: movieBL.getMovies()){
			System.out.println(m.getMovieCode() + " " + m.getTitle());
		}
		System.out.println();
		System.out.println("Remove Movie");
		System.out.println("------------");
		System.out.print("Enter the MovieCode of the movie to be removed (-1 to go back) : ");
		movieCode = ConsoleReader.readIntInput();
		
		if(movieCode == -1) {
			return;
		}
		
		System.out.println();
		movieBL.removeMovie(movieCode);
	}
	
	public void show(){
		Scanner scan = new Scanner(System.in);
		int choice=0;
		
		while(choice!=4){
			System.out.println("==============");
			System.out.println("  Movie Menu  ");
			System.out.println("==============");
			System.out.println("1) Create Movie");
			System.out.println("2) Update Movie");
			System.out.println("3) Remove Movie");
			System.out.println("4) Back");
			System.out.print("Please enter your choice : ");
			choice = scan.nextInt();
			if(choice>4 || choice<1){
				System.out.println("Invalid choice");
				continue;
			}
					
			switch(choice){
				case 1:
					createMovie();
					break;
					
				case 2:
					updateMovie();
					break;
					
				case 3:
					removeMovie();
					break;
					
				case 4:
					return;
			}
		}
	}
	public static void main(String[] args) {
		MovieListing_CLI cli = new MovieListing_CLI();
		cli.show();
	}
}
