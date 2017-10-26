package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import listener.*;
/**
* @author tao
* version 1.0
*/

public class ToolBarPane extends JToolBar{
	private JButton hide;
	public static JButton hold;
	public static JButton path;

	private ImageIcon holdImage;
	private JLabel label;
	private int count;
	public ToolBarPane(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		addSeparator();
		init();
		listener();

	}

	public void init(){
		hide=new JButton("����");
		hide.setBackground(new Color(210,240,240));
		hide.setFont(new Font("΢���ź�",Font.PLAIN,15));
		hide.setToolTipText("���ز���");
		add(hide);

		hold=new JButton("����");
		hold.setBackground(new Color(210,240,240));
		hold.setFont(new Font("΢���ź�",Font.PLAIN,15));
		hold.setToolTipText("����ͼƬ");
		add(hold);

		path=new JButton("·��");
		path.setBackground(new Color(210,240,240));
		path.setFont(new Font("΢���ź�",Font.PLAIN,15));
		path.setToolTipText("�½�·��");
		add(path);

		
	}
	
	public void listener(){
		hide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count+=1;
				if(count%2!=0){
					MainFrame.instance().removeWestPane();
				}
				else{
					MainFrame.instance().addWestPane();
				}
			}
		});
	}
}