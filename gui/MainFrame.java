package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Image;

/**
* @author tao
* @version 1.0
*/
public class MainFrame extends JFrame{
	private NorthPane northPane;
	private WestPane westPane;
	private CenterPane centerPane;
	private VilageColorPane vilageColorPane;
	private VilagePositonPane vilagePositonPane;

	private JPanel southPane;
	private InEastPane inEastPane;
	private JPanel inSouthPane;

	private JSplitPane splitPane;
	private JSplitPane centerSplitPane;
	private JSplitPane inSplitPane;
	private JTabbedPane tabbedPane;

	private ImageIcon image;
	private JLabel imageLabel;
	private JLabel titleLabel;
	private JLabel tagLabel;

	private Dimension dim;
	
	public static MainFrame f;
	public static MainFrame instance(){
		if(f==null){
			f=new MainFrame();
		}
		return f;
	}
	public MainFrame(){
		setTitle("全球图像村落-民居可识别系统");
		setLayout(new BorderLayout());
		dim=Toolkit.getDefaultToolkit().getScreenSize();
		int width=dim.width;
		int height=dim.height;
		setSize(width,height);
		System.out.println(width+"  "+height);
		f=this;
		init();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void init(){
		northPane=new NorthPane();
		northPane.setPreferredSize(new Dimension(getWidth(),145));
		add(northPane,BorderLayout.NORTH);

		southPane=new JPanel();
		southPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(southPane,BorderLayout.SOUTH);
		
		tagLabel=new JLabel("Copyright 2015-2016 LanBao Tech Team");
		tagLabel.setFont(new Font("微软雅黑",Font.PLAIN,14));
		southPane.add(tagLabel);

		tabbedPane=new JTabbedPane();
		centerPane=new CenterPane();
		vilageColorPane=new VilageColorPane();
		westPane=new WestPane();
		vilagePositonPane=new VilagePositonPane();

		tabbedPane.add("村落民居识别",centerPane);
		tabbedPane.add("黄山村落分布显示",vilagePositonPane);
		tabbedPane.add("黄山村落密集度显示",vilageColorPane);
		getSplitPane();
	}

	public void getSplitPane(){
		
		splitPane=new JSplitPane();
		add(splitPane,BorderLayout.CENTER);
		setSplitPane(splitPane);
		splitPane.setDividerLocation(240);
		splitPane.setLeftComponent(westPane);
		splitPane.setRightComponent(tabbedPane);
	}

	public void setSplitPane(JSplitPane splitPane){
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerSize(10); 		
	}

	public void setLayout(JPanel pane){
		pane.setLayout(new BorderLayout());
	}

	public void removeWestPane(){
		splitPane.remove(westPane);
	}

	public void addWestPane(){
		splitPane.setLeftComponent(westPane);
		splitPane.setDividerLocation(240);
	}
		
}