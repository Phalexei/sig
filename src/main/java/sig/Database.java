package sig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database class. Manage database connection
 * @author chretiea
 *
 */
public class Database {
	
	/**
	 * Database's informations for connection
	 */
	private final static String JDBC_DRIVER = "org.postgresql.Driver";
	private final static String DB_URL ="jdbc:postgresql://ensibd.imag.fr:5432/osm";
	private final static String USER = "chretiea";
	private final static String PASSWORD = "chretiea";
	
	/**
	 * Variable
	 */
	private static Database instance = null;
	private static Connection connection = null;
	
	/**
	 * Private constructor
	 */
	private Database(){
		if(DriverAvailable()){
			connect();
		}
		
	}
	
	/**
	 * Connection to the database
	 */
	private void connect() {
		try {
			 
			connection = DriverManager.getConnection(DB_URL, USER,PASSWORD);
			System.out.println("Connection succeed");
		} catch (SQLException e) {
 
			System.out.println("Connection Failed");
			e.printStackTrace();
		}
	}

	/**
	 * Check if JDBC driver id found
	 * @return
	 */
	private boolean DriverAvailable(){
		
		boolean available = true;
		try{
			Class.forName(JDBC_DRIVER);
		}catch (ClassNotFoundException e) {
			available = false;
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		
		return available;
	}
	
	/**
	 * Get the database's connection
	 * @return
	 */
	public static Connection getConnection(){
		if ( instance == null ){
			instance = new Database();
		}
		
		return connection;
	}

}
