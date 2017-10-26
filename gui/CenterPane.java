package gui;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
/**
* @author tao
* @version 1.0
*/

public class CenterPane extends JPanel{
	private JTabbedPane tabbedPane1;	
	
	private InCenterPane inCenterPane;
	private InEastPane inEastPane;
	
	private ShowDataPane showDataPane;
	private ChartPane chartPane;

	private ChartTownPane chartTownPane;
	

	public static CenterPane centerPane;
	public static CenterPane instance(){
		if(centerPane==null){
			centerPane=new CenterPane();
		}
		return centerPane;
	}

	public CenterPane(){
		setLayout(new BorderLayout());	
		centerPane=this;

		init();
	}

	public void init(){
		
		inCenterPane=new InCenterPane();
		add(inCenterPane,BorderLayout.CENTER);

		inEastPane=new InEastPane();
		inEastPane.setParameter();
		inEastPane.setVilageSum("南溪南村");
		add(new JScrollPane(inEastPane),BorderLayout.EAST);
		
		tabbedPane1=new JTabbedPane();
		add(tabbedPane1,BorderLayout.SOUTH);

		showDataPane=new ShowDataPane();
		showDataPane.setPreferredSize(new Dimension(1280,220));

		ChartPane chartPane=new ChartPane();
		chartPane.setPreferredSize(new Dimension(1280,220));
		
		chartTownPane=new ChartTownPane();
		chartTownPane.setPreferredSize(new Dimension(1280,220));

		tabbedPane1.add("黄山地图显示",showDataPane);
		tabbedPane1.add("黄山地图区县数据显示",chartPane);
		tabbedPane1.add("黄山地图乡镇数据显示",chartTownPane);
	}
}
