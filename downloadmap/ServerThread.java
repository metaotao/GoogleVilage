package downloadmap;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;

import java.util.ArrayList;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sql.*;
import bean.*;

/**
* @author tao
* version 1.0
*/

public class ServerThread implements Runnable{
	private Socket socket;
	//private ObjectInputStream input;
	private Connection conn=null;
	private DBConnection db=new DBConnection();
//	private ConnectionPoolImpl pool=new ConnectionPoolImpl(10);

	public ServerThread(Socket socket){
		try{
			this.socket=socket;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void run(){
		ObjectInputStream input=null;
		ObjectInputStream in=null;
		try{
			
			input=new ObjectInputStream(socket.getInputStream());						
			DBBean inputBean=(DBBean)input.readObject();
			String sql=inputBean.getSql();			
			System.out.println(sql);
			//获取客户端返回的信号		

			ArrayList list=executeQuery(sql);
			System.out.println(list);
			DBBean outputBean=new DBBean();
			outputBean.setList(list);
			outputObject(outputBean);

			in=new ObjectInputStream(socket.getInputStream());
			DBBean bean=(DBBean)input.readObject();
			String str=bean.getSql();			
			System.out.println(str);
			executeUpdate(str);
			list.clear();					
		}
		catch(Exception e){;
			e.printStackTrace();
		}	
		finally{
			try{
				input.close();
				in.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void outputObject(DBBean bean){
		try{
			ObjectOutputStream output=new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(bean);
			output.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}


	public ArrayList executeQuery(String sql){
		ResultSet rs=null;
		PreparedStatement pre=null;
		ArrayList list=new ArrayList();
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
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