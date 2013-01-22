package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.*;
import java.io.*;


public class Main {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(new File("connection.properties")));

			conn = getConnection(props);
			MovieExplorer movieExplorer = new MovieExplorer(conn);
			Movie [] movies = {new Movie(1, "movie 1", 1999),
							   new Movie(2, "movie 2", 1980),
							   new Movie(3, "movie 3", 2005),
							   new Movie(4, "movie 4", 2003)};
			for(Movie m : movies) {
				movieExplorer.insertMovie(m);
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	* Receives connection Properties 
	* 
	* @param connectionProps Object containing properties for connection
	* @throws SQLException if an error happens
	* @return Object representing the connection to the databaseserver 
	*/
	public static Connection getConnection(Properties connectionProps) throws SQLException {
		/*
		 * Copy 'n Paste from Slides
		 */
		return DriverManager.getConnection(
					"jdbc:db2://" + connectionProps.getProperty("server") + ":" +
					connectionProps.getProperty("port") + "/" +
					connectionProps.getProperty("database"),
					connectionProps.getProperty("user"),
					connectionProps.getProperty("password")
					);
	}
}
