package sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import gui.*;
/**
* @author tao
* @version 1.0
*/

public class GetVilageName{
	private DBConnection db=new DBConnection();
	public GetVilageName(){
	}

	public ResultSet executeQuery(String sql){
		ResultSet rs=null;
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			conn=db.getConnection();
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
}