package sig;

public class Database {
	
	private final static String JDBC_DRIVER = "";
	private final static String DB_URL ="jdbc:postgresql://";
	
	private final static String USER = "";
	private final static String PASSWORD = "";
	
	
	private static Database instance = null;
	
	private Database(){
		super();
	}
	
	public static Database getInstance(){
		if ( instance == null ){
			instance = new Database();
		}
		
		return instance;
	}

}
