//package puzzle;
/**
* @author tao
* @version 1.0
*/
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;

//import bean.FileBean;
public class Puzzles{
	private ArrayList<String> fileList;
	private ArrayList<BufferedImage> imageList=new ArrayList<BufferedImage>();

	public Puzzles(){
		puzzle();
	}
	public void getFiles(){
		fileList=new ArrayList<String>();
		try{
			File root=new File("E://googlemap//");
			File[] files=root.listFiles();
			for(File file:files){
				fileList.add(file.getAbsolutePath());
			}
		}
	
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getThread(){
		getFiles();
		for(int i=0;i<fileList.size();i++){
			new GetImage(fileList.get(i),imageList);
			System.out.println(fileList.get(i)+"文件夹拼接完成！");
		}
	}

	public void puzzle(){
		getThread();
		try{
			int height=0;
			int width=0;
			int new_height=0;
			int new_width=0;

			int imageNum=imageList.size();
			System.out.println(imageNum);
			int[] widthArray=new int[imageNum];
			ArrayList<int[]> imgRGB=new ArrayList<int[]>();

			BufferedImage buffer=null;
			int[] _imgRGB;
			for(int i=0;i<imageNum;i++){
				buffer=imageList.get(i);
				widthArray[i]=new_width=buffer.getWidth();
				height=buffer.getHeight();
				width+=new_width;

				_imgRGB=new int[width*height];
				_imgRGB=buffer.getRGB(0,0,new_width,height,_imgRGB,0,new_width);
				imgRGB.add(_imgRGB);
			}

			new_width=0;
			BufferedImage imageResult=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<imageNum;i++){
				new_width=widthArray[i];
				imageResult.setRGB(new_width*i,0,new_width,height,imgRGB.get(i),0,new_width);
				
			}	
			File outFile=new File("E://huangshan"+".png");
			ImageIO.write(imageResult,"png",outFile);
			System.out.println("地图拼接完成!");
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		new Puzzles();
	}

			
	
}