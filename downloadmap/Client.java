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

import sql.*;
import bean.*;

/**
* @author tao
* version 1.0
*/

public class Client{
	private Socket socket;
	private String sql="select province,city,county,start_co_longitude,start_co_latitude,end_co_longitude,end_co_latitude from nation_division where status=0 limit 1";
	
	public Client(){
		try{
			socket=new Socket("192.168.24.79",8081);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public ArrayList getMessage(){
		ArrayList list=null;
		ObjectOutputStream output=null;
		ObjectInputStream input=null;
		try{

			output=new ObjectOutputStream(socket.getOutputStream());		
			DBBean outputBean=new DBBean();
			//System.out.println(sql);
			outputBean.setSql(sql);
			output.writeObject(outputBean);
			
			input=new ObjectInputStream(socket.getInputStream());
			DBBean inputBean=(DBBean)input.readObject();
			list=inputBean.getList();
			System.out.println(list);

			outputObject(list);
			output.flush();	
			
		}
	
		catch(IOException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		finally{
			try{
				output.close();
				input.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public void outputObject(ArrayList list){
		try{
			ObjectOutputStream output=new ObjectOutputStream(socket.getOutputStream());
			DBBean bean=new DBBean();
			bean.setSql("update nation_division set status=1 where county='"+list.get(2)+"'");
			output.writeObject(bean);
			output.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new Client();
	}

}