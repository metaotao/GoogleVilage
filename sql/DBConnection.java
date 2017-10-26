package sql;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
* @author tao
* @version 1.0
*/
public class DBConnection{
	public Connection getConnection() throws SQLException{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/mapparameter";
		String user="root";
		String password="123456";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url,user,password);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}

	public Connection getConnection1() throws SQLException{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/googlemap";
		String user="root";
		String password="123456";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url,user,password);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}


	public static void close(ResultSet rs,PreparedStatement pre,Connection conn){
		close(rs);
		close(pre);
		close(conn);
	}

	public static void close(PreparedStatement pre,Connection conn){
		close(pre);
		close(conn);
	}
	public static void close(Connection conn){
		try{
			if(conn!=null){
				conn.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pre){
		try{
			if(pre!=null){
				pre.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}