package listener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

import gui.*;
/**
* @author tao
* @version 1.0
*/

public class ToolbarListener{
	private JButton hide;
	private JButton hold;
	private JButton show;
	private JButton path;
	private JButton landmark;
	private JButton shape;
	
	private JLabel landLabel;
	private int count;
	public ToolbarListener(JButton hide,JButton hold,JButton show,JButton path,JButton landmark,JButton shape){
		this.hide=hide;
		this.hold=hold;
		this.show=show;
		this.path=path;
		this.landmark=landmark;
		this.shape=shape;
		listener();
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