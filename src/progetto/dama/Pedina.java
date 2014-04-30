package progetto.dama;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Pedina extends JLabel {
	
	private int x;
	private int y;
	
	public Pedina (int x, int y,ImageIcon image) {
		this.x = x;
		this.y = y;
		setIcon(image);
		setVerticalAlignment(JLabel.CENTER);
	}
	
	public int getTheX() {
		return this.x;
	}
	
	public int getTheY() {
		return this.y;
	}

}
