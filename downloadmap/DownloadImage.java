package downloadmap;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.net.URL;
import java.net.HttpURLConnection;

import sql.*;

public class DownloadImage implements Runnable{
	private String path;
	private String name;
	private String type;
	private double end_longitude;
	private double end_latitude;
	private int count;
	private String key=null;
	private String[] keys={"AIzaSyB-fImuq9XFz3ZdRwFB-0KkytFkqOCJwzE","AIzaSyCA5UaUMFp2pnYSo6poJiQieWHSHg8mmDk",
		"AIzaSyCNs8J3o4LkpHG7EYdK_QailpYilLuBLOg","AIzaSyAndzSRoAbMJLajU-zrfmDT8b_VB8fTKgg",
		"AIzaSyB8eX8CsVMoXz_L04QO_AY68yxPhgKLdg4","AIzaSyA0A6RORCGxOHp2qS9JBndrlTsS2BQz25w",
		"AIzaSyB3zKVWf9WqTelPtinqxfQ3xDha1NHW6Nk","AIzaSyBTf6Sj4zaP9CwhXDDEACmO4a0IvELj4Dc",
		"AIzaSyCVSDDhSRJEj92EgGB7QbqSGVG9jj5AJqM"};

	private DownloadMapSql downloadMapSql=new DownloadMapSql();
	public DownloadImage(String path,String name){
		this.path=path;
		this.name=name;
	}
	
	public void setEnd_longitude(double end_longitude){
		this.end_longitude=end_longitude;
	}

	public double getEnd_longitude(){
		return end_longitude;
	}

	public void setEnd_latitude(double end_latitude){
		this.end_latitude=end_latitude;
	}

	public double getEnd_latitude(){
		return end_latitude;
	}


	public void run(){
		String[] split=name.split(",");
		URL url=null;
		HttpURLConnection urlConnection=null;
		OutputStream output=null;
		String urlStr=null;
		try{
			if(count<=0){
				key="AIzaSyAndzSRoAbMJLajU-zrfmDT8b_VB8fTKgg";
				urlStr="http://maps.googleapis.com/maps/api/staticmap?center="+split[2]+","+split[3]+
					"&zoom=18&format=png&size=256x316&maptype=satellite&sensor=false&key="+key;
			}
			else{
				urlStr="http://maps.googleapis.com/maps/api/staticmap?center="+split[2]+","+split[3]+
				"&zoom=18&format=png&size=256x316&maptype=satellite&sensor=false&key="+key;
			}

			url=new URL(urlStr);			
			urlConnection=(HttpURLConnection)url.openConnection();
			urlConnection.setReadTimeout(5000);
			urlConnection.setConnectTimeout(5000);			
						
			if(urlConnection.getResponseCode()==403){
				System.out.println("���ֽ�ֹ���ʴ���,���»���Կ");
				int random=(int)(Math.random()*8);
				key=keys[random];
				count+=1;
			}

			String imagepath=path+MapParameter.ZOOM+"-"+split[0]+"//"+split[1]+"-"+split[2]+"-"+split[3]+".png";

			BufferedImage image=ImageIO.read(urlConnection.getInputStream());
			BufferedImage newImage=new BufferedImage(MapParameter.MAP_WIDTH,MapParameter.MAP_HEIGHT,BufferedImage.TYPE_INT_RGB);
			int[] buf=image.getRGB(0,30,MapParameter.MAP_WIDTH,MapParameter.MAP_HEIGHT,new int[MapParameter.MAP_WIDTH*MapParameter.MAP_HEIGHT],0,MapParameter.MAP_WIDTH);				
			newImage.setRGB(0,0,MapParameter.MAP_WIDTH,MapParameter.MAP_HEIGHT,buf,0,MapParameter.MAP_WIDTH);
										
			File file=new File(imagepath);
			output=new FileOutputStream(file);
			ImageIO.write(newImage,"png",output);
			System.out.println("ͼƬ"+imagepath+"   17��  ������ɣ�");
			type="ok";
		}
		catch(Exception e){
			try{
				Thread.sleep(3000);
			}
			catch(InterruptedException ee){
			//	ee.printStackTrace();
			}
			System.out.println(e.getMessage());

		}
		finally{
			try{
				output.close();
				Thread.sleep(2000);						
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	public String getType(){
		return type;
	}
}