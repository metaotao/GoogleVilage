package listener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import gui.*;

import sql.*;
import recognition.*;
/**
* @author tao
* version 1.0
*/

public class TreeListener{
	private JTree tree;
	private int count;
	private String path="安徽省\\黄山市\\";
	private GetParameter getParameter=new GetParameter();
	public TreeListener(JTree tree){
		this.tree=tree;
		listener();
	}

	public void listener(){
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				String str=null;
				TreePath path=tree.getSelectionPath();
				if(path==null) return;
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
				str=node.toString();
				System.out.println(str);
				
				if(str.contains("村")){
					InCenterPane.instance().removeImagePanel();
					if(InCenterPane.imagePane!=null){
						InCenterPane.instance().removeImagePane();
					}
					ImagePanel.instance().removeAll();
					InCenterPane.instance().addImagePanel();
					ImagePanel.instance().addImage(str);
					InEastPane.instance().setVilageSum(str);
					
					InEastPane.instance().setParameter();
					InEastPane.instance().removeImage();
					InEastPane.instance().addComponent1(str);
					ImagePanel.instance().validate();
					ImagePanel.instance().repaint();
					ImagePanel.instance().setVisible(true);
					
				}
				if(str.contains("区")||str.contains("县")){
					String filepath="安徽省\\黄山市\\"+str;
					System.out.println(filepath);
					InCenterPane.instance().removeImagePanel();	
					
					InCenterPane.instance().addImagePane(filepath);
					InCenterPane.instance().repaint();
					InCenterPane.instance().validate();
					ImagePane.instance().repaint();
					ImagePane.instance().validate();
				}
			}
		});
	}
}