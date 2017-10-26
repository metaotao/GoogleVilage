package gui;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Image;
/**
* @author tao
* @version 1.0
*/

public class ShowDataPane extends JPanel{
	private static ShowDataPane showDataPane;
	
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;

	private JLabel image_label1;
	private JLabel image_label2;
	private JLabel image_label3;

	private JLabel showVilageNum;
	private JLabel showResidenceNum;
	private JLabel showVilageNum_label;
	private JLabel showResidenceNum_label;

	public static ShowDataPane instance(){
		if(showDataPane==null){
			showDataPane=new ShowDataPane();
		}
		return showDataPane;
	}


	public ShowDataPane(){
		setLayout(new GridLayout(1,4,5,10));
		showDataPane=this;
		init();
	}

	public void init(){ 
		p1=new JPanel();
		p1.setBorder(new TitledBorder(null,"黄山地图全景缩略显示",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		add(p1);
	//	p1.setLayout(null);
		addComponent1();

		p2=new JPanel();
		p2.setBorder(new TitledBorder(null,"徽州地图全景缩略显示",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		add(p2);
		addComponent2();

		p3=new JPanel();
		p3.setBorder(new TitledBorder(null,"典型村落南溪南村地图显示",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		add(p3);
		addComponent3();

		p4=new JPanel();
		p4.setBorder(new TitledBorder(null,"黄山村落相关数据显示",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		add(p4);
		p4.setLayout(null);
		addComponent4();
	}

	public void addComponent1(){
		image_label1=new JLabel(getImageIcon("image\\huangshan.png",350,180));
		p1.add(image_label1);
	}

	public void addComponent2(){
		image_label2=new JLabel(getImageIcon("image\\huizhou.png",350,180));
		p2.add(image_label2);
	}

	public void addComponent3(){
		image_label3=new JLabel(getImageIcon("map\\南溪南村.png",350,180));
		p3.add(image_label3);
	}

	public void addComponent4(){
		showVilageNum=new JLabel("黄山市全部村落数量统计显示：");
		showVilageNum.setFont(new Font("微软雅黑",Font.PLAIN,16));
		showVilageNum.setBounds(20,50,250,30);
		p4.add(showVilageNum);

		showVilageNum_label=new JLabel("679");
		showVilageNum_label.setFont(new Font("微软雅黑",Font.PLAIN,16));
		showVilageNum_label.setOpaque(true);
		showVilageNum_label.setBackground(Color.YELLOW);
		showVilageNum_label.setBounds(100,80,100,30);
		p4.add(showVilageNum_label);

		showResidenceNum=new JLabel("黄山市全部村落民居数量统计显示：");
		showResidenceNum.setFont(new Font("微软雅黑",Font.PLAIN,16));
		showResidenceNum.setBounds(15,120,300,30);
		p4.add(showResidenceNum);

		showResidenceNum_label=new JLabel("310681");
		showResidenceNum_label.setFont(new Font("微软雅黑",Font.PLAIN,16));
		showResidenceNum_label.setOpaque(true);
		showResidenceNum_label.setBackground(Color.YELLOW);
		showResidenceNum_label.setBackground(Color.YELLOW);
		showResidenceNum_label.setBounds(100,150,100,30);
		p4.add(showResidenceNum_label);

	}


	public ImageIcon getImageIcon(String path,int width,int height){
		if(width==0||height==0){
			return new ImageIcon(path);
		}
		ImageIcon icon=new ImageIcon(path);
		icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
		return icon;
	}
}