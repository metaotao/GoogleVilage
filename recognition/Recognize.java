//package clickimage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* @author tao
* @version 1.0
*/

public class Recognize extends JFrame{
	private BufferedImage image;
	private int width;
	private int height;
	private int[][] gray;
	private int[][] pi;
	private int count;
	private int sum;
	private int index;

	WritableRaster raster=null;
	ColorModel model=null;
	Color fractalColor=null;
	int argb=0;
	Object colorData=null;

	ColorModel model1=null;
	Color fractalColor1=null;;
	int argb1;
	Object colorData1=null;

	public Recognize(){
		image=getImage("E://nan.png");
		add(new JScrollPane(new JLabel(new ImageIcon(image))));
		setSize(1200,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public BufferedImage getImage(String file){
		BufferedImage image=null;
		try{
			image=ImageIO.read(new File(file));

			width=image.getWidth();
			height=image.getHeight();

			int minX=image.getMinX();
			int minY=image.getMinY();

			pi=new int[width][height];
			gray=new int[width][height];
			System.out.println(width+"  "+height);
			
			raster=image.getRaster();
			model=image.getColorModel();
			fractalColor=new Color(0,0,0);
			argb=fractalColor.getRGB();
			colorData=model.getDataElements(argb,null);

			
			
			model1=image.getColorModel();
			fractalColor1=new Color(255,255,255);
			argb1=fractalColor1.getRGB();
			colorData1=model1.getDataElements(argb1,null);
			
			//�趨��ֵ
			int sw=100;
			long startTime=System.currentTimeMillis();

			for(int i=minX;i<width;i++){
				for(int j=minY;j<height;j++){
					/**
					* ��ȡͼ�����أ�ȥ���࣬���Ϊ(0,0,0)
					*/
					pi[i][j]=getGray(image.getRGB(i,j));
					
				}
			}
			
			for(int i=minX;i<width;i++){
				for(int j=minY;j<height;j++){
					if(getAverageColor(pi,i,j,width,height)>sw){
						//�׵�
						gray[i][j]=0;
				//		raster.setDataElements(i,j,colorData1);
					}
					else{
						//�ڵ�
						gray[i][j]=1;
			//			raster.setDataElements(i,j,colorData);
					}
				}
			}


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
		}
			
		catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}


	
	/**
	* ͼƬ��ֵ�� ȥ�������������(0,0,0)��
	*/
	public void remove(int x,int y){
		
		int count=0;
	
		//ȥ���������ɫ���
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
					gray[i][j]=0;
					raster.setDataElements(i,j,colorData1);
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
		new Recognize();
	}

}