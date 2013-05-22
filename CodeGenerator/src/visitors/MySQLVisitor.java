package visitors;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public abstract class MySQLVisitor implements Visitor {

	protected Connection conn;
	protected PreparedStatement stmt;
	protected ResultSet rs;
	
	public MySQLVisitor()
	{
		
	}
	
	public MySQLVisitor(String path, String userName, String password) 
			throws ClassNotFoundException, SQLException
	{
		this();
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(path,userName,password);
	}
	
	public void closeConnection() throws SQLException
	{
		conn.close();
		stmt.close();
		rs.close();
	}
}
