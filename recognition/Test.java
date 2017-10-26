import java.io.IOException;
import java.io.File;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.imageio.ImageIO;

public class Test{
	//图像灰度化
	private BufferedImage image;
	private BufferedImage newImage;
	private int width;
	private int height;
	private double sum;

	public Test(){
		try{
			image=ImageIO.read(new File("E:\\nan.png"));
			width=image.getWidth();
			height=image.getHeight();
			getImage();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private int colorToRGB(int alpha, int red, int green, int blue){
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		return newPixel;
	}

	public void getImage(){
		try{
			BufferedImage newImage=new BufferedImage(width,height,image.getType());
			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					int color =image.getRGB(i,j);
					int r=(color>>16)&0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					int gray=(int)(0.3*r+0.59*g+0.11*b);;
					int newPixel=colorToRGB(255,gray,gray,gray);
					newImage.setRGB(i, j, newPixel);
				}
			}

			ImageIO.write(newImage,"png",new File("E:\\gray.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}



	/**
	*  图像灰度化 并进行熵的计算
	*/

	public void graying(){	
		int[] sgray=new int[256];
		for(int i=0;i<256;i++){
			sgray[i]=0;
		}
		
		
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){

				int color=image.getRGB(i,j);
				int r=(color>>16)&0xff;
				int g=(color>>8)&0xff;
				int b=color&0xff;

				int gray=(int)(0.3*r+0.59*g+0.11*b);
				//System.out.println("灰度值："+gray);
				sgray[gray]++;
				
			}
		}
		for(int i=0;i<256;i++){
			if(sgray[i]!=0){

				//每一灰度值出现的概率
				double p=sgray[i]*1.0/(width*height);
				//熵
				sum+=p*(Math.log(1/p)/Math.log(2));
			}
		}
		System.out.println("该图片灰度值的熵为："+sum);	
	}

	public static void main(String[] args){
		new Test();
	}
}