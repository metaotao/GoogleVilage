package puzzle;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import sql.*;

/**
* @author tao
* @version 1.0
*/

public class XMLReaderFile implements XmlDocument{
	
	private Map<String,String> map=new HashMap<String,String>();	
	private List<Attribute> p_attribute;
	private List<Attribute> c_attribute;
	private List<Attribute> attributeList;
	private Map<String,String> cmap=new HashMap<String,String>();
	private Map<String,String> pmap=new HashMap<String,String>();
	private DBConnection db=new DBConnection();
	private String getPname;
	public XMLReaderFile(){
		parserXml("file\\chinaBounder.xml");
		getRegion(pmap,"province","start_p_longitude","start_p_latitude","end_p_longitude","end_p_latitude");
		getRegion(cmap,"city","start_ci_longitude","start_ci_latitude","end_ci_longitude","end_ci_latitude");
		getRegion(map,"county","start_co_longitude","start_co_latitude","end_co_longitude","end_co_latitude");

	}

	public void parserXml(String fileName){
		File inputXml=new File(fileName);
		SAXReader saxReader=new SAXReader();
		 try{
            Document document = saxReader.read(inputXml);
            Element root=document.getRootElement();
			List<Element> elements=root.elements();
			for(Element element:elements){
				p_attribute=element.attributes();

				pmap.put(p_attribute.get(2).getValue(),p_attribute.get(3).getValue());
				List<Element> elementList=element.elements();
				for(Element ele:elementList){	
				c_attribute=ele.attributes();
					cmap.put(c_attribute.get(1).getValue(),c_attribute.get(2).getValue());
					List<Element> childList=ele.elements();
					for(Element eles:childList){
						attributeList=eles.attributes();
						String name=attributeList.get(1).getValue();
						String rings=attributeList.get(2).getValue();
						map.put(name,rings);						
					}
				}							
            }
			
        } 
		catch(DocumentException e){
            System.out.println(e.getMessage());
		}
	}

	public void getRegion(Map<String,String> map,String str,String start_longitude,String start_latitude,String end_longitude,String end_latitude){
		Set<String> set=map.keySet();
		for(String string:set){
			System.out.println(string+"导入完成!");
			
			double maxLng=0,maxLat=0,minLng=0,minLat=0;
			String values=map.get(string);
			if(values.equals("")){
				continue;
			}
			else{
				ArrayList<String> list=getRange(map,string);	
				String[] min=list.get(0).split(" ");
				minLng=Double.parseDouble(min[0]);
				minLat=Double.parseDouble(min[1]);

				for(int i=0;i<list.size();i++){
					String s1=list.get(i);
					String[] ss=s1.split(" ");

					double lng=Double.parseDouble(ss[0]);
					double lat=Double.parseDouble(ss[1]);
			
					if(lng>maxLng){
						maxLng=lng;
					}
					if(lat>maxLat){
						maxLat=lat;
					}
					if(minLng>lng){
						minLng=lng;
					}
					if(minLat>lat){
						minLat=lat;
					}
				}
			}
			System.out.println(maxLat+","+minLng+"     "+minLat+","+maxLng);
			String sql="update `nation_division` set `"+start_longitude+"`=?,`"+start_latitude+"`=?,`"+end_longitude+"`=?,`"+end_latitude+"`=? where "+str+"=?";
			importDataToSql(sql,string,minLng,maxLat,maxLng,minLat);
		}
	}	 

	public ArrayList<String> getRange(Map<String,String> map,String str){
		ArrayList<String> areaRegion=new ArrayList<String>();
		String value=map.get(str);
		String[] ranges=value.split(",");
		for(String s:ranges){
			areaRegion.add(s);
		}
		return areaRegion;
	}

	public void importDataToSql(String sql,String name,double start_longitude,double start_latitude,double end_longitude,double end_latitude){
		Connection conn=null;
		PreparedStatement pre=null;
		try{
			conn=db.getConnection1();
			pre=conn.prepareStatement(sql);
			pre.setDouble(1,start_longitude);
			pre.setDouble(2,start_latitude);
			pre.setDouble(3,end_longitude);
			pre.setDouble(4,end_latitude);
			pre.setString(5,name);
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

	public static void main(String[] args) throws Exception{
		LoadFile load=new LoadFile();
		new XMLReaderFile();
	}
					
}