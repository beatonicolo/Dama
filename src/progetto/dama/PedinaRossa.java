package progetto.dama;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PedinaRossa extends Pedina {
	
	public PedinaRossa (int x,int y) {
		
		super(x,y,new ImageIcon("images/pedina.png"));
	}
	
	@Override
	public boolean equals (Object other) {
		
		return other instanceof PedinaRossa ;
	}
}
