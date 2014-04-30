package progetto.dama;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Scacchiera extends JPanel {
	
	private Casella [] [] caselle;

	private boolean turnoUser;
	private boolean mangiataCons;
	private int xMangiataCons,yMangiataCons,xCons,yCons;
	
	static int [] [] pedineRosse = new int [8] [8];
	static int [] [] pedineBlu = new int [8] [8];
	
	private boolean selezionato;
	
	private int xPartenza,yPartenza,xArrivo,yArrivo;
	
	public Scacchiera () {
		caselle = new Casella [8] [8];
		this.selezionato = false;
		turnoUser = true;
		setLayout(new GridLayout(8,8));
		popola();
		riempi();
	}
	
	private void popola() {	//crea scacchiera (le celle)
		for (int y=0 ; y<8 ;y++) {
			for (int x=0 ; x<8 ;x++){
				if((x%2) == (y%2)) {
					caselle [x] [y] = new Casella (0,x,y) {
						protected void Clicked(int x,int y) {
							setBackground(Color.GREEN);
							Scacchiera.this.Clicked(getTheX(),getTheY());
						}};}
				else {
					caselle [x] [y] = new Casella (1,x,y) {
						protected void Clicked(int x, int y) {}};
				}
				add(caselle [x] [y]);
			}}}

	public void riempi () {	//posiziona pedine nelle celle
		 for (int y = 0; y < 8; y++) {
	            for (int x = 0; x < 8; x++) {
	               if ( x % 2 == y % 2 ) {
	                  if (y < 3) {
	                	  caselle [x][y].add(new PedinaRossa(x,y),BorderLayout.CENTER);
	                	  pedineRosse [x][y] = 1;
	                  }
	                  else if (y > 4) {
	                	  caselle [x][y].add(new PedinaBlu(x,y),BorderLayout.CENTER);
	                	  pedineBlu [x][y] = 1; 
	                  }}}}}
	
	/**
	 * ridisegna  la scacchiera secondo l'evoluzione del gioco
	 */
	private void Repaint() {
		removeAll();
		popola();
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	if (pedineBlu [x] [y] == 1)
            		caselle [x][y].add(new PedinaBlu(x,y),BorderLayout.CENTER);
            	else if (pedineBlu [x] [y] == 2)
            		caselle [x][y].add(new DamoneBlu(x,y),BorderLayout.CENTER);
            	else if (pedineRosse [x] [y] == 1)
            		caselle [x][y].add(new PedinaRossa(x,y),BorderLayout.CENTER);
            	else if (pedineRosse [x] [y] == 2)
            		caselle [x][y].add(new DamoneRosso(x,y),BorderLayout.CENTER);
            }}
		this.revalidate();
	}

	/**
	 * metodo invocato dall' AI dopo aver deciso la mossa più "conveniente"
	 * @param xPartenza
	 * @param yPartenza
	 * @param xArrivo
	 * @param yArrivo
	 */
	
	public void DoMossaRossa (int xPartenza,int yPartenza,int xArrivo,int yArrivo){
		Muovi(xPartenza,yPartenza,xArrivo,yArrivo);
	}
	
	private void Clicked (int x , int y) {
		if (selezionato == false) {
			xPartenza = x;
			yPartenza = y;
			selezionato=true;
		}
		else {
			xArrivo=x;
			yArrivo=y;
			Muovi(xPartenza,yPartenza,xArrivo,yArrivo);
			selezionato=false;
		}}

	private void Muovi (int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		
		if(mangiataCons) {
			DoMangiataCons(xPartenza,yPartenza,xArrivo,yArrivo);
		}
		else {
		if (Mangia()){
			if (turnoUser) {		// turno blu
				if ((pedineBlu[xPartenza][yPartenza] == 1) && (pedineBlu[xArrivo][yArrivo] == 0 && pedineRosse[xArrivo][yArrivo] == 0)) {
					
					if ((xArrivo - xPartenza) >0){
						if (pedineRosse [xPartenza+1] [yPartenza-1] == 1 )
							DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
					}
					else if ((xArrivo - xPartenza) <0) {
						if (pedineRosse [xPartenza-1] [yPartenza-1] == 1 )
							DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
					} }
				else if ((pedineBlu[xPartenza][yPartenza] == 2) && (pedineBlu[xArrivo][yArrivo] == 0 && pedineRosse[xArrivo][yArrivo] == 0)) {
						if ((xArrivo - xPartenza) >0){
							if (yArrivo -yPartenza < 0)
								if (pedineRosse [xPartenza+1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
								else {}
							else {
								if (pedineRosse [xPartenza+1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
								else {}
							}}
						else {
							if (yArrivo -yPartenza < 0)
								if (pedineRosse [xPartenza-1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
								else {}
							else {
								if (pedineRosse [xPartenza-1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
								else {}
							}}}
				
			}
			else {
				if ((pedineRosse[xPartenza][yPartenza] == 1) && (pedineRosse[xArrivo][yArrivo] == 0 && pedineBlu[xArrivo][yArrivo] == 0)) {
					
					if ((xArrivo - xPartenza) >0){
						if (pedineBlu [xPartenza+1] [yPartenza+1] == 1 )
							DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
					}
					else if ((xArrivo - xPartenza) <0) {
						if (pedineBlu [xPartenza-1] [yPartenza+1] == 1 )
							DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
					}
			}
			else if ((pedineRosse[xPartenza][yPartenza] == 2) && (pedineRosse[xArrivo][yArrivo] == 0 && pedineBlu[xArrivo][yArrivo] == 0)) {
						if ((xArrivo - xPartenza) >0){
							if (yArrivo -yPartenza < 0)
								if (pedineBlu [xPartenza+1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
								else {}
							else {
								if (pedineBlu [xPartenza+1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
								else {}
							}}
						else {
							if (yArrivo -yPartenza < 0)
								if (pedineBlu [xPartenza-1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
								else {}
							else {
								if (pedineBlu [xPartenza-1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
								else {}
							}}}
			}
			}
		else {
			if (turnoUser) {
				if (Spostamento(1,pedineBlu[xPartenza][yPartenza],xPartenza,yPartenza,xArrivo,yArrivo)) 	{
					if (pedineBlu[xPartenza][yPartenza]==1 && yArrivo==0){	
						pedineBlu[xPartenza][yPartenza] = 0;
						pedineBlu[xArrivo][yArrivo] = 2;	
						turnoUser = !(turnoUser);
						AI.RedTurn();
					}
					else {
						pedineBlu[xArrivo][yArrivo] = pedineBlu[xPartenza][yPartenza];	
						pedineBlu[xPartenza][yPartenza] = 0;
						turnoUser = !(turnoUser);
						AI.RedTurn();}}
			}
			else {
				if (Spostamento(0,pedineRosse[xPartenza][yPartenza],xPartenza,yPartenza,xArrivo,yArrivo)) 	{
					if (pedineRosse[xPartenza][yPartenza] == 1 && yArrivo==7) {
						pedineRosse[xPartenza][yPartenza] = 0;
						pedineRosse[xArrivo][yArrivo] = 2;	
						turnoUser = !(turnoUser);
					}
					else {
						pedineRosse[xArrivo][yArrivo] = pedineRosse[xPartenza][yPartenza];	
						pedineRosse[xPartenza][yPartenza] = 0;
						turnoUser = !(turnoUser);
					}
				}	
			}
		}}
		Repaint();
	}
	
	/**
	 * ritorna true se è legale il movimento della pedina
	 * 
	 * @param colorePedina
	 * @param typePedina
	 * @param xPartenza
	 * @param yPartenza
	 * @param xArrivo
	 * @param yArrivo
	 * @return
	 */
	
	private boolean Spostamento (int colorePedina, int typePedina, int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		if (typePedina == 1) {	// mosse consentite a pedina base
			if (colorePedina == 0) { //pedina rossa -> mosse consentite
				if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && (yArrivo == yPartenza +1) && pedineRosse[xArrivo][yArrivo]== 0 && pedineBlu[xArrivo][yArrivo]==0) 
					return true;
				else
					return false;	
			}
			else if (colorePedina == 1){ // pedina blu -> mosse consentite
				if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && (yArrivo == yPartenza -1)&& pedineRosse[xArrivo][yArrivo]== 0 && pedineBlu[xArrivo][yArrivo]==0) 
					return true;
				else
					return false;	
			}
			else
				return false;
		}
		else if (typePedina == 2) {	//per i damoni sono consentite le stesse mosse qualsiasi sia il colore
			if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && ((yArrivo == yPartenza +1) || (yArrivo == yPartenza -1))&& pedineRosse[xArrivo][yArrivo]== 0 && pedineBlu[xArrivo][yArrivo]==0) 
				return true;
			else
				return false;	
			}
		else {
			return false;
			}}

	/**
	 * effettua la mangiata
	 * 
	 * @param xPartenza
	 * @param yPartenza
	 * @param xArrivo
	 * @param yArrivo
	 * @param xMangiato
	 * @param yMangiato
	 */
	
	private void DoMangia (int xPartenza,int yPartenza,int xArrivo,int yArrivo,int xMangiato, int yMangiato) {
		
		if (turnoUser) {
			if (pedineBlu[xPartenza][yPartenza] ==1 && yArrivo == 0) {
				pedineBlu[xPartenza][yPartenza] = 0;
				pedineRosse [xMangiato] [yMangiato] = 0;
				pedineBlu[xArrivo][yArrivo] = 2;
			}
			else{
				pedineBlu[xArrivo][yArrivo] = pedineBlu[xPartenza][yPartenza];
				pedineBlu[xPartenza][yPartenza] = 0;
				pedineRosse [xMangiato] [yMangiato] = 0;
			}
		}
		
		else {
			if (pedineRosse[xPartenza][yPartenza] ==1 && yArrivo == 7) {
				pedineRosse[xPartenza][yPartenza] = 0;
				pedineBlu [xMangiato] [yMangiato] = 0;
				pedineRosse[xArrivo][yArrivo] = 2;
			}
			else {
				pedineRosse[xArrivo][yArrivo] = pedineRosse[xPartenza][yPartenza];
				pedineRosse[xPartenza][yPartenza] = 0;
				pedineBlu [xMangiato] [yMangiato] = 0;
				}
		}

		mangiataCons = MangiataCons(xArrivo,yArrivo);
		if (mangiataCons) {		// controlla se vi è la mangiata consecutiva
			xMangiataCons = xArrivo;
			yMangiataCons = yArrivo;
			if (turnoUser == false) {	 //se è il turno della cpu forzo la mangiata consecutiva
				DoMangiataCons(xMangiataCons,yMangiataCons,xCons,yCons);
			}
			
		}
		else {
			mangiataCons = false;
			turnoUser = !(turnoUser);
			if (turnoUser == false){
				AI.RedTurn();}
			CheckRes();
			}
		Repaint();
		
	}
	
	/**
	 * ritorna true se all'inizio del turno vi sono delle mangiate possibili in modo che il metodo
	 * Muovi ne renda obbligatoria una
	 * @return
	 */
	
	private boolean Mangia (){

		if (turnoUser) { // turno blu
			
			for (int x=0 ; x<8 ;x++) {
				for (int y=0 ; y<8 ;y++){
					if (pedineBlu [x] [y]==1){ //x pedina blu
						if (x>=2 && x<6 && y>=2) {
							if (pedineRosse [x+1] [y-1] == 1 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
								return true;
							else if (pedineRosse [x-1] [y-1] == 1 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
								return true;
						}
						else if (x<2 && y>=2) {
							if (pedineRosse [x+1] [y-1] == 1 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
								return true;
						}
						else if (x>= 6 && y>=2) {
							if (pedineRosse [x-1] [y-1] == 1 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
								return true;
						}
						}
					
					else if (pedineBlu [x] [y] ==2){ // x damone blu
						if (x>=2 && x<6) {
							if (y<6 && y>=2) {
								if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								else if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;}
							else if (y<2) {
								if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								else if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
							}
						}
						else if (x<2) {
							if (y<6 && y>=2){
								if (pedineRosse [x+1] [y-1]>0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
							}
							else if (y<2){
								if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
						}
						else if (x>=6) {
							if (y<6 && y>=2) {
								if (pedineRosse [x-1] [y-1]>0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
							}
							else if (y<2) {
								if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
						}		
						}
					else
					{}	
					}
				}
			return false;
			}
		
		else { //turno rosso
			for (int x=0 ; x<8 ;x++){
				 for (int y=0 ; y<8 ;y++){
					if (pedineRosse [x] [y] == 1) { // x pedina rossa
							if (x>=2 && x<6 && y<6) {
								if (pedineBlu [x+1] [y+1] == 1 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								else if (pedineBlu [x-1] [y+1] == 1 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (x<2 && y<6) {
								if (pedineBlu [x+1] [y+1] == 1 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
							else if (x>= 6 && y<6) {
								if (pedineBlu [x-1] [y+1] == 1 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
					}
					
					else if (pedineRosse [x] [y] == 2) { // x damone rosso
						if (x>=2 && x<6 ){
							if (y>=2 && y<6) {
								if (pedineBlu [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineBlu [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;}
							else if (y<2) {
								if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineBlu [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineBlu [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
							}
						}
						else if (x<2) {
							if (y>=2 && y< 6) {
								if (pedineBlu [x+1] [y-1]>0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
							else if (y<2)
							{
								if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineBlu [x+1] [y-1]>0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
							}
						}
						else if (x>=6) {
							if (y>=2 && y<6){
								if (pedineBlu [x-1] [y-1]>0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (y<2) {
								if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineBlu [x-1] [y-1]>0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
							}
						}
					}
					else
						{}	
				}
			}
			return false;
		}
	}
	
	/**
	 assegnamo ad una nuova variabile il valore che ritorna Mangiatacons 
	 salviamo anche le coordinate da cui effettuare la mangiata consecutiva
	 non cambiamo il turno (come se avessimo 2 turni consecutivi)
	 nel Muovi prima del controllo su Magia controlliamo se questa nuova variabile è vera in tal caso
	 creiamo un sotto albero di esecuzione in cui l'unica mossa consentita è la n-esima mangiata!
	 */
	private boolean MangiataCons (int x,int y) {
		if (turnoUser) { // turno blu
			
					if (pedineBlu [x] [y]==1){ // x pedina blu
						if (x>=2 && x<6) {
							if (y>=2) {
								if (pedineRosse [x+1] [y-1] == 1 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineRosse [x-1] [y-1] == 1 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
							}
								else {}	
							}
						else if (x<2) {
							if (y>=2 ){
								if (pedineRosse [x+1] [y-1] == 1 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								}
							}
						else if (x>= 6) {
							if (y>=2) {
								if (pedineRosse [x-1] [y-1] == 1 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								}
								else {}
								}
							}
					else if (pedineBlu [x] [y]==2){ // x damone blu
						if (x>=2 && x<6) {
							if (y<2){
								if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
								else if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;}
							else if (y>=6){
								if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;}
							else{
								if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;
								else if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
								else if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
								else if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
							}
						}
						else if (x<2) {
							if (y<2){
							if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
								return true;}
							else if (y>=6){
								if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
							}
							else{
								if (pedineRosse [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
									return true;
								if (pedineRosse [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
									return true;
							}
							}
						else if (x>=6) {
							if (y<2){
								if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;}
							else if (y>=6){
								if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;}
							else{
								if (pedineRosse [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
									return true;
								if (pedineRosse [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
									return true;}
						}
					}
			
			return false;
			}
		
		else {	//turno rosso

			if (pedineRosse [x] [y] == 1) { // x pedina rossa
				if (x>=2 && x<6) {
					if (y<6) {
					if (pedineBlu [x+1] [y+1] == 1 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
					{
						xCons = x+2;
						yCons = y+2;
						return true;}
					else if (pedineBlu [x-1] [y+1] == 1 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
					{
						xCons = x-2;
						yCons = y+2;
						return true;}}
					else {}
				}
				else if (x<2) {
					if (y<6) {
						if (pedineBlu [x+1] [y+1] == 1 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
						{
							xCons = x+2;
							yCons = y+2;
							return true;}
						}
					else {}
				}
				else if (x>= 6) {
					if (y<6) {
						if (pedineBlu [x-1] [y+1] == 1 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
						{
							xCons = x-2;
							yCons = y+2;
							return true;}
						}
					else {}
				}
		}
		
		else if (pedineRosse [x] [y] == 2) { // x damone rosso
			if (x>=2 && x<6 ){
				if (y>=2 && y<6){
					if (pedineBlu [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
					{
						xCons = x+2;
						yCons = y-2;
						return true;}
					else if (pedineBlu [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
					{
						xCons = x-2;
						yCons = y-2;
						return true;}
					else if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
					{
						xCons = x+2;
						yCons = y+2;
						return true;}
					else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
					{
						xCons = x-2;
						yCons = y+2;
						return true;} }
				else if (y<2) {
					if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
					{
						xCons = x+2;
						yCons = y+2;
						return true;}
					else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
					{
						xCons = x-2;
						yCons = y+2;
						return true;}
				}
				else if (y>=6) {
					if (pedineBlu [x+1] [y-1] >0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
					{
						xCons = x+2;
						yCons = y-2;
						return true;}
					else if (pedineBlu [x-1] [y-1] >0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
					{
						xCons = x-2;
						yCons = y-2;
						return true;}
				}
				}
			else if (x<2) {
				if (y>=2 && y<6) {
					if (pedineBlu [x+1] [y-1]>0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
					{
						xCons = x+2;
						yCons = y-2;
						return true;}
					else if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
					{
						xCons = x+2;
						yCons = y+2;
						return true;}
				}
				else if (y<2) {
						if (pedineBlu [x+1] [y+1] >0 && (pedineBlu [x+2] [y+2] ==0 && pedineRosse [x+2] [y+2] ==0))
						{
							xCons = x+2;
							yCons = y+2;
							return true;}}
				else if (y>=6){
						if (pedineBlu [x+1] [y-1]>0 && (pedineBlu [x+2] [y-2] ==0 && pedineRosse [x+2] [y-2] ==0))
						{
							xCons = x+2;
							yCons = y-2;
							return true;}
					}
				
			}
			else if (x>=6) {
				if (y>=2 && y <6){
					if (pedineBlu [x-1] [y-1]>0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
					{
						xCons = x-2;
						yCons = y-2;
						return true;}
					else if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
					{
						xCons = x-2;
						yCons = y+2;
						return true;}
				}
				else {
					if (y<2) {
						if (pedineBlu [x-1] [y+1] >0 && (pedineBlu [x-2] [y+2] ==0 && pedineRosse [x-2] [y+2] ==0))
						{
							xCons = x-2;
							yCons = y+2;
							return true;}
					}
					else if (y>=6) {
						if (pedineBlu [x-1] [y-1]>0 && (pedineBlu [x-2] [y-2] ==0 && pedineRosse [x-2] [y-2] ==0))
						{
							xCons = x-2;
							yCons = y-2;
							return true;}
					}
				}
			}
		}
		else
			{}	
			return false;
		}
	}

	private void DoMangiataCons (int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		if (xPartenza == xMangiataCons && yPartenza == yMangiataCons) {
			if (turnoUser) {		// turno blu
					if ((pedineBlu[xPartenza][yPartenza] == 1) && (pedineBlu[xArrivo][yArrivo] == 0 && pedineRosse[xArrivo][yArrivo] == 0)) {
				
						if ((xArrivo - xPartenza) >0){
							if (pedineRosse [xPartenza+1] [yPartenza-1] == 1 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
						}
						else if ((xArrivo - xPartenza) <0) {
							if (pedineRosse [xPartenza-1] [yPartenza-1] == 1 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
						} }
					else if ((pedineBlu[xPartenza][yPartenza] == 2) && (pedineBlu[xArrivo][yArrivo] == 0 && pedineRosse[xArrivo][yArrivo] == 0)) {
						if ((xArrivo - xPartenza) >0){
							if (yArrivo -yPartenza < 0)
								if (pedineRosse [xPartenza+1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
								else {}
							else {
								if (pedineRosse [xPartenza+1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
								else {}
							}}
						else {
							if (yArrivo -yPartenza < 0)
								if (pedineRosse [xPartenza-1] [yPartenza-1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
								else {}
							else {
								if (pedineRosse [xPartenza-1] [yPartenza+1] > 0 )
									DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
								else {}
							}}}
			
		}
		else {
			if ((pedineRosse[xPartenza][yPartenza] == 1) && (pedineRosse[xArrivo][yArrivo] == 0 && pedineBlu[xArrivo][yArrivo] == 0)) {
				
				if ((xArrivo - xPartenza) >0){
					if (pedineBlu [xPartenza+1] [yPartenza+1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
				}
				else if ((xArrivo - xPartenza) <0) {
					if (pedineBlu [xPartenza-1] [yPartenza+1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
				}
		}
		else if ((pedineRosse[xPartenza][yPartenza] == 2) && (pedineRosse[xArrivo][yArrivo] == 0 && pedineBlu[xArrivo][yArrivo] == 0)) {
					if ((xArrivo - xPartenza) >0){
						if (yArrivo -yPartenza < 0)
							if (pedineBlu [xPartenza+1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
							else {}
						else {
							if (pedineBlu [xPartenza+1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
							else {}
						}}
					else {
						if (yArrivo -yPartenza < 0)
							if (pedineBlu [xPartenza-1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
							else {}
						else {
							if (pedineBlu [xPartenza-1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
							else {}
						}}}
			}}
		
		}

	/**
	 * controlla il numero di pedine sulla scacchiera per definire la vincita o la sconfitta
	 */
	
	private void CheckRes () {
		int numRossi=0;
		int numBlu=0;
		for (int y=0;y<8;y++){
			for (int x=0 ; x<8 ;x++) {
				numRossi+=pedineRosse[x][y];
				numBlu+=pedineBlu [x] [y];
			}
		}
	if (numRossi==0)
		JOptionPane.showMessageDialog(null,"YOU WIN!");
	else if(numBlu==0)
		JOptionPane.showMessageDialog(null,"YOU LOSE!");
	else {}
}
}
