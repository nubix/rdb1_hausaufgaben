package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.Person;

/**
  * A class to wrap some data maipulation and querying tasks for the application
  */
public class MovieExplorer {
	public static void createSchema(Connection conn) throws SQLException {
		// TODO Exercise 12.1b: Create the following tables:
		/*
		 * CREATE TABLE movie(
		 *     id INTEGER NOT NULL PRIMARY KEY,
		 *     title VARCHAR(255) NOT NULL,
		 *     "year" INTEGER
		 * )
		 * 
		 * CREATE TABLE person(
		 *     id INTEGER NOT NULL PRIMARY KEY,
		 *     name VARCHAR(255) NOT NULL,
		 *     gender CHAR(1) CHECK (gender IN ('f', 'm')),
		 *     birthday CHAR(10)
		 * )
		 * 
		 * CREATE TABLE actor(
		 *     person INTEGER NOT NULL REFERENCES person,
		 *     movie INTEGER NOT NULL REFERENCES movie,
		 *     role VARCHAR(255) NOT NULL,
		 *     PRIMARY KEY (person, movie)
		 * )
		 */
		throw new RuntimeException("Not implemented. Please implement this method!");
	}
	
	private PreparedStatement insertMovieStmt;
	private PreparedStatement getMoviesBetweenYearsStmt;
	
	// TODO Exercise 12.1e declare prepared statements here 
	// TODO Exercise 12.1f declare prepared statements here 
	
	public MovieExplorer(Connection conn) throws SQLException {
		if(conn == null) {
			throw new IllegalArgumentException("Connecton must not be null");
		}
		
		// TODO Exercise 12.1c: Ensure that the schema is created here
		// use the static method MovieExplorer.createSchema(Connection) method complete this task
		// Handle the case where the tables are already set
		
		insertMovieStmt = conn.prepareStatement("INSERT INTO movie(id, title, \"year\") VALUES (?, ?, ?)");
		getMoviesBetweenYearsStmt = conn.prepareStatement("SELECT id, title, \"year\" FROM movie WHERE \"year\" >= ? AND \"year\" <= ?");
		
		// TODO Exercise 12.1e prepare the INSERT statements here 
		
		// TODO Exercise 12.1f prepare the SELECT statements here 
	}
	
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
	
	// TODO 12.1e implement the following methods analogous to the insertMovie method
	public void insertPerson(Person person) throws SQLException {
		throw new RuntimeException("Not implemented. Please implement this method!");
	}
	
	public void insertActorTuple(int personId, int movieId, String role) throws SQLException {
		throw new RuntimeException("Not implemented. Please implement this method!");
	}
	
	// TODO 12.1f implement the following method analogous to the getMoviesBetweenYears method
	public List<Person> getActorsToAMovie(int movieId) throws SQLException {
		throw new RuntimeException("Not implemented. Please implement this method!");
	}
}

















