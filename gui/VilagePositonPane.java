package gui;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
/**
* @author tao
* @version 1.0
*/

public class VilagePositonPane extends JPanel{
	private JLabel imageLabel;
	private VilageImagePane vilagePane;
	private ShowPane showPane;

	private JLabel label;

	public VilagePositonPane(){
		setLayout(new BorderLayout());
		init();
	}

	public void init(){
		vilagePane=new VilageImagePane();
		vilagePane.setPreferredSize(new Dimension(getWidth(),getHeight()));
		add(vilagePane,BorderLayout.CENTER);

		showPane=new ShowPane();
		showPane.setPreferredSize(new Dimension(200,getHeight()));
		add(showPane,BorderLayout.EAST);
	}

	class VilageImagePane extends JPanel{
		public VilageImagePane(){
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			ImageIcon image=new ImageIcon("image//vilage.png");
			g.drawImage(image.getImage(),0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	
	class ShowPane extends JPanel{
		private JLabel redLabel;
		private JLabel greenLabel;

		public ShowPane(){
			setLayout(null);
			init();
		}

		public void init(){
			redLabel=new JLabel("带天井的村落");
			redLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			redLabel.setBounds(30,10,150,30);
			add(redLabel);

			greenLabel=new JLabel("不带天井的村落");
			greenLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			greenLabel.setBounds(30,50,150,30);
			add(greenLabel);			

			
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d=(Graphics2D)g;
			g2d.setColor(Color.RED);
			g2d.fillRect(10,20,15,15);

			g2d.setColor(Color.GREEN);
			g2d.fillRect(10,60,15,15);
		}	
	}
}