package downloadmap;
import java.sql.Connection;
import java.sql.SQLException;
/**
* @author tao
* @version 1.0
*/

public interface ConnectionPool{
	//�����ӳ���ȥ������
	public Connection getConnection() throws SQLException;
	//�����ӷŻ����ӳ�
	public void releaseConnection(Connection conn) throws SQLException;
	//�ر�����
	public void close();

}