package sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

import sql.DBConnection;
/**
* @author tao
* @version 1.0
*/
public class DownloadMapSql{
	private DBConnection db=new DBConnection();
	private Connection conn=null;

	private static DownloadMapSql downloadMapSql;
	public static DownloadMapSql instance(){
		if(downloadMapSql==null){
			downloadMapSql=new DownloadMapSql();
		}
		return downloadMapSql;
	}

	public DownloadMapSql(){
		downloadMapSql=this;
	}

	public void executeUpdate(String sql){
		PreparedStatement pre=null;
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement(sql);
			pre.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				db.close(pre,conn);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}	


}