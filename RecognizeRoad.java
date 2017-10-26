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

public class RecognizeRoad extends JFrame{
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
	private static RecognizeRoad recognizeImage;

	private Color[] c={Color.red,Color.blue,Color.green};
	public static RecognizeRoad instance(){
		if(recognizeImage==null){
			recognizeImage=new RecognizeRoad();
		}
		return recognizeImage;
	}
	public RecognizeRoad(){	
		
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
					int pixel=image.getRGB(i,j);
					rgb[0]=(pixel>>16)&0xff;
					rgb[1]=(pixel>>8)&0xff;
					rgb[2]=(pixel)&0xff;
					
					if(rgb[0]>144&&rgb[1]>144&&rgb[2]>144&&rgb[0]<240&&rgb[1]<240&&rgb[2]<240
						&&rgb[1]-rgb[2]<=10){
					//	Graphics2D g=image.createGraphics();
						//g.setColor(Color.red);
						image.setRGB(i,j,255);
					}
					else{
						image.setRGB(i,j,-123456);
					}

				}
			}
		}
			
		catch(Exception e){
			e.printStackTrace();
		}
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
		new RecognizeRoad();
	}

}