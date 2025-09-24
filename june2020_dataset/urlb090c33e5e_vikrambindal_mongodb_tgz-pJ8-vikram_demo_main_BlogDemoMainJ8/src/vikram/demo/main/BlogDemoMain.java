package vikram.demo.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vikram.demo.dao.AuthorDAOImpl;
import vikram.demo.dao.PostCommentsDAOImpl;
import vikram.demo.dao.PostDAOImpl;
import vikram.demo.domain.Author;
import vikram.demo.domain.Post;
import vikram.demo.domain.PostComments;
import vikram.demo.factory.MongoDBFactory;

public class BlogDemoMain {

	public static void main(String[] args) {

		//Init and create DB
		MongoDBFactory mongoDBFactory = MongoDBFactory.getInstance();
		
		AuthorDAOImpl authorDAO = new AuthorDAOImpl(mongoDBFactory);
		PostDAOImpl postDAO = new PostDAOImpl(mongoDBFactory);
		PostCommentsDAOImpl postCommentsDAO = new PostCommentsDAOImpl(mongoDBFactory);
		
		System.out.println("Creating Author");
		Author author = new Author("Vikram", "Bindal", "vikrambindal@gmail.com", "I LOVEEEE MONGODB" );
		authorDAO.createAuthor(author);
		
		System.out.println("Finding Author");
		Author searchedAuthor = authorDAO.findAuthorByFirstName("ikram");
		System.out.println(searchedAuthor);
		
		//Create a post
		List<String> tagList = new ArrayList<String>();
		tagList.add("enjoy");
		Post post = new Post("My First Post", "This is awesome. I am loving it", new Date(), searchedAuthor.getId(), tagList);
		postDAO.createPost(post);
		tagList = new ArrayList<String>();
		tagList.add("awesome");
		tagList.add("enjoy");
		Post secondPost = new Post("My Second Post", "This is awesome. I am loving it", new Date(), searchedAuthor.getId(), tagList);
		postDAO.createPost(secondPost);
		tagList = new ArrayList<String>();
		tagList.add("Ma");
		Post thirdPost = new Post("My Third Post", "Look Ma, NO SQL", new Date(), searchedAuthor.getId(), tagList);
		postDAO.createPost(thirdPost);
		
		
		//Searching for a post
		System.out.println("Searching Post");
		Post searchPost = postDAO.findPostByTitle("First");
		System.out.println(searchPost);
		
		//Create a comment for the post
		PostComments comments = new PostComments("John", "john@test.com", "Cool man this rocks !!!", new Date(), searchPost.getId());
		postCommentsDAO.createComment(comments);
		
		comments = new PostComments("Nicolas", "nicolas@test.com", "Awesomeeeeeeeee", new Date(), searchPost.getId());
		postCommentsDAO.createComment(comments);
		
		System.out.println("Searching Post");
		searchPost = postDAO.findPostByTitle("Third");
		System.out.println(searchPost);
		
		comments = new PostComments("Kathy", "kathy@test.com", "Thank you, this is what I was looking for :)", new Date(), searchPost.getId());
		postCommentsDAO.createComment(comments);
		
		long numOfPost = postDAO.countPostByAuthor(searchedAuthor.getId());
		System.out.println("Number of Post: " + numOfPost);
		System.out.println("Posts");
		List<Post> authorPost = postDAO.findPostByAuthor(searchedAuthor.getId());
		for (Post post2 : authorPost) {
			System.out.println(post2);
			List<PostComments> postComments = postCommentsDAO.findCommentsByPost(post2);
			System.out.println("Comments : " + postComments.size());
			for (PostComments postComments2 : postComments) {
				System.out.println(postComments2);
			}
		}
		
		mongoDBFactory.dropDB(Constants.DB_NAME);
	}

}
