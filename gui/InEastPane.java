package gui;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Image;

import java.util.ArrayList;

import sql.*;

/**
* @author tao
*/

public class InEastPane extends JPanel{
	private JPanel northPane;
	private JTabbedPane tabbedPane;	
	private JPanel p1;
	private JPanel p2;

	private JLabel title;
	private JLabel imageTitle;
	private JLabel imageLabel;

	public static JButton vilageButton=new JButton("开始进行村落民居识别");

	private JLabel vilageArea;
	private JLabel vilageSum;
	private JLabel vilageDensity;
	private JLabel vilageShape;
	private JLabel vilageNeighbour;
	private JLabel degree;
	private JLabel cultureRating;
	private JLabel vilageRadius;
	private JLabel vilageTime;
	private JLabel vilageStartTime;
	private JLabel vilageEndTime;
	private JLabel vilageSize;
	private JLabel peopleSum;
	private JLabel peopleFamily;

	private ButtonGroup group;
	private ButtonGroup vilageGroup;
	private ButtonGroup vilageGroup1;
	
	private JTextField peopleSumField;
	private JTextField peopleFamilyField;
	private JTextField vilageAreaField;
	private JTextField vilageSumField;
	private JTextField vilageDensityField;
	private JTextField vilageShapeField;
	private JTextField vilageNeighbourField;
	private JTextField degreeField;
	private JTextField cultureRatingField;
	private JTextField vilageRadiusField;
	private JTextField vilageTimeField;
	private JTextField vilageStartTimeField; 
	private JTextField vilageEndTimeField; 

	private JRadioButton button7;
	private JRadioButton button8;
	private JRadioButton button9;
	private JRadioButton button10;
	private JRadioButton button11;
	private JRadioButton button12;
	private JRadioButton button13;

	private GetParameter getParameter=new GetParameter();
	public static InEastPane inEastPane;

	public static InEastPane instance(){
		if(inEastPane==null){
			inEastPane=new InEastPane();
		}
		return inEastPane;
	}

