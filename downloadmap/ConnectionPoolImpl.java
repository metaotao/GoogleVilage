package downloadmap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import sql.*;
/**
* @author tao
* @version 1.0
*/

public class ConnectionPoolImpl implements ConnectionPool{
	
	private DBConnection db=new DBConnection();
	//连接池缓存，存放连接
	private final ArrayList<Connection> pool=new ArrayList<Connection>();
	//连接池缓存中做多可以容纳的连接数目
	private int poolSize=10;
	public ConnectionPoolImpl(){
		initialize();
	}
	public ConnectionPoolImpl(int poolSize){		
		this.poolSize=poolSize;
		initialize();
	}
	
	//初始化连接池
	public void initialize(){
		try{
			synchronized(pool){
				for(int i=0;i<poolSize;i++){
					pool.add(db.getConnection1());
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	//从连接池中取出连接
	public Connection getConnection() throws SQLException{
		synchronized(pool){
			if(!pool.isEmpty()){
				int last=pool.size()-1;
				Connection con=pool.remove(last);
				return con;
			}
		}
		Connection con=db.getConnection1();
		return con;
	}
	
	//把连接放回连接池
	public void releaseConnection(Connection con) throws SQLException{
		synchronized(pool){
			int currentSize=pool.size();
			if(currentSize<poolSize){
				pool.add(con);
				return;
			}
		}
		try{
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	protected void finalize(){
		close();
	}
	//关闭连接池

	public void close(){
		Iterator<Connection> iter=pool.iterator();
		while(iter.hasNext()){
			try{
				iter.next().close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		pool.clear();
	}


}