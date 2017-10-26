package sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
* @author tao
* @version 1.0
*/

public class SaveParameterToSql{
	DBConnection db=new DBConnection();
	public SaveParameterToSql(){
	}

	public void saveSql(String vilageName,String selectState,int height,int width,String shape){
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			conn=db.getConnection();
			String sql="insert into createParameter(vilageName,selectState,height,width,shape) values(?,?,?,?,?)";
			pre=conn.prepareStatement(sql);
			pre.setString(1,vilageName);
			pre.setString(2,selectState);
			pre.setInt(3,height);
			pre.setInt(4,width);
			pre.setString(5,shape);
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

	public void saveResultSql(String vilageName,int nowSum,String accuracyRating,
		String greenPercentage,String fieldPercentage,String roadPercentage){
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			conn=db.getConnection();
			String sql="insert into results(vilageName,nowSum,accuracyRating,greenPercentage,fieldPercentage,roadPercentage) values(?,?,?,?,?,?)";
			pre=conn.prepareStatement(sql);
			pre.setString(1,vilageName);
			pre.setInt(2,nowSum);
			pre.setString(3,accuracyRating);
			pre.setString(4,greenPercentage);
			pre.setString(5,fieldPercentage);
			pre.setString(6,roadPercentage);
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