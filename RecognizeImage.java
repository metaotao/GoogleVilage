//package clickimage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BorderLayout;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* @author tao
* @version 1.0
*/

public class RecognizeImage extends JFrame{
	private BufferedImage image;
	private int width;
	private int height;
	private int[][] gray;

	private int[][] gray1;
	private int[][] pi;
	private int count;
	private int sum;
	private int index;
	
	private JButton ensure;

	WritableRaster raster=null;
	ColorModel model=null;
	Color fractalColor=null;
	int argb=0;
	Object colorData=null;

	public static final int RIGHT=1;   //向右走
	public static final int DOWN=2;    //向下走
	public static final int LEFTDOWN=3; //向左下走
	public static final int RIGHTUP=4;   //向右下走
	private static RecognizeImage recognizeImage;
	private Color[] c={Color.red,Color.blue,Color.green};
	public static RecognizeImage instance(){
		if(recognizeImage==null){
			recognizeImage=new RecognizeImage();
		}
		return recognizeImage;
	}
	public RecognizeImage(){	
		
		init();
		setSize(1200,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}


	public void init(){
		try{
			
			image=ImageIO.read(new File("E://nan.bmp"));
			
			getImage(image);
			add(new JScrollPane(new JLabel(new ImageIcon(image))));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getImage(BufferedImage image){		
		try{

			width=image.getWidth();
			height=image.getHeight();

			int minX=image.getMinX();
			int minY=image.getMinY();

			pi=new int[width][height];
			gray=new int[width][height];
			int[] rgb=new int[3];
			gray1=new int[width][height];
			System.out.println(width+"  "+height);

			raster=image.getRaster();
			model=image.getColorModel();
			fractalColor=new Color(255,255,255);
			argb=fractalColor.getRGB();
			colorData=model.getDataElements(argb,null);

			//设定阈值
			int sw=100;
			long startTime=System.currentTimeMillis();

			for(int i=minX;i<width;i++){
				for(int j=minY;j<height;j++){
					pi[i][j]=getGray(image.getRGB(i,j));
					
				}
			}
			for(int i=minX;i<width;i++){
				for(int j=minY;j<height;j++){
					if(getAverageColor(pi,i,j,width,height)>sw){
						//白点
						gray[i][j]=0;
					}
					else{
						//黑点
						gray[i][j]=1;
					}
				}
			}

			for(int i=minX;i<width;i++){
				for(int j=minY;j<height;j++){
					int pixel=image.getRGB(i,j);
					rgb[0]=(pixel>>16)&0xff;
					rgb[1]=(pixel>>8)&0xff;
					rgb[2]=(pixel)&0xff;

					if(rgb[0]>60&&rgb[1]>60&&rgb[2]>60&&rgb[0]<160&&rgb[1]<160&&rgb[2]<160
						&&rgb[0]-rgb[2]<15){
						gray1[i][j]=1;
					}

					if(((rgb[1]-rgb[0])+(rgb[1]-rgb[2]))>10){
						gray1[i][j]=0;
					}

					if(rgb[0]>85&&rgb[0]<90&&rgb[1]>95&&rgb[1]<120&&rgb[2]>95&&rgb[2]<120){
						gray1[i][j]=0;
					}
				}
			}

			//去除大面积噪点
			for(int x=10;x<width-10;x+=20){
				for(int y=10;y<height-10;y+=20){
					remove(x,y);
				}
			}

			for(int y=10;y<height-10;y+=20){
				for(int x=10;x<width-10;x+=20){
					remove(x,y);
				}
			}
			
			getSum(image);
			ImageIO.write(image,"png",new File("map\\南溪南村.png"));
			long endTime=System.currentTimeMillis();
			System.out.println("共耗时:"+(endTime-startTime)+"ms");
			System.out.println("民居总数为："+sum);
		}
			
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getSum(BufferedImage image){
		for(int x=12;x<width-12;x+=24){
			for(int y=11;y<height-11;y+=22){
				getSquare(image,x,y);
			}
		}
	}

	//定义一个24x22正方形
	public void getSquare(BufferedImage image,int x,int y){			
		for(int i=x-12;i<=x+12;i++){
			for(int j=y-11;j<=y+11;j++){
				if(gray1[i][j]==1){
					count+=1;
				}
			}
		}

		if(count>=396){
			sum+=1;
			Graphics2D g=image.createGraphics();
			Color c1=c[(int)(Math.random()*3)];
			g.setColor(c1);
			g.drawRect(x-10,y-10,20,20);
		}

		count=0;
	}
	
	/**
	* 图片二值化 去除大面积连续的(0,0,0)点
	*/
	public void remove(int x,int y){	
		int count=0;	
		//去除大面积黑色噪点
		for(int i=x-10;i<=x+10;i++){
			for(int j=y-10;j<=y+10;j++){
				if(gray[i][j]==1){
					count+=1;
				}			
			}
		}

		if(count>=360){
			for(int i=x-10;i<=x+10;i++){
				for(int j=y-10;j<=y+10;j++){
					gray1[i][j]=0;
				//	raster.setDataElements(i,j,colorData);
				}
			}
		}
		count=0;
	}

	//对角扫描
	public void diagonal(){
		int direction=0;
		int count=0;
		int x=10;
		int y=10;
		while(!(x==width-10&&y==height-10)){
		//先判断下一步往哪一个方向走 为0表示还没走出第一步 应往右走
		if(direction==0){
			direction=RIGHT;
		}
		else if(direction==RIGHT){
			//左下
			if(x-10>=0&&y+10<height){
				direction=LEFTDOWN;
			}
			//右上
			else{
				direction=RIGHTUP;
			}
		}

		else if(direction==DOWN){
			//左下
			if(x-10>=0&&y+10<height){
				direction=LEFTDOWN;
			}
			//右上
			else{
				direction=RIGHTUP;
			}
		}

		else if(direction==LEFTDOWN){
				//是否向左下
			if(y+10<height&&x-10>=0){
					direction=LEFTDOWN;
			}
			//是否向下
			else if(y+10<height){
				direction=DOWN;
			}
			//是否向右
			else{
				direction=RIGHT;
			}
		}

		else if(direction==RIGHTUP){
			//是否向右上
			if(x+10<width&&y-10>=0){
				direction=RIGHTUP;
			}

			//是否向右
			else if(x+10<width){
				direction=RIGHT;
			}
				//是否向下
			else{
				direction=DOWN;
			}
		}
		switch(direction){
			case RIGHT:x=x+10;break;
			case DOWN:y=y+10;break;
			case LEFTDOWN:x=x-10;y=y+10;break;
			case RIGHTUP:x=x+10;y=y-10;break;
		}

		for(int i=x-10;i<=x+10;i++){
			for(int j=y-10;j<=y+10;j++){
				if(gray[i][j]==1){
					count+=1;
				}			
			}
		}

		if(count>=360){
			for(int i=x-10;i<=x+10;i++){
				for(int j=y-10;j<=y+10;j++){
					gray1[i][j]=0;
					//raster.setDataElements(i,j,colorData);
				}
			}
		}

		count=0;
		}
	}


	public static int getGray(int rgb){
		Color c=new Color(rgb);
		int r=c.getRed();
		int g=c.getGreen();
		int b=c.getBlue();
		int top=(r+g+b)/3;
		return (int)(top);
	}

	public static int getAverageColor(int[][] pi,int x,int y,int width,int height){
		int rs=pi[x][y]
			+(x==0?255:pi[x-1][y])
			+(x==0||y==0?255:pi[x-1][y-1])
			+(x==0||y==height-1?255:pi[x-1][y+1])
			+(y==0? 255:pi[x][y-1])
			+(y==height-1?255:pi[x][y+1])
			+(x==width-1?255:pi[x+1][y])
			+(x==width-1||y==0?255:pi[x+1][y-1])
			+(x==width-1||y==height-1?255:pi[x+1][y+1]);
		return rs/9;
	}

	public static void main(String[] args){
		new RecognizeImage();
	}

}