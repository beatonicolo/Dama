package progetto.dama;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Casella extends JPanel {
	
	private int x;
	private int y;
	private int colore; 	// 0=nero  1=bianco
	
	
	//la classe deve essere astratta con relativo actionlistener
	public Casella (int colore,int x,int y) {
		
		//super();
		this.x=x;
		this.y=y;
		this.colore = colore;
		
		if (this.colore == 0)
			this.setBackground(Color.decode("#3d2015"));
		else
			this.setBackground(Color.decode("#f9f9e0"));
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				Clicked(getTheX(),getTheY());
			}
		});
	}
	
	protected abstract void Clicked(int x, int y);
	
	public int getTheX() {
		return this.x;
	}
	
	public int getTheY() {
		return this.y;
	}
}
