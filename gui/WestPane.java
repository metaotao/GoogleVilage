package gui;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeCellRenderer;  
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import listener.TreeListener;
import sql.*;
import clickimage.*;
/**
* @author tao
* @version 1.0
*/
public class WestPane extends JPanel{
	private JPanel pane1;
	private JPanel pane2;	

	private JLabel searchLabel;
	private JButton searchButton;
	private JScrollPane scrollPane;
	private JTree tree;
	private DefaultTreeModel model;
	private String[] childNode;
	public static WestPane westPane;
	public static int count=0;
	public static JTextField search;
	private SelectArea name=new SelectArea();
	
	private ArrayList<String> areaList=new ArrayList<String>();
	private ArrayList<String> vilageList=new ArrayList<String>();
	private Set<String> townSet=new HashSet<String>();
	public static String path="";

	private int judge;
	private int clickNum;
	public static WestPane instance(){
		if(westPane==null){
			westPane=new WestPane();
		}
		return westPane;
	}

	public WestPane(){
		setLayout(new BorderLayout(10,10));
		westPane=this;
		init();
		getTree();
		listener();
	}

	public void init(){
		pane1=new JPanel();
		pane1.setLayout(new BorderLayout(10,10));
		add(pane1,BorderLayout.NORTH);

		search=new JTextField();	
		search.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
		pane1.add(search,BorderLayout.CENTER);
		
		searchButton=new JButton("ËÑË÷");
		searchButton.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
		pane1.add(searchButton,BorderLayout.EAST);
		
	}

	public void getTree(){
		pane2=new JPanel();
		pane2.setLayout(new BorderLayout());
		add(new JScrollPane(pane2),BorderLayout.CENTER);
		JTree tree=createTree();
		TreeListener listener=new TreeListener(tree);
	}

	public String getText(){
		return search.getText();
	}

	public void listener(){
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String str=getText();
				InCenterPane.instance().removeImagePanel();	
				if(InCenterPane.imagePane!=null){
					InCenterPane.instance().removeImagePane();
				}
				InCenterPane.instance().addImagePanel();
				ImagePanel.instance().addImage(str);
				InEastPane.instance().removeImage();
				InEastPane.instance().addComponent1(str);
				InEastPane.instance().setVilageSum(str);
				ImagePanel.instance().validate();
				ImagePanel.instance().repaint();
				ImagePanel.instance().setVisible(true);
							
				InEastPane.instance().setParameter();
			
			}
		});
	}

	public void getArea(){
		try{
			ResultSet rs=name.executeQuery("select county from nation_division where city='»ÆÉ½ÊÐ' and start_co_longitude>0");
			while(rs.next()){
				areaList.add(rs.getString(1));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void getTown(String area){	
		try{
			ResultSet rs=name.executeQuery("select townname from quantity where areaname='"+area+"'");
			while(rs.next()){
				townSet.add(rs.getString(1));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void getVilage(String townname){	
		try{
			ResultSet rs=name.executeQuery("select vilagename from quantity where townname='"+townname+"'");
			while(rs.next()){
				vilageList.add(rs.getString(1));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	//Éú³ÉÊ÷

	private JTree createTree(){
		getArea();
		DefaultMutableTreeNode node=new DefaultMutableTreeNode("È«ÇòÍ¼Ïñ");
		DefaultMutableTreeNode node1=new DefaultMutableTreeNode("ÖÐ¹ú");
		DefaultMutableTreeNode node2=new DefaultMutableTreeNode("°²»ÕÊ¡");
		DefaultMutableTreeNode node3=new DefaultMutableTreeNode("»ÆÉ½ÊÐ");
		DefaultMutableTreeNode root=new DefaultMutableTreeNode();
		DefaultMutableTreeNode[] node4=getNode(areaList);
		
		add(root,node);
		add(node,node1);
		add(node1,node2);
		add(node2,node3);
		add(node3,node4);
		
		for(int i=0;i<areaList.size();i++){
			getTown(areaList.get(i));
			int index=0;
			for(String townname:townSet){
				DefaultMutableTreeNode node5=new DefaultMutableTreeNode(townname);
				node4[i].add(node5);
				getVilage(townname);
				DefaultMutableTreeNode[] node6=getNode(vilageList);
				add(node5,node6);
				vilageList.clear();
			}
			vilageList.clear();
			townSet.clear();

		}

		JTree tree=new JTree(root);
		tree.setRootVisible(false);
		tree.setRowHeight(25); 
		tree.setBackground(new Color(240,240,240));
		tree.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,16));
		tree.setCellRenderer(new MyDefaultTreeCellRenderer());
				
		TreePath treePath=new TreePath(node);
		expandTree(tree,root);
		pane2.add(tree,BorderLayout.WEST);
		updateUI();

		return tree;
	}
	

	public void expandTree(JTree tree,DefaultMutableTreeNode root){
		Enumeration<?> enumeration=root.preorderEnumeration();
		while(enumeration.hasMoreElements()){
			DefaultMutableTreeNode node=(DefaultMutableTreeNode)enumeration.nextElement();
			if(!node.isLeaf()){
				TreePath path=new TreePath(node.getPath());
				tree.expandPath(path);
			}
		}
	}

	public DefaultMutableTreeNode[] getNode(ArrayList<String> list){
		DefaultMutableTreeNode[] nodes=new DefaultMutableTreeNode[list.size()];
		int index=0;
		for(String name:list){
			nodes[index]=new DefaultMutableTreeNode(name);
			index+=1;
		}
		return nodes;
	}

	private void add(DefaultMutableTreeNode node,DefaultMutableTreeNode[] nodes){
		for(DefaultMutableTreeNode n:nodes){
			node.add(n);
		}
	}

	private void add(DefaultMutableTreeNode node,DefaultMutableTreeNode n){
		node.add(n);
	}

	public DefaultMutableTreeNode getNode(String name){
		return new DefaultMutableTreeNode(name);
	}

	class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer{
		private static final long serialVersionUID=1L;
		/**
		* ÖØÐ´¸¸Àà·½·¨
		*/
		public Component getTreeCellRendererComponent(JTree tree,Object value,
			boolean selected,boolean expanded,boolean leaf,int row,boolean hasFocus){  
			super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);    
			 
			String value1=value.toString();
			if(value1=="È«ÇòÍ¼Ïñ"){  
				setIcon(new ImageIcon("image//earth.jpg"));  
			}  
			return this;  
		}
	}
}