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

public class VilageColorPane extends JPanel{
	private JLabel imageLabel;
	private VilagePane vilagePane;
	private ShowPane showPane;

	private JLabel label;

	public VilageColorPane(){
		setLayout(new BorderLayout());
		init();
	}

	public void init(){
		vilagePane=new VilagePane();
		vilagePane.setPreferredSize(new Dimension(getWidth(),getHeight()));
		add(vilagePane,BorderLayout.CENTER);
	//	System.out.println("*********"+getWidth()+"  "+getHeight());
		showPane=new ShowPane();
		showPane.setPreferredSize(new Dimension(200,getHeight()));
		add(showPane,BorderLayout.EAST);
	}
	
	class ShowPane extends JPanel{
		private JLabel redLabel;
		private JLabel greenLabel;
		private JLabel blueLabel;
		private JLabel highLabel;

		public ShowPane(){
			setLayout(null);
			init();
		}

		public void init(){
			redLabel=new JLabel("代表民居数量为0");
			redLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			redLabel.setBounds(30,10,150,30);
			add(redLabel);

			greenLabel=new JLabel("代表民居数量为1或2");
			greenLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			greenLabel.setBounds(30,50,150,30);
			add(greenLabel);			

			blueLabel=new JLabel("代表民居数量为3或4");
			blueLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			blueLabel.setBounds(30,90,150,30);
			add(blueLabel);

			highLabel=new JLabel("代表民居数量为7");
			highLabel.setFont(new Font("微软雅黑",Font.PLAIN,16));
			highLabel.setBounds(30,130,150,30);
			add(highLabel);
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d=(Graphics2D)g;
			g2d.setColor(new Color(70,30,20));
			g2d.fillRect(10,20,15,15);

			g2d.setColor(new Color(15,20,20));
			g2d.fillRect(10,60,15,15);

			g2d.setColor(new Color(20,70,13));
			g2d.fillRect(10,100,15,15);

			g2d.setColor(new Color(72,68,9));
			g2d.fillRect(10,140,15,15);
		}	
	}
}