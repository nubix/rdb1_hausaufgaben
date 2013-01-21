package main;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
	public static void main(String[] args) {
		/*
		 * If you use db2, please use the connection.properties file to manage your user name and password.
		 * The usage of a Properties file in Java is as follows:
		 * 
		 *   // First create a new Properties object:
		 *   Properties props = new Properties();
		 * 
		 *   // Then load the properties contained in the properties file:
		 *   props.load(new FileInputStream(new File("connection.properties")));
		 * 
		 *   // you can then read the properties (e.g. the user name):
		 *   String user = props.getProperty("user");
		 * 
		 */
		
		Connection conn = null;
		try {
			conn = getConnection();
			MovieExplorer movieExplorer = new MovieExplorer(conn);
			// TODO 12.1d: insert the movies described in the exercise sheet
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	// if you use db2, the getConnection method should receive a Properties object to get the relevant data for the connection
	// if you use derby this is not really necessary
	public static Connection getConnection(/*Properties connectionProps*/) throws SQLException {
		/*
		 * If you use derby, the connection URL has the following pattern (text in angle brackets has to be replaced respectively):
		 *     jdbc:derby:<dbname>;create=true
		 *     Example: jdbc:derby:testdb;create=true
		 *     
		 * If you use db2 please follow the instructions contained in the lecture slides.
		 */
		 
		// TODO Exercise 12.1a
		throw new RuntimeException("Not implemented. Please implement this method!");
	}
}
