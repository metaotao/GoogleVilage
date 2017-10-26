package clickimage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
* @author tao
* @version 1.0
*/

public final class GoogleGeocoderUtil{
	private JLabel showLabel;

	public static final int ADDRESS=1;
	public static final int LATLNG=2;
	private Logger log=Logger.getLogger(GoogleGeocoderUtil.class.getName());
	private int type;
	private static GoogleGeocoderUtil instance;	
	private final String GOOGLEAPIURL="http://maps.googleapis.com/maps/api/geocode/json?language=zh-CN&sensor=true";
	public static String region;

	public static GoogleGeocoderUtil getInstance(){
		if(instance==null){
			instance=new GoogleGeocoderUtil();
		}
		return instance;
	}

	public GoogleGeocoderUtil(){

	}

	public int getType(){
		return type;
	}

	public void setType(int type){
		this.type=type;
	}
	

	public GoogleGeocodeJSONBean geocodeByAddress(String address) throws Exception{
		if(address==null||address.equals("")){
			return null;
		}

		System.out.println("输入的地址(经纬度)为:"+address);
		System.out.println("开始编码搜索...");

		GoogleGeocodeJSONBean bean=null;
		BufferedReader in= null;
		HttpURLConnection httpConn=null;

		try{
			String urlPath=GOOGLEAPIURL+"&address="+URLEncoder.encode(address,"UTF-8")+"";
			if(this.getType()==LATLNG){
				urlPath=GOOGLEAPIURL+"&latlng="+address;
			}
			
			if(this.getType()==ADDRESS){
				urlPath=GOOGLEAPIURL+"&address="+URLEncoder.encode(address,"UTF-8")+"";
			}

			log.info("URL为:"+urlPath);
			System.out.println("URL为:"+urlPath);

			URL url=new URL(urlPath);
			httpConn=(HttpURLConnection)url.openConnection(); 
			httpConn.setDoInput(true);   

			in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));   
		    String line="";
		    String result="";
		    while((line=in.readLine())!=null){   
		        result+=line;   
		    }
			
		    in.close();

		    JSONObject jsonObject=JSONObject.fromObject(result);
		    bean=(GoogleGeocodeJSONBean)JSONObject.toBean(jsonObject,GoogleGeocodeJSONBean.class);

		    if(bean!=null&&bean.status.equalsIgnoreCase("ok")&&
				bean.results!=null&&bean.results[0].geometry.getLocation()!=null){
				region=bean.results[0].getFormatted_address();
		    	System.out.println("开始地理编码搜索..");
		    	System.out.println("地理编码为:"+region);
		    	log.info("精确经纬度为:"+bean.results[0].geometry.getLocation().getLat()+","+
					bean.results[0].geometry.getLocation().getLng());
			    System.out.println("地理编码结果搜索结束");
		    }

		    System.out.println("结束");
		    return bean;
		}
		
		catch(MalformedURLException e){
			log.error(e);
			throw e;
		} 
		
		catch(IOException e){
			log.error(e);
			throw e;
		} 
		
		finally{
			if(in!=null){
				try{
					in.close();
				} 
				catch(IOException e){
					log.error(e);
					throw e;
				}
			}

			if (httpConn!=null){
				httpConn.disconnect();
			}
		}
	}
	
	public String getGoogleLongitudeDimensions(GoogleGeocodeJSONBean googleBean) throws IOException{
		if (googleBean!=null&&googleBean.status.equalsIgnoreCase("ok")
				&& googleBean.results[0]!=null
				&& googleBean.results[0].formatted_address!=null
				&& googleBean.results[0].getGeometry().location!=null
				&& googleBean.results[0].getGeometry().location.getLat()!=null
				&& googleBean.results[0].getGeometry().location.getLng()!=null){

			String formatted_Address=googleBean.results[0].formatted_address;
			String location = googleBean.results[0].getGeometry().location.getLat()+","+googleBean.results[0].getGeometry().location.getLng();
			return formatted_Address.trim()+"|"+location;
		}
		return null;
	}
	public static void main(String[] args) throws Exception{
		getInstance().setType(1);
		GoogleGeocodeJSONBean bean=getInstance().geocodeByAddress("呈坎村");
	}
}
