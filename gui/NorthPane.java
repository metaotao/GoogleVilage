package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
/**
* @author tao
* @version 1.0
*/

public class NorthPane extends JPanel{
	public NorthPane(){
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon image=new ImageIcon("image//title.jpg");
		g.drawImage(image.getImage(),0,0,this.getWidth(),this.getHeight(),this);
	}
}