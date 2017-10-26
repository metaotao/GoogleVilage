package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Point;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JDialog;

/**
* @author tao
* @version 1.0
*/

class ShowDialog extends JDialog{
	private JLabel showLabel;
	private String info;
	public ShowDialog(String info){
		this.info=info;
		setSize(380,100);
		addLabel();
		setVisible(true);			
	}

	public void addLabel(){
		showLabel=new JLabel(info);
		showLabel.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,15));
		add(showLabel);
	}
}
	