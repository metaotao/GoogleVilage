package main;
import javax.swing.UIManager;

import gui.MainFrame;
/**
* @author tao
* @version 1.0
*/
public class Main{
	public static void main(String[] args){	
		try{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new MainFrame();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}