package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Point;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BasicStroke;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import listener.LabelListener;
import downloadmap.*;
import clickimage.*;
import recognition.*;
/**
* @author tao
* @version 1.0
*/
public class ImagePane extends JPanel{
	private JLabel imageLabel;
	public static ImagePane imagePane;
	
	private Image image;
	private ArrayList fileList=new ArrayList();
	private Map<Integer,String> map=new HashMap<Integer,String>();
	private ArrayList<String> distanceList=new ArrayList<String>();

	private Point imagePosition=new Point(-256,-256);//图片当前位置
	private Point mouseStartPosition=new Point(0,0);  //每次拖拽开始时鼠标位置
	private Point imageStartPosition=new Point(0,0);  //每次拖拽开始图片位置
	
	private double EARTH_RADIUS=6378.137;
	private int numX=0;
	private int numY=0;
	private int count;
	private int index;
	private double endLatPixel;
	private double endLngPixel;
	private double x;
	private double y;
	private double differX;
	private double differY;
	private double latitude;
	private double longitude;
	private double distance;

	private String imagepath;
	public static String path;
	private JFileChooser chooser;
	private int flag=1;
	
	private BufferedImage newImage;
	private ShowDialog showDialog;

	public static ImagePane instance(){
		if(imagePane==null){
			imagePane=new ImagePane(path);
		}
		return imagePane;
	}


	public ImagePane(String path){
		this.path=path;	
		imagePane=this;
		listener();	
		addListener1();
		addListener2();
//		addListener3();

	}

