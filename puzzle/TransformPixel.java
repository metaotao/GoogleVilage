package puzzle;
import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import downloadmap.Transform;
/**
* @author tao
* @version 1.0
*/

public class TransformPixel extends JFrame{
	private String filePath="E:\\0-29.782621000000006_118.18549300000001.png";
	private File file=new File(filePath);
	private double lngStartPixel;
	private double latStartPixel;
	private XMLReaderFile XMLFile=new XMLReaderFile();
	private BufferedImage image;
	private JLabel label;
	public TransformPixel(){
		setLayout(new BorderLayout());
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int width1=dim.width;
		int height1=dim.height;
		setSize(width1,height1);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		transform();
		setVisible(true);
	}

	public String getName(){
		int index1=filePath.lastIndexOf("-");
		int index2=filePath.lastIndexOf(".");

		String getName=filePath.substring(index1+1,index2);
		String name=getName.replaceAll("_"," ");
		return name;

	}

	public void transform(){
		try{
			image=ImageIO.read(file);

			int width=image.getWidth();
			int height=image.getHeight();

			System.out.println(width+"  "+height);
			String s=getName();
			String[] str=s.split(" ");

			double latitude=Double.parseDouble(str[0]);
			double longitude=Double.parseDouble(str[1]);

			latStartPixel=Transform.latToPixel(latitude,13);			
			lngStartPixel=Transform.lngToPixel(longitude,13);

			System.out.println(longitude+"   "+latitude);

			ArrayList<String> list=XMLFile.getRange();
			ArrayList<Integer> imageList1=new ArrayList<Integer>();
			ArrayList<Integer> imageList2=new ArrayList<Integer>();
			double maxLng=0,maxLat=0,minLng=0,minLat=0;
			for(int i=0;i<list.size();i++){
				String s1=list.get(i);
				String[] ss=s1.split(" ");

				double lng=Double.parseDouble(ss[0]);
				double lat=Double.parseDouble(ss[1]);
				
				if(lng>maxLng){
					maxLng=lng;
				}
				double latPixel=Transform.latToPixel(lat,13);			
				double lngPixel=Transform.lngToPixel(lng,13);

				int lng_differ=(int)(lngPixel-lngStartPixel);
				int lat_differ=(int)(latPixel-latStartPixel);

				System.out.println(lng_differ+"  "+lat_differ);

				if(lng_differ<=width&&lat_differ<=height){
					imageList1.add(lng_differ);
					imageList2.add(lat_differ);
					
				}
			/*	if(lat>latitude||lng<longitude||(lat>latitude&&lng<longitude)){
					list.remove(i);
				}*/
			}
			
			System.out.println("%%%%%%%"+maxLng);
			for(int i=0;i<imageList1.size();i++){
				if((i+1)>=imageList1.size()){
					break;
				}
				Graphics2D g2d = image.createGraphics();
				g2d.drawLine(imageList1.get(i),imageList2.get(i),imageList1.get(i+1),imageList2.get(i+1));
				
			}
			add(new JScrollPane(new JLabel(new ImageIcon(image))));
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	public static void main(String[] args){
		new TransformPixel();
	}
}