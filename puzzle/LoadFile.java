package puzzle;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.BufferedReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import sql.*;
public class LoadFile{
	private DBConnection db=new DBConnection();
	public LoadFile(){
		load();
	}

	public void load(){
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			BufferedReader reader=new BufferedReader(new FileReader(new File("file\\huangshan.txt")));
			String str=null;
			conn=db.getConnection1();
			pre=conn.prepareStatement("insert into quantity(province,city,areaname,townname,vilagename) values(?,?,?,?,?)");
			while((str=reader.readLine())!=null){
				String[] split=str.split("	");
				pre.setString(1,split[0]);
				pre.setString(2,split[1]);
				pre.setString(3,split[2]);
				pre.setString(4,split[3]);
				pre.setString(5,split[4]);
				pre.executeUpdate();
			}
			
			System.out.println("数据导入成功！");
		}
		catch(Exception e){
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

	public static void main(String[] args){
		new LoadFile();
	}
}