	public void getImage(String imagepath){
		try{
			
			File[] images=new File(imagepath).listFiles();
			for(File file:images){
				String[] paths=file.getAbsolutePath().split("-");
				String[] index=paths[1].split("\\\\");
				int i=Integer.parseInt(index[1]);
				map.put(i,paths[2]+"-"+paths[3]);			
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void paintComponent(Graphics g){
		System.gc();
		super.paintComponent(g);
		newImage=new BufferedImage(256*9,256*8,BufferedImage.TYPE_INT_RGB);
		
		int new_width=0;
		int new_height=0;
		int[] imgRGB;
		int i=0;	
		for(int x=numX;x<=numX+8;x++){
			int j=0;
			File[] files=new File("Z:\\"+path).listFiles();
			if(x==files.length){
				break;
			}

			for(int y=numY;y<numY+8;y++){
				String filepath="Z:\\"+path+"\\"+"18-"+x;
				File[] files1=new File(filepath).listFiles();
				if(y==files1.length){
					break;
				}
				getImage(filepath);
				imagepath=filepath+"\\"+y+"-"+map.get(y);
				image=Toolkit.getDefaultToolkit().getImage(imagepath);
				g.drawImage(image,(int)imagePosition.getX()+256*i+1,(int)imagePosition.getY()+256*j+1,this);
				try{
					if(map.get(y)!=null){
						BufferedImage buffer=ImageIO.read(new File(imagepath));
					
						new_width=buffer.getWidth();
						new_height=buffer.getHeight();
						imgRGB=new int[(new_width*(i+1))*(new_height*(j+1))];
						imgRGB=buffer.getRGB(0,0,buffer.getWidth(),buffer.getHeight(),imgRGB,0,buffer.getWidth());

						newImage.setRGB(new_width*i,new_height*j,new_width,new_height,imgRGB,0,new_width);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				j+=1;				
			}
			i+=1;
			map.clear();
			new_height=0;
		}
	}

	public void addListener2(){
		ToolBarPane.hold.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				chooser=new JFileChooser("E:\\");
				
				FileNameExtensionFilter filter=new FileNameExtensionFilter(
		        "JPG,JPEG,GIF,bmp & PNG Images","jpg","gif","png","jpeg","bmp");
				chooser.setFileFilter(filter);
				int result=chooser.showSaveDialog(null);
				File file=new File(chooser.getSelectedFile().getAbsolutePath());
				if(result==JFileChooser.APPROVE_OPTION){
					
					System.out.println(chooser.getSelectedFile().getAbsolutePath());
					if(!file.exists()){
						try{
							file.createNewFile();						
						
							ImageIO.write(newImage,"png",file);
						} 
						catch(IOException e2){
							e2.printStackTrace();
						}
					}
					else{
						int i=JOptionPane.showConfirmDialog(null,"已存在相同的文件名,是否替换？","确认框",JOptionPane.YES_NO_OPTION);
						if(i==0){
							try{
								ImageIO.write(newImage,"png" ,file);
							} 
							catch(IOException e1){
								e1.printStackTrace();
							}
						}
					}

				}
			}
		});
	}

	public void addListener1(){
		ToolBarPane.path.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				flag=1;
				if(flag==1){
					listener1();
				}				
			}
		});
	
	}

	public void listener1(){
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton()==MouseEvent.BUTTON1&&flag==1){
					count+=1;
					x=e.getX();
					y=e.getY();
					
					differX=x-imagePosition.getX();
					differY=y-imagePosition.getY();
									
					latitude=Transform.pixelToLat((endLatPixel+differX),MapParameter.ZOOM);
					longitude=Transform.pixelToLng((endLngPixel+differY),MapParameter.ZOOM);

					distanceList.add(x+","+y+","+latitude+","+longitude);

					System.out.println(x+","+y+","+latitude+","+longitude);
					getPaint(distanceList);
				}
			
				else if(e.getButton()==MouseEvent.BUTTON3){
					JPopupMenu menu=new JPopupMenu();
					JMenuItem cancel=new JMenuItem("取消测距");
					menu.add(cancel);
					menu.show(e.getComponent(),e.getX(),e.getY());

					cancel.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){							
							distanceList.clear();
							repaint();
							flag=0;
							count=0;
							
						}
					});
				}
			}

		});
	}
	
	public void getPaint(ArrayList<String> list){
		Graphics g=getGraphics();							
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.RED);
		g2d.fillRect((int)x,(int)y,6,6);
		for(int i=1;i<list.size();i++){

			if(count>=2){

				g2d.setColor(Color.RED);
				g2d.fillRect((int)x,(int)y,6,6);
				String[] s1=list.get(i-1).split(",");

				String[] s2=list.get(i).split(",");
				double lat1=Double.parseDouble(s1[2]);
				double lng1=Double.parseDouble(s1[3]);
				double lat2=Double.parseDouble(s2[2]);
				double lng2=Double.parseDouble(s2[3]);
				double x1=Double.parseDouble(s1[0]);
				double y1=Double.parseDouble(s1[1]);
				double x2=Double.parseDouble(s2[0]);
				double y2=Double.parseDouble(s2[1]);
									
				g2d.setColor(new Color(192,75,199));
				g2d.setStroke(new BasicStroke(2.0f)); 
				g2d.drawLine((int)x1,(int)y1,(int)x2,(int)y2);	
				
				double s=getDistance(lat1,lng1,lat2,lng2);
				distance=s+distance;
			}
						
		}
		String s=distance+"公里";
		g2d.setColor(Color.RED);
		g2d.setFont(new Font("微软雅黑",Font.PLAIN,15));
		g2d.drawString(s,(int)x,(int)y);
				
		System.out.println(distance+"公里");
		distance=0;
	}

	public void listener(){
		addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e){
				mouseStartPosition=e.getPoint();
				imageStartPosition.setLocation(imagePosition.getLocation());
			}

			public void mouseClicked(MouseEvent e){
				try{
					getGeocode();
					if(e.getClickCount()==2){
						x=e.getX();
						y=e.getY();
						differX=x-imagePosition.getX();
						differY=y-imagePosition.getY();
									
						latitude=Transform.pixelToLat((endLatPixel-differX),MapParameter.ZOOM);
						longitude=Transform.pixelToLng((endLngPixel-differY),MapParameter.ZOOM);

						System.out.println(latitude+"   "+longitude);
						GoogleGeocoderUtil.getInstance().setType(2);
						GoogleGeocodeJSONBean bean=GoogleGeocoderUtil.getInstance().geocodeByAddress(latitude+","+longitude);
								
						showDialog=new ShowDialog(GoogleGeocoderUtil.region);
						showDialog.setLocation((int)x,(int)(y+100));
						repaint();
						count=0;

					}				
				}
				catch(Exception ee){
					ee.printStackTrace();
				}
			}

			public void mouseReleased(MouseEvent e){
			}

			public void mouseEntered(MouseEvent e){
			}

			public void mouseExited(MouseEvent e){
			}

		});

		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){
				Point point=e.getPoint();
				repaint();
				updateUI();
				//鼠标位置的偏移量
				double x=point.getX()-mouseStartPosition.getX();
				double y=point.getY()-mouseStartPosition.getY();

				//左右拖拽 图片的当前位置等于图片的起始位置加上鼠标位置的偏移量
				if(imagePosition.getX()<=-512){
					mouseStartPosition.setLocation(mouseStartPosition.getX()-256,mouseStartPosition.getY());
					x=point.getX()-mouseStartPosition.getX();
					y=point.getY()-mouseStartPosition.getY();
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
					numX+=1;
				}
				else{
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
				}

				if(imagePosition.getX()>=0){
					mouseStartPosition.setLocation(mouseStartPosition.getX()+256,mouseStartPosition.getY());
					x=point.getX()-mouseStartPosition.getX();
					y=point.getY()-mouseStartPosition.getY();
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
					numX-=1;
				}
				else{
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
				}

				//上下拖拽
				if(imagePosition.getY()<=-512){
					mouseStartPosition.setLocation(mouseStartPosition.getX(),mouseStartPosition.getY()-256);
					x=point.getX()-mouseStartPosition.getX();
					y=point.getY()-mouseStartPosition.getY();
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
					numY+=1;
				}
				else{
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
				}

				if(imagePosition.getY()>=0){
					mouseStartPosition.setLocation(mouseStartPosition.getX(),mouseStartPosition.getY()+256);
					x=point.getX()-mouseStartPosition.getX();
					y=point.getY()-mouseStartPosition.getY();
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
					numY-=1;
				}
				else{
					imagePosition.setLocation(imageStartPosition.getX()+x,imageStartPosition.getY()+y);
				}
				repaint();
				updateUI();
			}

			public void mouseMoved(MouseEvent e){

			}
		});
	}

	public void getGeocode(){
		System.out.println(imagepath);
		String[] split=imagepath.split("-");
		String s=split[3].substring(0,split[3].length()-4);
		String lat=split[2];
		String lng=s;

		double end_latitude=Double.parseDouble(lat);
		double end_longitude=Double.parseDouble(lng);

		endLatPixel=Transform.latToPixel(end_latitude,MapParameter.ZOOM);
		endLngPixel=Transform.lngToPixel(end_longitude,MapParameter.ZOOM);	

	}
	
	//测量距离
	private double rad(double d){
		return d*Math.PI/180.0;
	}
	
	/**
	*  根据两个位置经纬度 来计算两地的距离
	*/
	public double getDistance(double start_latitude,double start_longitude,
		double end_latitude,double end_longitude){

		double radLat1=rad(start_latitude);
		double radLat2=rad(end_latitude);

		double a=radLat1-radLat2;
		double b=rad(start_longitude)-rad(end_longitude);

		double s=2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+
			Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));

		s=s*EARTH_RADIUS;

		double distance=(double)Math.round(s*10000)/10000;
		return distance;
	}	
}