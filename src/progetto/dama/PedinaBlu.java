package progetto.dama;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PedinaBlu extends Pedina {
	
	public PedinaBlu (int x, int y) {
		
		super(x,y,new ImageIcon("images/pedinab.png"));
	}

	@Override
	public boolean equals (Object other) {
		
		return other instanceof PedinaBlu ;
	}
	
}
