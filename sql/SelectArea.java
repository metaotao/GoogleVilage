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

public class SelectArea{
	private DBConnection db=new DBConnection();

	public SelectArea(){
	}

	public ResultSet executeQuery(String sql){
		ResultSet rs=null;
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}


}