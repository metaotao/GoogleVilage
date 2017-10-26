package sql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.util.ArrayList;

/**
* @author tao
* @version 1.0
*/

public class GetParameter{
	public GetParameter(){
	}

	public static ArrayList<String> getHouseData(){
		ArrayList<String> list=new ArrayList<String>();

		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection();
			String sql="select * from houseParameter";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+
					" "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11)+" "
				+rs.getString(12)+" "+rs.getString(13)+" "+rs.getString(14));
				
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

	public static ArrayList<String> getVilageData(){
		ArrayList<String> list=new ArrayList<String>();

		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection();
			String sql="select * from vilageParameter";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+
					" "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11)+" "+rs.getString(12
					)+" "+rs.getString(13)+" "+rs.getString(14));
				
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

	public static ArrayList<String> getVilageInfo(){
		ArrayList<String> list=new ArrayList<String>();

		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection();
			String sql="select * from results";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
				
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

	public static int getVilageSum(String vilagename){
		int sum=0;
		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection1();
			String sql="select sum from quantity where vilagename='"+vilagename+"'";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				sum=rs.getInt(1);
				
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
		return sum;
	}

}