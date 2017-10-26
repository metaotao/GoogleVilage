package listener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Point;

import gui.ImagePanel;
/**
* @author tao
* @version 1.0
*/

public class LabelListener{
	private JLabel label;

	boolean inTheImage=false;	
	private Point po1=new Point(0,0);
	private int width;
	private int height;
	private int image_X;
	private int image_Y;
	
	private int pane_width;
	private int pane_height;

	public LabelListener(JLabel label,int width,int height){
		this.label=label;
		this.width=width;
		this.height=height;
		listener();
	}

	public void listener(){
		label.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				//检测落点是否在图片上
				po1=SwingUtilities.convertPoint(label,e.getPoint(),label.getParent());
				inTheImage=true;				
			}

			public void mouseReleased(MouseEvent e){
				inTheImage=false;
			}

			public void mouseCilcked(MouseEvent e){
				if(e.getClickCount()==2){
					//ImagePane.instance().addImage("image//1.jpg");
					System.out.println("sdfskjdf");
				}
			}

		});

		label.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){
				Point po2=SwingUtilities.convertPoint(label,e.getPoint(),label.getParent());

				int x=label.getX()+po2.x-po1.x;
				int y=label.getY()+po2.y-po1.y;
				if(inBounds(x,y)){
					label.setLocation(x,y);
				}
				po1=po2;
				
			}

			public void mouseMoved(MouseEvent e){
			}
		});
	}

	/**
	* 图片不出现白边
	*/

	public boolean inBounds(int x,int y){
		pane_width=ImagePanel.instance().getWidth();
	    pane_height=ImagePanel.instance().getHeight();
		if(x<=0&&x>=(pane_width-width)&&y<=0&&y>=(pane_height-height)){
			return true;
		}
		else{
			return false;
		}
	}
}