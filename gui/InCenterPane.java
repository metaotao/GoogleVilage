package gui;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
/**
* @author tao
* @version 1.0
*/

public class InCenterPane extends JPanel{
	public static InCenterPane inCenterPane;

	private ToolBarPane barPane;
	public static ImagePane imagePane;
	private ImagePanel imagePanel;
	public static InCenterPane instance(){
		if(inCenterPane==null){
			inCenterPane=new InCenterPane();
		}
		return inCenterPane;
	}

	public InCenterPane(){
		setLayout(new BorderLayout());	
		inCenterPane=this;

		init();
	}

	public void init(){
		barPane=new ToolBarPane();
		inCenterPane.add(barPane,BorderLayout.NORTH);	
		imagePanel=new ImagePanel();
		imagePanel.addImage("南溪南村");
		InEastPane.instance().addComponent1("南溪南村");
		add(imagePanel,BorderLayout.CENTER);
		//InEastPane.instance().setParameter();
		//ResultPane.instance().addPane();
	}
	
	public void addImagePanel(){
		imagePanel=new ImagePanel();
		add(imagePanel,BorderLayout.CENTER);
	}

	public void removeImagePanel(){
		remove(imagePanel);
	}

	public void addImagePane(String path){	
		imagePane=new ImagePane(path);
		add(imagePane,BorderLayout.CENTER);	
		System.out.println("添加成功");
			
	}

	public void removeImagePane(){
		remove(imagePane);
	}
}

