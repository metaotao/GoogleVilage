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

public class ChartPane extends ChartPanel{
	private static DBConnection db=new DBConnection();
	private static ArrayList<Integer> list=new ArrayList<Integer>();
	public ChartPane(){
		super(createChart(createDataset()));
	}

	// ����ͼ��������JFreeChart
	public static JFreeChart createChart(DefaultCategoryDataset linedataset){
		JFreeChart chart = ChartFactory.createLineChart("�������������ʾ","������","����", // ����������
			linedataset, // ����
			PlotOrientation.VERTICAL, // ˮƽ��ʾͼ��
			true, // include legend
			true, // tooltips
			false // urls
		);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true); //�Ƿ���ʾ������
		plot.setBackgroundAlpha(0.3f); //���ñ���͸����
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI/1.0);

		LineAndShapeRenderer lineandshaperenderer=(LineAndShapeRenderer)plot.getRenderer();  
  
        lineandshaperenderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�  
  
        lineandshaperenderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�  

		Font font1 = new Font("SimSun",10,14); // �趨���塢���͡��ֺ�
		chart.getTitle().setFont(font1); // ����
		Font font2 = new Font("SimSun",10,14); // �趨���塢���͡��ֺ�
		plot.getDomainAxis().setLabelFont(font2);// �൱�ں�������ΪX��
	    plot.getRangeAxis().setLabelFont(font2);// �൱���������ΪY��
	    Font font3 = new Font("SimSun",10,12); // �趨���塢���͡��ֺ�
		chart.getLegend().setItemFont(font3);
		CategoryAxis categoryaxis=plot.getDomainAxis(); // �����ϵ�
		categoryaxis.setTickLabelFont(new Font("SansSerif",10,12));// �趨���塢���͡��ֺ�

		return chart;
	}
	// ��������
	public static DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset linedataset=new DefaultCategoryDataset();
		String series ="�������";
	    String[] str={"��Ϫ��","������","������", "���","��ɽ��", "������","����"};

		for(int i=0;i<str.length;i++){
			getVilageSum(str[i]);
			for(int j=0;j<list.size();j++){
				linedataset.addValue((double)list.get(j),series,str[i]);				
			}
			list.clear();
		}
		list.clear();
	    return linedataset;
	}

	public static void getVilageSum(String area){
		DBConnection db=new DBConnection();
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null;
		try{
			conn=db.getConnection1();
			String sql="select sum(sum) from quantity where areaname='"+area+"'";
			pre=conn.prepareStatement(sql);
			rs=pre.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
				
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
	}

}
