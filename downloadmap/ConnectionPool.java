package downloadmap;
import java.sql.Connection;
import java.sql.SQLException;
/**
* @author tao
* @version 1.0
*/

public interface ConnectionPool{
	//从连接池里去除连接
	public Connection getConnection() throws SQLException;
	//把连接放回连接池
	public void releaseConnection(Connection conn) throws SQLException;
	//关闭连接
	public void close();

}