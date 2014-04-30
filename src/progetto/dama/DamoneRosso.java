package progetto.dama;

import javax.swing.ImageIcon;

	public class DamoneRosso extends Pedina {
		public DamoneRosso (int x, int y) {
		super(x,y,new ImageIcon("images/damoneRosso.png"));
	}
	
	@Override
	public boolean equals (Object other) {
		
		return other instanceof DamoneRosso;
	}
}
