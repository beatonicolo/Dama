package progetto.dama;

import javax.swing.ImageIcon;

public class DamoneBlu extends Pedina {

	public DamoneBlu (int x, int y) {
		super(x,y,new ImageIcon("images/damoneBlu.png"));
	}
	
	@Override
	public boolean equals (Object other) {
		
		return other instanceof DamoneBlu;
	}
}
