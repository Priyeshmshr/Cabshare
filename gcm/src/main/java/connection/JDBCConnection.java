package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JDBCConnection {

	static final String url = "jdbc:mysql://127.9.96.130:3306/csdb";
	static String user = null;
	static String password = null;
	private static Connection conn;
	Logger logger = Logger.getLogger("SmackCcsClient");

	public static Connection getConnection() {
		user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		if (user == null)
			user = "adminx7Ikp6v";
		password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		if (password == null)
			password = "rzA3E3AtpM29";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
