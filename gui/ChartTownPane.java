package gui;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.FileOutputStream;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.renderer.category.LineAndShapeRenderer; 

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.util.ArrayList;
import sql.*;
/**
* @author tao
* @version 1.0
*/

public class ChartTownPane extends ChartPanel{
	private static DBConnection db=new DBConnection();
	public ChartTownPane(){
		super(createChart(createDataset()));
	}

	// 生成图表主对象JFreeChart
	public static JFreeChart createChart(DefaultCategoryDataset linedataset){
		JFreeChart chart = ChartFactory.createLineChart("村落民居数据显示","乡镇名","数量", // 纵坐标名称
			linedataset, // 数据
			PlotOrientation.VERTICAL, // 水平显示图像
			true, // include legend
			true, // tooltips
			false // urls
		);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true); //是否显示格子线
		plot.setBackgroundAlpha(0.3f); //设置背景透明度
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
	//	rangeAxis.setLowerMargin(0.20);// 左边距 边框距离
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI/1.0);

		LineAndShapeRenderer lineandshaperenderer=(LineAndShapeRenderer)plot.getRenderer();  
  
     //   lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见  
  
        lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见  

		Font font1 = new Font("SimSun",10,14); // 设定字体、类型、字号
		chart.getTitle().setFont(font1); // 标题
		Font font2 = new Font("SimSun",10,11); // 设定字体、类型、字号
		plot.getDomainAxis().setLabelFont(font2);// 相当于横轴或理解为X轴
	    plot.getRangeAxis().setLabelFont(font2);// 相当于竖轴理解为Y轴
	    Font font3 = new Font("SimSun",10,12); // 设定字体、类型、字号
		chart.getLegend().setItemFont(font3);
		CategoryAxis categoryaxis=plot.getDomainAxis(); // 横轴上的
		categoryaxis.setTickLabelFont(new Font("SansSerif",Font.PLAIN+Font.BOLD,9));// 设定字体、类型、字号
		
		categoryaxis.setLowerMargin(0.01);// 左边距 边框距离
		categoryaxis.setUpperMargin(0.01);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90); // 横轴上的  
		categoryaxis.setMaximumCategoryLabelLines(1);  
		return chart;
	}
	// 生成数据
	public static DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset linedataset=new DefaultCategoryDataset();
		String series ="村落民居";
	    ArrayList<String> townName=getTownName();

		for(int i=0;i<townName.size();i++){
			ArrayList<Integer> list=getVilageSum(townName.get(i));
			for(int j=0;j<list.size();j++){
				linedataset.addValue((double)list.get(j),series,townName.get(i));				
			}
			list.clear();
		}
		//list.clear();
	    return linedataset;
	}

	public static ArrayList<String> getTownName(){
		ArrayList<String> townList=new ArrayList<String>();
		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection1();
			String sql="select townname from quantity";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				townList.add(rs.getString(1));
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				db.close(rs,pre,conn);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return townList;
	}

	public static ArrayList<Integer> getVilageSum(String townName){
		ArrayList<Integer> vilageSum=new ArrayList<Integer>();
		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection1();
			String sql="select sum(sum) from quantity where townname='"+townName+"'";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				vilageSum.add(rs.getInt(1));
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				db.close(rs,pre,conn);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return vilageSum;
	}

}
