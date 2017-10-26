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
	//���ӳػ��棬�������
	private final ArrayList<Connection> pool=new ArrayList<Connection>();
	//���ӳػ���������������ɵ�������Ŀ
	private int poolSize=10;
	public ConnectionPoolImpl(){
		initialize();
	}
	public ConnectionPoolImpl(int poolSize){		
		this.poolSize=poolSize;
		initialize();
	}
	
	//��ʼ�����ӳ�
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

	//�����ӳ���ȡ������
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
	
	//�����ӷŻ����ӳ�
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
	//�ر����ӳ�

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