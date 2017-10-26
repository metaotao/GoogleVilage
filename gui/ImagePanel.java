package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import listener.LabelListener;
import sql.*;
import recognition.*;
/**
* @author tao
* @version 1.0
*/
public class ImagePanel extends JPanel{
	private JLabel imageLabel;
	public static ImagePanel imagePane;
	public static BufferedImage image;
	private ImageIcon icon;
	private JFileChooser chooser;
	public static int index=0;
	public static ImagePanel instance(){
		if(imagePane==null){
			imagePane=new ImagePanel();
		}
		return imagePane;
	}
	public ImagePanel(){
		imagePane=this;
		
		addListener2();
	}
		
	public void addImage(String str){
		try{
			image=ImageIO.read(new File("map//"+str+".png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		updateUI();
		icon=new ImageIcon(image);
		imageLabel=new JLabel(icon);
		LabelListener listener=new LabelListener(imageLabel,icon.getIconWidth(),icon.getIconHeight());
		add(imageLabel);
		index=0;
		listener(str);
		System.out.println("map//"+str+".png");
		updateUI();
		repaint();
	}

	public BufferedImage getImage(){
		return image;
	}

	public void listener(String str){
		InEastPane.vilageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(index==0){
					RecognizeImage.instance().getImage(image);	
					index+=1;
					repaint();
					updateUI();
					validate();
				}				
					
			}
		});		
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
						
							ImageIO.write(image,"png",file);
						} 
						catch(IOException e2){
							e2.printStackTrace();
						}
					}
					else{
						int i=JOptionPane.showConfirmDialog(null,"已存在相同的文件名,是否替换？","确认框",JOptionPane.YES_NO_OPTION);
						if(i==0){
							try{
								ImageIO.write(image,"png" ,file);
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

	public void removeImage(){
		imageLabel.setIcon(null);
	}
}