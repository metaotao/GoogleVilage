package sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

/**
* @author tao
* @version 1.0
*/

public class GetAgeAmount{
	public GetAgeAmount(){
	}

	public static ArrayList<String> getAmount(){
		ArrayList<String> list=new ArrayList<String>();

		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection();
			String sql="select * from historyRecord";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getInt(5)+" "+rs.getInt(6)+" "+rs.getInt(7)+" "+rs.getInt(8));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				db.close(rs,pre,conn);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

}