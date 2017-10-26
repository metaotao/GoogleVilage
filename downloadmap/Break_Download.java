package downloadmap;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;
import bean.*;
import sql.*;
/**
* @author tao
* version 1.0
*/

public class Break_Download{
	private DBConnection db=new DBConnection();
	private ArrayList list;
	private String province;
	private String city;
	private String county;
	
	private double start_longitude;
	private double start_latitude;
	private double break_longitude;
	private double break_latitude;
	private double end_longitude;
	private double end_latitude;

	private double lngStartPixel;
	private double lngEndPixel;
	private double latStartPixel;

	private double latEndPixel;
	private String type;
	private String path;
	public static Break_Download download;

	private int col;
	private int row;
	
	public static Break_Download instance(){
		if(download==null){
			download=new Break_Download();
		}
		return download;
	}

	public Break_Download(){
		start();
		download=this;
	}

	public void getInfo(){
		list=executeQuery("select break_position,break_col,break_row,break_longitude,break_latitude,end_longitude,end_latitude from break_place where status=0");
		System.out.println(list);
		path=(String)list.get(0);
		col=(int)list.get(1);
		row=(int)list.get(2);
		break_longitude=(double)list.get(3);
		break_latitude=(double)list.get(4);
		end_longitude=(double)list.get(5);
		end_latitude=(double)list.get(6);
	}

	public void setEnd_longitude(double end_longitude){
		this.end_longitude=end_longitude;
	}
	public double getEnd_longitude(){
		return end_longitude;
	}

	public void setEnd_latitude(double end_latitude){
		this.end_latitude=end_latitude;
	}
	public double getEnd_latitude(){
		return end_latitude;
	}

	public void start(){
		getInfo();
		getStartLngLat(end_longitude);
		updateInfo(end_longitude);
		ExecutorService pool=Executors.newFixedThreadPool(10);
		
		//String[] getpath=path.split(18+"");
		if(new File(path).exists()){
			new File(path).mkdirs();
		}
	
		latStartPixel=Transform.latToPixel(start_latitude,MapParameter.ZOOM);
		latEndPixel=Transform.latToPixel(end_latitude,MapParameter.ZOOM);
				
		lngStartPixel=Transform.lngToPixel(start_longitude,MapParameter.ZOOM);
		lngEndPixel=Transform.lngToPixel(end_longitude,MapParameter.ZOOM);

		int colRange=(int)((lngEndPixel-lngStartPixel)/MapParameter.MAP_WIDTH);
		int rowRange=(int)((latEndPixel-latStartPixel)/MapParameter.MAP_HEIGHT);
		System.out.println(colRange+"  "+rowRange);
		
		
		for(int i=col;i<colRange;i++){
			if(i==col){
				Break_DownloadMap downloadMap=new Break_DownloadMap(path,start_latitude,start_longitude,col);
				downloadMap.setRowCount(rowRange);
				downloadMap.setRow(row);
				pool.execute(downloadMap);
			}
			else{
				Break_DownloadMap downloadMap=new Break_DownloadMap(path,start_latitude,start_longitude,i);
				downloadMap.setRowCount(rowRange);
				downloadMap.setRow(0);
				pool.execute(downloadMap);
			}
				
			try{
				Thread.sleep(3000);
			}
			catch(Exception e){
				e.printStackTrace();
			}				
		}
			
		//关闭线程池
		pool.shutdown();
		while(!pool.isTerminated()){  
           // 如果所有线程执行完成,那么跳出该循环.  
		} 
			
		System.out.println(path+"  "+"下载完成！");
		list.clear();
		try{
			Thread.sleep(3000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
	}

	public ArrayList executeQuery(String sql){
		ResultSet rs=null;
		PreparedStatement pre=null;
		ArrayList list=new ArrayList();
		Connection conn=null;
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
				list.add(rs.getInt(2));
				list.add(rs.getInt(3));
				list.add(rs.getDouble(4));
				list.add(rs.getDouble(5));
				list.add(rs.getDouble(6));
				list.add(rs.getDouble(7));
			}			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				pre.close();
				conn.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

	public void getStartLngLat(double end_longitude){
		ResultSet rs=null;
		PreparedStatement pre=null;
		Connection conn=null;
		try{
			conn=db.getConnection1();
			String sql="select start_co_longitude,start_co_latitude from nation_division where end_co_longitude="+end_longitude;
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			
			while(rs.next()){
				start_longitude=rs.getDouble(1);
				start_latitude=rs.getDouble(2);			
			}	
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				pre.close();
				conn.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void updateInfo(double end_longitude){
		PreparedStatement pre=null;
		Connection conn=null;
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement("update break_place set status=1 where end_longitude=?");
			pre.setDouble(1,end_longitude);
			pre.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				pre.close();
				conn.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args){
		new Break_Download();
	}
}