	public InEastPane(){
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,500));
		northPane=new JPanel();
		add(northPane,BorderLayout.NORTH);
		inEastPane=this;
		tabbedPane=new JTabbedPane();
		add(tabbedPane,BorderLayout.CENTER);	
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		init();
	}

	public void init(){
		title=new JLabel("民居-村落参数输出");
		title.setFont(new Font("微软雅黑",Font.PLAIN+Font.BOLD,16));
		title.setPreferredSize(new Dimension(300,40));
		//northPane.add(title);

	//	centerPane.setLayout(null);
		
		p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(0,40,300,400);
		p1.setBorder(new TitledBorder(null,"村落民居识别设置与图像显示",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		addComponent1("南溪南村");

		p2=new JPanel();
		p2.setLayout(null);
		p2.setBounds(0,40,300,400);
		p2.setBorder(new TitledBorder(null,"古村落识别与数据参数",TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,new Font("微软雅黑",Font.PLAIN,16),Color.BLACK));
		addComponent2();

		tabbedPane.add("地图显示",new JScrollPane(p1));
		tabbedPane.add("参数输出",new JScrollPane(p2));
	}

	public void addComponent1(String str){
		imageTitle=new JLabel("村落图像小图显示：");
		setLabelColor(imageTitle);
		imageTitle.setBounds(20,40,200,30);
		p1.add(imageTitle,BorderLayout.NORTH);

		imageLabel=new JLabel(getImageIcon("map\\"+str+".png",230,280));
		imageLabel.setBounds(15,80,230,280);
		p1.add(imageLabel,BorderLayout.CENTER);

		vilageButton.setBounds(20,380,200,30);
		vilageButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
		p1.add(vilageButton,BorderLayout.SOUTH);
	}

	public void removeImage(){
		imageLabel.setIcon(null);
	}

	public ImageIcon getImageIcon(String path,int width,int height){
		if(width==0||height==0){
			return new ImageIcon(path);
		}
		ImageIcon icon=new ImageIcon(path);
		icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
		return icon;
	}

	public void addComponent2(){
		vilageArea=new JLabel("村落面积(m):");
		setLabelColor(vilageArea);
		vilageArea.setBounds(10,30,80,25);
		p2.add(vilageArea);
		
		vilageAreaField=new JTextField();
		vilageAreaField.setBounds(90,30,50,25);
		p2.add(vilageAreaField);

		vilageSum=new JLabel("民居数量:");
		setLabelColor(vilageSum);
		vilageSum.setBounds(155,30,60,25);
		p2.add(vilageSum);
		
		vilageSumField=new JTextField();
		vilageSumField.setBounds(240,30,50,25);
		p2.add(vilageSumField);

		peopleFamily=new JLabel("村落户数:");
		setLabelColor(peopleFamily);
		peopleFamily.setBounds(10,70,80,25);
		p2.add(peopleFamily);

		peopleFamilyField=new JTextField();
		peopleFamilyField.setBounds(90,70,50,25);
		p2.add(peopleFamilyField);

		peopleSum=new JLabel("村落人口数:");
		setLabelColor(peopleSum);
		peopleSum.setBounds(155,70,80,25);
		p2.add(peopleSum);

		peopleSumField=new JTextField();
		peopleSumField.setBounds(240,70,50,25);
		p2.add(peopleSumField);
		

		vilageDensity=new JLabel("村落密度:");
		setLabelColor(vilageDensity);
		vilageDensity.setBounds(10,110,80,25);
		p2.add(vilageDensity);

		vilageDensityField=new JTextField();
		vilageDensityField.setBounds(90,110,50,25);
		p2.add(vilageDensityField);

		vilageNeighbour=new JLabel("邻里密度:");
		setLabelColor(vilageNeighbour);
		vilageNeighbour.setBounds(155,110,80,25);
		p2.add(vilageNeighbour);

		vilageNeighbourField=new JTextField();
		vilageNeighbourField.setBounds(240,110,50,25);
		p2.add(vilageNeighbourField);

		degree=new JLabel("拥挤度:");
		setLabelColor(degree);
		degree.setBounds(10,150,80,25);
		p2.add(degree);

		degreeField=new JTextField();
		degreeField.setBounds(90,150,50,25);
		p2.add(degreeField);

		cultureRating=new JLabel("文明程度:");
		setLabelColor(cultureRating);
		cultureRating.setBounds(155,150,80,25);
		p2.add(cultureRating);

		cultureRatingField=new JTextField();
		cultureRatingField.setBounds(240,150,50,25);
		p2.add(cultureRatingField);

		vilageRadius=new JLabel("影响半径(m):");
		setLabelColor(vilageRadius);
		vilageRadius.setBounds(10,190,80,25);
		p2.add(vilageRadius);

		vilageRadiusField=new JTextField();
		vilageRadiusField.setBounds(90,190,50,25);
		p2.add(vilageRadiusField);

		vilageTime=new JLabel("村落演变时间:");
		setLabelColor(vilageTime);
		vilageTime.setBounds(10,230,100,25);
		p2.add(vilageTime);

		vilageStartTime=new JLabel("起始时间");
		vilageStartTime.setFont(new Font("微软雅黑",Font.PLAIN,13));
		vilageStartTime.setBounds(10,260,60,25);
		p2.add(vilageStartTime);
		
		vilageStartTimeField=new JTextField();
		vilageStartTimeField.setBounds(75,260,50,25);
	
		p2.add(vilageStartTimeField);

		vilageEndTime=new JLabel("终止时间");
		vilageEndTime.setFont(new Font("微软雅黑",Font.PLAIN,13));
		vilageEndTime.setBounds(140,260,60,25);
		p2.add(vilageEndTime);

		vilageEndTimeField=new JTextField();
		vilageEndTimeField.setBounds(205,260,50,25);
		
		p2.add(vilageEndTimeField);

		vilageSize=new JLabel("村落规模:");
		vilageSize.setBounds(10,300,80,25);
		setLabelColor(vilageSize);
		p2.add(vilageSize);
		
		vilageGroup=new ButtonGroup();
		button7=new JRadioButton("大",false);
		button7.setBounds(10,330,50,25);
		vilageGroup.add(button7);
		p2.add(button7);

		button8=new JRadioButton("中",true);
		button8.setBounds(80,330,50,25);
		vilageGroup.add(button8);
		p2.add(button8);

		button9=new JRadioButton("小",false);
		button9.setBounds(150,330,50,25);
		vilageGroup.add(button9);
		p2.add(button9);

		vilageShape=new JLabel("村落形状:");
		setLabelColor(vilageShape);
		vilageShape.setBounds(10,360,80,25);
		p2.add(vilageShape);

		vilageGroup1=new ButtonGroup();
		button10=new JRadioButton("方形",true);
		button10.setBounds(10,390,60,25);
		vilageGroup1.add(button10);
		p2.add(button10);

		button11=new JRadioButton("圆形",false);
		button11.setBounds(80,390,60,25);
		vilageGroup1.add(button11);
		p2.add(button11);

		button12=new JRadioButton("片状",false);
		button12.setBounds(150,390,60,25);
		vilageGroup1.add(button12);
		p2.add(button12);

		button13=new JRadioButton("带状",false);
		button13.setBounds(220,390,60,25);
		vilageGroup1.add(button13);
		p2.add(button13);
	}

	public void setLabelColor(JLabel label){
		label.setForeground(new Color(100,150,130));
		label.setFont(new Font("微软雅黑",Font.PLAIN+Font.BOLD,13));
	}

	public void setVilageSum(String str){
		int sum=getParameter.getVilageSum(str);
		System.out.println(sum+"********");
		vilageSumField.setText(sum+"");
	}

	public void setParameter(){
		ArrayList<String> list2=getParameter.getVilageData();
		int random2=(int)(Math.random()*list2.size());
		String[] split2=list2.get(random2).split(" ");
		
		vilageAreaField.setText(split2[0]);
		vilageDensityField.setText(split2[1]);
		vilageNeighbourField.setText(split2[2]);
		degreeField.setText(split2[3]);
		cultureRatingField.setText(split2[4]);
		vilageRadiusField.setText(split2[5]);
		//vilageSumField.setText(split2[6]);
		vilageStartTimeField.setText(split2[7]);
		vilageEndTimeField.setText(split2[8]);
		peopleSumField.setText(split2[11]);
		peopleFamilyField.setText(split2[12]);
		String str=split2[9];		
		
		if(!str.equals("中")){
			button8.setSelected(false);
			vilageGroup.clearSelection();
			if(button7.getText()==str){
				button7.setSelected(true);
			}
			else{
				button9.setSelected(true);
			}
		}
		String s=split2[10];
		if(!s.equals("方形")){
			button10.setSelected(false);
			vilageGroup1.clearSelection();
			if(button11.getText()==s){
				button11.setSelected(true);
			}
			else if(button12.getText()==s){
				button12.setSelected(true);
			}
			else{
				button13.setSelected(true);
			}
		}
	}
}

		