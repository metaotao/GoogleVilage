package downloadmap;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

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
public class Server{
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket=new ServerSocket(8081);
		System.out.println("wait client connect...");
		try{
			while(true){
				Socket socket=serverSocket.accept();
				System.out.println("a client connected!");
				try{
					new Thread(new ServerThread(socket)).start();
				}
				catch(Exception e){
					//socket.close();
				}			
			}
		}
		finally{
			serverSocket.close();
		}
	}
}