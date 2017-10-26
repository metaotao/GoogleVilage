package bean;

import java.util.ArrayList;

import java.io.Serializable;
/**
* @author tao
* version 1.0
*/

public class DBBean implements Serializable{
	private ArrayList list;
	private String sql;
	private String signal;
	public void setSql(String sql){
		this.sql=sql;
	}

	public String getSql(){
		return sql;
	}

	public void setList(ArrayList list){
		this.list=list;
	}

	public ArrayList getList(){
		return list;
	}

	public void setSignal(String signal){
		this.signal=signal;
	}

	public String getSignal(){
		return signal;
	}
}