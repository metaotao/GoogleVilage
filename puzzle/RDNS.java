//package downloadmap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
* @author tao
* @version 1.0
*/
public class RDNS{
	/**
	* 根据经纬度反向解析
	* @param latitude
	*            纬度
	* @param longitude
	*            经度
	* @return
	*/

	public static String geocodeAddr(String latitude,String longitude){
		String addr=null;
		String url=String.format("http://ditu.google.cn/maps/geo?output=xml&key=abc&q=%s,%s",latitude,longitude);
		URL myURL=null;
		URLConnection urlConnection=null;
		try{
			myURL=new URL(url);
		}
		//URL是否错误
		catch(MalformedURLException e){
			e.printStackTrace();
			return null;
		}
		try{
			urlConnection=(URLConnection)myURL.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			String data=null;
			if((data=br.readLine())!=null){
				System.out.println("********"+data);
				String[] retList=data.split(",");
				if(retList.length>2&&("200".equals(retList[0]))){
					addr=retList[2];
					addr=addr.replace("\"","");
				}
				else{
					addr=null;
				}
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
		return addr;
	}

	public static void main(String[] args){
		String addr=geocodeAddr("31.71099194","117.32457778");
		System.out.println(addr);
	}

}