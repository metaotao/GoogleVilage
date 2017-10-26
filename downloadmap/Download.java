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
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bean.*;

/**
* @author tao
* version 1.0
*/

public class Download{
	private Client client=new Client();
	private ArrayList list=client.getMessage();
	private String province;
	private String city;
	private String county;
	
	private double start_longitude;
	private double start_latitude;
	private double end_longitude;
	private double end_latitude;

	private double lngStartPixel;
	private double lngEndPixel;
	private double latStartPixel;
	private double latEndPixel;

	private String type;
	private String path;
	private Socket socket;

	private InfoBean infoBean=new InfoBean();

	private static Download download;
	public static Download instance(){
		if(download==null){
			download=new Download();
		}
		return download;
	}

	public Download(){
		start(list);
		download=this;
		
	}

	public void start(ArrayList list){
		
		ExecutorService pool=Executors.newFixedThreadPool(10);
		province=(String)(list.get(0));
		city=(String)(list.get(1));
		county=(String)(list.get(2));

		start_longitude=(double)(list.get(3));
		start_latitude=(double)(list.get(4));
		end_longitude=(double)(list.get(5));
		end_latitude=(double)(list.get(6));
		setEnd_longitude(end_longitude);
		setEnd_latitude(end_latitude);
		
		System.out.println(province+"  "+city+"  "+county+"  "+start_longitude+"  "+start_latitude+"  "+end_longitude+"  "+end_latitude);
		if((start_longitude!=0)&&(start_latitude!=0)&&(end_longitude!=0)&&(end_latitude!=0)){
			
			path="F://map//"+province+"//"+city+"//"+county+"//";
			if(!new File(path).exists()){
				new File(path).mkdirs();
			}

			latStartPixel=Transform.latToPixel(start_latitude,MapParameter.ZOOM);
			latEndPixel=Transform.latToPixel(end_latitude,MapParameter.ZOOM);
				
			lngStartPixel=Transform.lngToPixel(start_longitude,MapParameter.ZOOM);
			lngEndPixel=Transform.lngToPixel(end_longitude,MapParameter.ZOOM);

			int colRange=(int)((lngEndPixel-lngStartPixel)/MapParameter.MAP_WIDTH);
			int rowRange=(int)((latEndPixel-latStartPixel)/MapParameter.MAP_HEIGHT);
			System.out.println(colRange);
			
			for(int col=0;col<colRange;col++){
				DownloadMap downloadMap=new DownloadMap(path,start_latitude,start_longitude,col);
				downloadMap.setRowCount(rowRange);
				pool.execute(downloadMap);
				
				try{
					Thread.sleep(3000);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}
			
			//关闭线程池
			pool.shutdown();

			while(!pool.isTerminated()){  
            // 如果所有线程执行完成,那么跳出该循环.  
				
			} 
			
			System.out.println(province+"  "+city+"  "+county+"  "+"下载完成！");
			list.clear();
       			
			try{
				Thread.sleep(3000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			//连接完成后 重新获取连接
			ArrayList list1=new Client().getMessage();
			start(list1);
		}

		else{
			System.out.println("经纬度范围获取失败，请重新获取！");	
			list.clear();
			try{
				Thread.sleep(3000);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			//连接失败后 重新获取连接
			ArrayList list2=new Client().getMessage();
			start(list2);
		}		
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

	public static void main(String[] args){
		new Download();
	}
}