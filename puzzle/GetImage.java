//package puzzle;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.List;

/**
* @author tao
* @version 1.0
*/

public class GetImage implements Runnable{
	private String filePath;
	private ArrayList<String> list;
	private ArrayList<BufferedImage> imageLists;
	//private ArrayList<BufferedImage> imageList=new ArrayList<BufferedImage>();
	private int imageSize=256;
	private int imageNum=16;
	public GetImage(String filePath,ArrayList<BufferedImage> imageLists){
		this.filePath=filePath;
		this.imageLists=imageLists;
		run();
	}

	public void run(){
		list=new ArrayList<String>();
		try{
			File root=new File(filePath);
			File[] files=root.listFiles();
			for(File file:files){
				list.add(file.getAbsolutePath());
			}
			getNewImage(list);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getNewImage(ArrayList<String> imageList){
		if(imageList==null||imageList.size()<=0){
			System.out.println("文件夹无图片!");
			return;
		}

		try{
			int height=0;
			int width=0;
			int new_height=0;
			int new_width=0;

			int imageNum=imageList.size();
			int[] heightArray=new int[imageNum];
			ArrayList<int[]> imgRGB=new ArrayList<int[]>();

			File newImage=null;
			BufferedImage buffer=null;
			int[] _imgRGB;
			for(int i=0;i<imageNum;i++){
				System.out.println(imageList.get(i)+"   正在拼接...");
				newImage=new File(imageList.get(i));
				buffer=ImageIO.read(newImage);

				heightArray[i]=new_height=buffer.getHeight();				
				width=buffer.getWidth();
				height+=new_height;

				_imgRGB=new int[width*height];				

				_imgRGB=buffer.getRGB(0,0,width,new_height,_imgRGB,0,width);
				imgRGB.add(_imgRGB);
			}
			new_height=0;
			BufferedImage imageResult=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<imageNum;i++){				
				new_height=heightArray[i];					
				imageResult.setRGB(0,new_height*i,width,new_height,imgRGB.get(i),0,width);								
			}
			imageLists.add(imageResult);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
