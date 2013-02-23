package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.Person;

/**
  * A class to wrap some data manipulation and querying tasks for the application
  */
public class MovieExplorer {

	/**
	 * This method creates all necessary tables 
	 *
	 * @param conn the Object representing the connection
	 * @throws SQLException if something goes wrong
	 */
	public static void createSchema(Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		String movie = "CREATE TABLE movie("
		      		   +"id INTEGER NOT NULL PRIMARY KEY,"
		     		   +"title VARCHAR(255) NOT NULL,"
		      		   +"\"year\" INTEGER)";
		  
		String person = "CREATE TABLE person("
		     		 	+"id INTEGER NOT NULL PRIMARY KEY,"
		      			+"name VARCHAR(255) NOT NULL,"
		      			+"gender CHAR(1) CHECK (gender IN ('f', 'm')),"
		      			+"birthday CHAR(10))";
		  
		String actor =	"CREATE TABLE actor("
		      			+"person INTEGER NOT NULL REFERENCES person,"
		      			+"movie INTEGER NOT NULL REFERENCES movie,"
		      			+"role VARCHAR(255) NOT NULL,"
		      			+"PRIMARY KEY (person, movie))";
		st.executeUpdate(movie);
		st.executeUpdate(person);
		st.executeUpdate(actor);
	}
	
	private PreparedStatement insertMovieStmt;
	private PreparedStatement insertPersonStmt;
	private PreparedStatement insertActorStmt;
	private PreparedStatement getActorsToAMovieStmt;
	private PreparedStatement getMoviesBetweenYearsStmt;
	
	// TODO Exercise 12.1e declare prepared statements here 
	// TODO Exercise 12.1f declare prepared statements here 
	
	public MovieExplorer(Connection conn) throws SQLException {
		if(conn == null) {
			throw new IllegalArgumentException("Connection must not be null");
		}
		
		/*
		 * Tries to create neccesary tables, outputs
		 */
		try {
			MovieExplorer.createSchema(conn);
		} catch (SQLException ex) {
			System.err.println("Seems like the tables already exist.");
		}

		insertMovieStmt = conn.prepareStatement("INSERT INTO movie(id, title, \"year\") VALUES (?, ?, ?)");
		getMoviesBetweenYearsStmt = conn.prepareStatement("SELECT id, title, \"year\" FROM movie WHERE \"year\" >= ? AND \"year\" <= ?");
		
		/*
		 * Needed for person and actor insertion
		 */
		insertPersonStmt = conn.prepareStatement("INSERT INTO person(id, name, gender, birthday) VALUES (?, ?, ?, ?)");
		insertActorStmt = conn.prepareStatement("INSERT INTO actor(person, movie, role) VALUES (?, ?, ?)");

		/*
		 * Needed for getActorsToAMovie
		 */
		getActorsToAMovieStmt = conn.prepareStatement("SELECT p.id, p.name, p.gender, p.birthday FROM person p, actor a WHERE a.movie = ? AND p.id = a.person");
	}
	/
	public void insertMovie(Movie movie) throws SQLException {
		insertMovieStmt.setInt(1, movie.getId());
		insertMovieStmt.setString(2, movie.getTitle());
		insertMovieStmt.setInt(3, movie.getYear());
		insertMovieStmt.executeUpdate();
	}


	public List<Movie> getMoviesBetweenYears(int yearFrom, int yearTo) throws SQLException {
		List<Movie> movies = new ArrayList<Movie>();
		
		getMoviesBetweenYearsStmt.setInt(1, yearFrom);
		getMoviesBetweenYearsStmt.setInt(2, yearTo);
		ResultSet moviesResultSet = getMoviesBetweenYearsStmt.executeQuery();
		while(moviesResultSet.next()) {
			int id = moviesResultSet.getInt(1);
			String title = moviesResultSet.getString(2);
			int year = moviesResultSet.getInt(3);
			movies.add(new Movie(id, title, year));
		}
		
		return movies;
	}
	
	/**
	 * Inserts a new Person into the database
	 *
	 * @param person the object representing the Person to be inserted.
	 * @throws SQLException if something goes wrong
	 */
	public void insertPerson(Person person) throws SQLException {
		insertPersonStmt.setInt(1, person.getId());
		insertPersonStmt.setString(2, person.getName());
		insertPersonStmt.setString(3, person.getGender());
		insertPersonStmt.setString(4, person.getBirthday());
		insertPersonStmt.executeUpdate();
	}
	
	/**
	 * Inserts a new actor into the database
	 *
	 * @param personId the referencing id of the corresponding person.§
	 * @param movieId the referencing id of the movie in question.
	 * @param role the role the actor played in the movie
	 * @throws SQLException if something goes wrong
	 */
	public void insertActorTuple(int personId, int movieId, String role) throws SQLException {
		insertActorStmt.setInt(1, personId);
		insertActorStmt.setInt(2, movieId);
		insertActorStmt.setString(3, role);
		insertActorStmt.executeUpdate();
	}
	
	/**
	 * Get a list of all person who had been an actor in a certain movie.
	 *
	 * @param movieId the id of the movie in question
	 * @return The list of all persons.
	 * @throws SQLException if something goes wrong
	 */
	public List<Person> getActorsToAMovie(int movieId) throws SQLException {
		List<Person> persons = new ArrayList<Person>();
		
		getActorsToAMovieStmt.setInt(1, movieId);
		ResultSet personsResultSet = getActorsToAMovieStmt.executeQuery();

		while(personsResultSet.next()) {
			int id = personsResultSet.getInt(1);
			String name = personsResultSet.getString(2);
			String gender = personsResultSet.getString(3);
			String birthday = personsResultSet.getString(4);
			persons.add(new Person(id, name, gender, birthday));
		}
		
		return persons;
	}
}

















