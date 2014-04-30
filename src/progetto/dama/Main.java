package progetto.dama;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
	JFrame wnd = new JFrame ();
	Scacchiera s = new Scacchiera();
	AI cpu = new AI(s);	
	wnd.setSize(800,800);
	wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	wnd.setLayout(new BorderLayout());
	wnd.add(s,BorderLayout.CENTER);
	wnd.setVisible(true);
	}
}
