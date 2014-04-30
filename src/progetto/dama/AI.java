package progetto.dama;

import java.util.Date;
import java.util.Random;


public class AI {
	
	private static Scacchiera board;
	static int [] [] pedineBlues;
	static int [] [] pedineReds;
	private static boolean mangiataCons;
	private static int xMangiataCons,yMangiataCons;
	private static boolean turnoUserSimul;
	private static int xRossoPartenza,yRossoPartenza,xRossoArrivo,yRossoArrivo;
	private static int puntiMossaRossaNew,puntiMossaRossaOld,puntiMossaBluOld,puntiMossaBluNew;
	private static Random random = new Random();
	
	public AI (Scacchiera board) {
		this.board = board;
		pedineBlues = new int [8] [8];
		pedineReds = new int [8] [8];
		mangiataCons=false;
		puntiMossaRossaOld=-1000000;
		puntiMossaBluOld=10;
	}
	
	/**
	 * questo metodo viene invocato per definire la mossa che deve eseguire la cpu
	 */
	
	public static void RedTurn () {
		pedineBlues = new int [8] [8];
		pedineReds = new int [8] [8];
		Reset();
		puntiMossaRossaOld=-2000000;
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	pedineBlues [x] [y] = board.pedineBlu[x] [y];
            	pedineReds [x] [y] = board.pedineRosse[x] [y];
            }}
		CercaMossaRossa();
		board.DoMossaRossa (xRossoPartenza,yRossoPartenza,xRossoArrivo,yRossoArrivo);
	}
	
	private static int GeneraRandom () {
		Date d = new Date(); 
		return  d.getSeconds() + random.nextInt();
	}
	
	/**
	 * resetta il valore delle variabili che contengono i punti al fine di una giusta determinazione della "convenienza" della mossa
	 */
	
	private static void Reset () {
		puntiMossaBluOld = 1;
		puntiMossaBluNew = 0;
		puntiMossaRossaNew = 0;
		
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	pedineBlues [x] [y] = board.pedineBlu[x] [y];
            	pedineReds [x] [y] = board.pedineRosse[x] [y];
            }
		}
	}
	
	/**
	 * determina se la nuova mossa simulata è più "conveniente" della precedente
	 * @param old è il coefficiente di convenienza della mossa fino ad ora migliore
	 * @param nuovo è la somma algebrica del punteggio della mossa rossa appena simulata, e il punteggio della migliore mossa blu per questa
	 * @param xOtherPartenza
	 * @param yOtherPartenza
	 * @param xOtherArrivo
	 * @param yOtherArrivo
	 * @return
	 */
	
	private static int ConfrontoRosso (int old,int nuovo,int xOtherPartenza, int yOtherPartenza,int xOtherArrivo, int yOtherArrivo){
		if ((nuovo >old)) {
			old = nuovo;
			xRossoPartenza = xOtherPartenza;
			yRossoPartenza = yOtherPartenza;
			xRossoArrivo = xOtherArrivo;
			yRossoArrivo = yOtherArrivo;
		}
		else if (nuovo == old) {
			if (GeneraRandom()%2 == 0){
				old = nuovo;
				xRossoPartenza = xOtherPartenza;
				yRossoPartenza = yOtherPartenza;
				xRossoArrivo = xOtherArrivo;
				yRossoArrivo = yOtherArrivo;
			}}
		Reset ();
		return old;
	}
	
	/**
	 * determina qual è la mossa blu più "conveniente" al fine di eseguire una scelta del "miglior caso pessimo" (dal punto di vista della cpu)
	 * @param old
	 * @param nuovo
	 * @return
	 */
	
	private static int ConfrontoBlu (int old,int nuovo) {
	
		if (nuovo<old)
			old=nuovo;
		else if (nuovo<old){}
		else {
			if ((random.nextInt())%2 ==0)
				old=nuovo;
		}
		puntiMossaBluNew = 1;
		
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	pedineBlues [x] [y] = board.pedineBlu[x] [y];
            }}
		
		return old;
	}
	
	/**
	 * simula tutte le possibili mosse eseguibili dalla cpu
	 */
	
	private static void CercaMossaRossa () {
		
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	if (pedineReds[x] [y] ==1)
            	{
            		if (x>0 && x<7 && y<7) {
            			Muovi(x,y,x+1,y+1);
            			Muovi(x,y,x-1,y+1);
            		}
            		else if (x==0){
            			Muovi(x,y,x+1,y+1);
            			}
            		else if (x==7){
            			Muovi(x,y,x-1,y+1);
            			}
            		}
            	else if (pedineReds [x] [y]==2){
            		if (x>0 && x<7 ){
            			if (y<7 && y>0) {
            				Muovi(x,y,x+1,y+1);
            				Muovi(x,y,x-1,y+1);            		
            				Muovi(x,y,x+1,y-1);            			
            				Muovi(x,y,x-1,y-1);
            				}
            			else if (y==0) {
            				Muovi(x,y,x+1,y+1);                			
            				Muovi(x,y,x-1,y+1);
            			}
            			else if (y==7) {
            				Muovi(x,y,x+1,y-1);                			
            				Muovi(x,y,x-1,y-1);
            			}          			
            		}
            		else if (x == 0) {
            			if (y==0){
            				Muovi(x,y,x+1,y+1);
            				}
            			else if (y==7){
            				Muovi(x,y,x+1,y-1);
            				}
            			else {
            				Muovi(x,y,x+1,y+1);            				
            				Muovi(x,y,x+1,y-1);            				
            			}
            		}
            		else if (x==7) {
            			if (y==0){
            				Muovi(x,y,x-1,y+1);
            				}
            			else if (y==7){
            				Muovi(x,y,x-1,y-1);
            				}
            			else {
            				Muovi(x,y,x-1,y+1);            				
            				Muovi(x,y,x-1,y-1);            				
            			}
            		}
            	}
            }
		}
	}
	
	/**
	 * simula tutte le possibili contromosse eseguibili dall'utente
	 */
	
	private static void CercaMossaBlu() {
		
		for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
		if (pedineBlues [x] [y] ==1){
			if (x>0 && x<7 && y>0) {
				Muovi(x,y,x+1,y-1);
				Muovi(x,y,x-1,y-1);
			}
			else if (x==0){
				Muovi(x,y,x+1,y-1);
				}
			else if (x==7){
				Muovi(x,y,x-1,y-1);
				}
			}
		else if (pedineBlues [x] [y] == 2){
    		if (x>0 && x<7 && y<7 && y>0){
    			Muovi(x,y,x+1,y+1);
    			Muovi(x,y,x-1,y+1);
    			Muovi(x,y,x+1,y-1);
    			Muovi(x,y,x-1,y-1);
    		}
    		else if (x == 0) {
    			if (y==0) {
    				Muovi(x,y,x+1,y+1);
    				}
    			else if (y==7) {
    				Muovi(x,y,x+1,y-1);
    				}
    			else {
    				Muovi(x,y,x+1,y+1);
    				Muovi(x,y,x+1,y-1);
    			}
    		}
    		else if (x==7) {
    			if (y==0) {
    				Muovi(x,y,x-1,y+1);
    				}
    			else if (y==7) {
    				Muovi(x,y,x-1,y-1);
    				}
    			else {
    				Muovi(x,y,x-1,y+1);
    				Muovi(x,y,x-1,y-1);
    			}
    		}
    		else if (y==0 && x>0 && x<7) {
    			Muovi(x,y,x+1,y+1);
    			Muovi(x,y,x-1,y+1);
    		}
    		else if (y==7 && x<7 && x>0) {
    			Muovi(x,y,x+1,y-1);
    			Muovi(x,y,x-1,y-1);
    		}
    	}}}
	}

	private static void Muovi (int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		if (Mangia()){
			if (turnoUserSimul) {		// turno blu
				
				if (pedineBlues [xPartenza] [yPartenza]==1){ //x pedina blu
					if (xPartenza>=2 && xPartenza<6) {
						if (yPartenza>=2) {
							if (pedineReds [xPartenza+1] [yPartenza-1] == 1 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
							else if (pedineReds [xPartenza-1] [yPartenza-1] == 1 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
					}
						else {}	
					}
					else if (xPartenza<2) {
						if (yPartenza>=2 ){
							if (pedineReds [xPartenza+1] [yPartenza-1] == 1 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
						}
					}
					else if (xPartenza>= 6) {
						if (yPartenza>=2) {
							if (pedineReds [xPartenza-1] [yPartenza-1] == 1 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
					}
						else {}
						}
					}
				else if (pedineBlues [xPartenza] [yPartenza]==2){
					if (xPartenza>=2 && xPartenza<6) { //CASO 1
						if (yPartenza>=2 && yPartenza<6) {
							if (pedineReds [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
							else if (pedineReds [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);} 
							else if (pedineReds [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);} 
							else if (pedineReds [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);} 
						}
						else if (yPartenza<2){
							if (pedineReds [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							else if (pedineReds [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
						}
						else if (yPartenza>=6){
							if (pedineReds [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
							else if (pedineReds [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);} 
						}
					}
					else if (xPartenza<2) { //CASO 2
						if (yPartenza>=2 && yPartenza<6) {
							if (pedineReds [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							else if (pedineReds [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
						}
						else if (yPartenza<2) {
							if (pedineReds [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
						}
						else if (yPartenza>=6){
							if (pedineReds [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);} 
						}
					}
					else if (xPartenza>=6) { //CASO 3
						if (yPartenza>=2 && yPartenza<6){
							if (pedineReds [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
							else if (pedineReds [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
						}
						else if (yPartenza<2){
							if (pedineReds [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
						}
						else if (yPartenza>=6){
							if (pedineReds [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
							{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
						}
					}
				}
			}
	
	else {	//turno rosso

				if (pedineReds [xPartenza] [yPartenza] == 1) { // xPartenza pedina rossa
						if (xPartenza>=2 && xPartenza<6) {
							if (yPartenza<6) {
							if (pedineBlues [xPartenza+1] [yPartenza+1] == 1 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
							{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							else if (pedineBlues [xPartenza-1] [yPartenza+1] == 1 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
							{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
							}
							else {}
						}
						else if (xPartenza<2) {
							if (yPartenza<6) {
								if (pedineBlues [xPartenza+1] [yPartenza+1] == 1 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
									{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
								}
							else {}
						}
						else if (xPartenza>= 6) {
							if (yPartenza<6) {
								if (pedineBlues [xPartenza-1] [yPartenza+1] == 1 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
									{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
								}
							else {}
						}
				}
				
				else if (pedineReds [xPartenza] [yPartenza] == 2) { // xPartenza damone rosso
					if (xPartenza>=2 && xPartenza<6){
						if (yPartenza>=2 && yPartenza<6) {
							if (pedineBlues [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
							else if (pedineBlues [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
							else if (pedineBlues [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							else if (pedineBlues [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
							}
						else if (yPartenza<2) {
							if (pedineBlues [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							else if (pedineBlues [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
						}
						else if (yPartenza>=6) {
							if (pedineBlues [xPartenza+1] [yPartenza-1] >0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
							else if (pedineBlues [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}}
					}
					else if (xPartenza<2) {
						if (yPartenza>=2 && yPartenza<6) {
							if (pedineBlues [xPartenza+1] [yPartenza-1]>0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
							else if (pedineBlues [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
						}
						else if (yPartenza<2) {
							if (pedineBlues [xPartenza+1] [yPartenza+1] >0 && (pedineBlues [xPartenza+2] [yPartenza+2] ==0 && pedineReds [xPartenza+2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza+2,xPartenza+1,yPartenza+1);}
							}
						else if (yPartenza>=6){
							if (pedineBlues [xPartenza+1] [yPartenza-1]>0 && (pedineBlues [xPartenza+2] [yPartenza-2] ==0 && pedineReds [xPartenza+2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza+2,yPartenza-2,xPartenza+1,yPartenza-1);}
						}
					}
					else if (xPartenza>=6) {
						if (yPartenza>=2 && yPartenza <6){
							if (pedineBlues [xPartenza-1] [yPartenza-1]>0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
							else if (pedineBlues [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}
						}
						else if (yPartenza<2){
							if (pedineBlues [xPartenza-1] [yPartenza+1] >0 && (pedineBlues [xPartenza-2] [yPartenza+2] ==0 && pedineReds [xPartenza-2] [yPartenza+2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza+2,xPartenza-1,yPartenza+1);}}
						else if(yPartenza>=6){
							if (pedineBlues [xPartenza-1] [yPartenza-1] >0 && (pedineBlues [xPartenza-2] [yPartenza-2] ==0 && pedineReds [xPartenza-2] [yPartenza-2] ==0))
								{DoMangia(xPartenza,yPartenza,xPartenza-2,yPartenza-2,xPartenza-1,yPartenza-1);}
						}
					}
				}
			}
			}
		else {
			if (turnoUserSimul) {
				if (Spostamento(1,pedineBlues[xPartenza][yPartenza],xPartenza,yPartenza,xArrivo,yArrivo)) 	{
					if (pedineBlues[xPartenza][yPartenza]==1 && yArrivo==0){	
						pedineBlues[xPartenza][yPartenza] = 0;
						pedineBlues[xArrivo][yArrivo] = 2;	
						turnoUserSimul = !(turnoUserSimul);
						puntiMossaBluNew --;
						puntiMossaBluOld = ConfrontoBlu (puntiMossaBluOld,puntiMossaBluNew);
					}
					else {
						pedineBlues[xArrivo][yArrivo] = pedineBlues[xPartenza][yPartenza];	
						pedineBlues[xPartenza][yPartenza] = 0;
						turnoUserSimul = !(turnoUserSimul);
						puntiMossaBluNew --;
						puntiMossaBluOld = ConfrontoBlu (puntiMossaBluOld,puntiMossaBluNew);}}
			}
			else {
				if (Spostamento(0,pedineReds[xPartenza][yPartenza],xPartenza,yPartenza,xArrivo,yArrivo)) 	{
					if (pedineReds[xPartenza][yPartenza] == 1 && yArrivo==7) {
						pedineReds[xPartenza][yPartenza] = 0;
						pedineReds[xArrivo][yArrivo] = 2;	
						turnoUserSimul = !(turnoUserSimul);
						CercaMossaBlu();
						puntiMossaRossaNew ++;
						puntiMossaRossaOld= ConfrontoRosso(puntiMossaRossaOld,puntiMossaRossaNew+puntiMossaBluOld,xPartenza,yPartenza,xArrivo,yArrivo);
					}
					else {
						pedineReds[xArrivo][yArrivo] = pedineReds[xPartenza][yPartenza];	
						pedineReds[xPartenza][yPartenza] = 0;
						turnoUserSimul = !(turnoUserSimul);
						CercaMossaBlu();
						puntiMossaRossaNew ++;
						puntiMossaRossaOld= ConfrontoRosso(puntiMossaRossaOld,puntiMossaRossaNew+puntiMossaBluOld,xPartenza,yPartenza,xArrivo,yArrivo);}	}	
			}
		}
	}
	
	private static boolean Spostamento (int colorePedina, int typePedina, int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		if (typePedina == 1) {	// mosse consentite a pedina base pedina base
			if (colorePedina == 0) { //pedina rossa -> mosse consentite
				if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && (yArrivo == yPartenza +1) && pedineReds[xArrivo][yArrivo]== 0 && pedineBlues[xArrivo][yArrivo]==0) 
					return true;
				else
					return false;	
			}
			else if (colorePedina == 1){ // pedina blu -> mosse consentite
				if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && (yArrivo == yPartenza -1)&& pedineReds[xArrivo][yArrivo]== 0 && pedineBlues[xArrivo][yArrivo]==0) 
					return true;
				else
					return false;	
			}
			else
				return false;
		}
		else if (typePedina == 2) {	//per i damoni sono consentite le stesse mosse qualsiasi sia il colore
			if ((( xArrivo == xPartenza +1) || (xArrivo == xPartenza -1)) && ((yArrivo == yPartenza +1) || (yArrivo == yPartenza -1))&& pedineReds[xArrivo][yArrivo]== 0 && pedineBlues[xArrivo][yArrivo]==0) 
				return true;
			else
				return false;	
			}
		else {
			return false;
			}}
	
	private static void DoMangia (int xPartenza,int yPartenza,int xArrivo,int yArrivo,int xMangiato, int yMangiato) {
		
		if (turnoUserSimul) {
			if (pedineBlues[xPartenza][yPartenza] ==1 && yArrivo == 0) {
				pedineBlues[xPartenza][yPartenza] = 0;
				pedineReds [xMangiato] [yMangiato] = 0;
				pedineBlues[xArrivo][yArrivo] = 2;
				puntiMossaBluNew-=500;
				puntiMossaBluOld = ConfrontoBlu (puntiMossaBluOld,puntiMossaBluNew);
			}
			else{
				pedineBlues[xArrivo][yArrivo] = pedineBlues[xPartenza][yPartenza];
				pedineBlues[xPartenza][yPartenza] = 0;
				pedineReds [xMangiato] [yMangiato] = 0;
				puntiMossaBluNew-=500;
				puntiMossaBluOld = ConfrontoBlu (puntiMossaBluOld,puntiMossaBluNew);
			}
		}
		
		else {
			if (pedineReds[xPartenza][yPartenza] ==1 && yArrivo == 7) {
				pedineReds[xPartenza][yPartenza] = 0;
				pedineBlues [xMangiato] [yMangiato] = 0;
				pedineReds[xArrivo][yArrivo] = 2;
				puntiMossaRossaNew +=500;
				puntiMossaRossaOld= ConfrontoRosso(puntiMossaRossaOld,puntiMossaRossaNew+puntiMossaBluOld,xPartenza,yPartenza,xArrivo,yArrivo);
			}
			else {
				pedineReds[xArrivo][yArrivo] = pedineReds[xPartenza][yPartenza];
				pedineReds[xPartenza][yPartenza] = 0;
				pedineBlues [xMangiato] [yMangiato] = 0;
				puntiMossaRossaNew +=500;
				puntiMossaRossaOld= ConfrontoRosso(puntiMossaRossaOld,puntiMossaRossaNew+puntiMossaBluOld,xPartenza,yPartenza,xArrivo,yArrivo);
				}
		}
		mangiataCons = MangiataCons(xArrivo,yArrivo);
		if (mangiataCons) {
			DoMangiataCons(xArrivo,yArrivo,xMangiataCons,yMangiataCons);
		}
		else {
			turnoUserSimul = !(turnoUserSimul);
			mangiataCons = false;
			CercaMossaBlu();}
	}
	
	private static boolean Mangia (){

		if (turnoUserSimul) {		// turno blu
			
			for (int x=0 ; x<8 ;x++) {
				for (int y=0 ; y<8 ;y++){
					if (pedineBlues [x] [y]==1){ //x pedina blu
						if (x>=2 && x<6 && y>=2) {
							if (pedineReds [x+1] [y-1] == 1 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								return true;
							else if (pedineReds [x-1] [y-1] == 1 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								return true;
						}
						else if (x<2 && y>=2) {
							if (pedineReds [x+1] [y-1] == 1 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								return true;
						}
						else if (x>=6 && y>=2) {
							if (pedineReds [x-1] [y-1] == 1 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								return true;
						}
						}
					else if (pedineBlues [x] [y] ==2){ // x damone blu
						if (x>=2 && x<6) {
							if (y>=2 && y<6) {
								if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
								else if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
								else if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (y<2) {
								if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
								else if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
							}
							
						}
						else if (x<2) {
							if (y<6 && y>=2){
								if (pedineReds [x+1] [y-1]>0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								}
							else if (y < 2) {
								if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
							}
						}
						else if (x>=6) {
							if (y<6 && y>=2) {
								if (pedineReds [x-1] [y-1]>0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
								else if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
							}
							else if (y<2) {
								if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
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
		
		else {	//turno rosso
			for (int x=0 ; x<8 ;x++){
				 for (int y=0 ; y<8 ;y++){
					if (pedineReds [x] [y] == 1) { // x pedina rossa
							if (x>=2 && x<6 && y<6) {
								if (pedineBlues [x+1] [y+1] == 1 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
								else if (pedineBlues [x-1] [y+1] == 1 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (x<2 && y<6) {
								if (pedineBlues [x+1] [y+1] == 1 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
							}
							else if (x>= 6 && y<6) {
								if (pedineBlues [x-1] [y+1] == 1 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
					}
					else if (pedineReds [x] [y] == 2) { // x damone rosso
						if (x>=2 && x<6 ){
							if (y>=2 && y<6) {
								if (pedineBlues [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineBlues [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
								else if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;}
							else if (y<2) {
								if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;}
							else if (y>=6) {
								if (pedineBlues [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineBlues [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
							}
						}
						else if (x<2) {
							if (y>=2 && y<6) {
								if (pedineBlues [x+1] [y-1]>0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
								else if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
							}
							else if (y<2)
							{
								if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineBlues [x+1] [y-1]>0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									return true;
							}
						}
						else if (x>=6) {
							if (y>=2 && y<6){
								if (pedineBlues [x-1] [y-1]>0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
									return true;
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (y<2) {
								if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									return true;
							}
							else if (y>=6) {
								if (pedineBlues [x-1] [y-1]>0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
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
	 assegnamo ad una nuova variiabile il valore che ritorna mangiatacons 
	 salviamo anche le coordinate da cui effettuare la mangiata consecutiva
	 non cambiamo il turno (Ã‹ come se avessimo 2 turni consecutivi)
	 nel Muovi prima del controllo su Magia2 controlliamo se questa nuova variebile Ã‹ vera in tal caso
	 creeiamo un sotto albero di esecuzione in cui l'unica mossa consentita la n-esima mangiata!
	 */
	private static boolean MangiataCons (int x,int y) {
		if (turnoUserSimul) {		// turno blu
			
					if (pedineBlues [x] [y]==1){ //x pedina blu
						if (x>=2 && x<6) {
							if (y>=2) {
								if (pedineReds [x+1] [y-1] == 1 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineReds [x-1] [y-1] == 1 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
							}
								else {}	
							}
						else if (x<2) {
							if (y>=2 ){
							if (pedineReds [x+1] [y-1] == 1 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
							{
								xMangiataCons = x+2;
								yMangiataCons = y-2;
								return true;}
							}
						}
						else if (x>= 6) {
							if (y>=2) {
							if (pedineReds [x-1] [y-1] == 1 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
							{
								xMangiataCons = x-2;
								yMangiataCons = y-2;
								return true;}
						}
							else {}
							}
						}
					
					else if (pedineBlues [x] [y]==2){ //x damone blu
						if (x>=2 && x<6) {
							if (y>=2 && y<6) {
								if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}	
								else if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}}
							else if (y<2) {
								if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
								if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
							}
							else if (y>6) {
								if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
								if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
							}
						}
						else if (x<2) {
							if (y>=2 && y<6) {
								if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
							}
							else if (y<2) {
								if (pedineReds [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
							}
							else if (y>=6){
								if (pedineReds [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
							}
						}
						else if (x>=6) {
							if (y>=2 && y<6 ){
								if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
								}
							else if (y<2) {
								if (pedineReds [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
							}
							else if (y>=6) {
								if (pedineReds [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
							}
						}
					}
			
			return false;
			}
		
		else {	//turno rosso

					if (pedineReds [x] [y] == 1) { // x pedina rossa
							if (x>=2 && x<6) {
								if (y<6) {
								if (pedineBlues [x+1] [y+1] == 1 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineBlues [x-1] [y+1] == 1 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}}
								else {}
							}
							else if (x<2) {
								if (y<6) {
									if (pedineBlues [x+1] [y+1] == 1 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
									{
										xMangiataCons = x+2;
										yMangiataCons = y+2;
										return true;}
									}
								else {}
							}
							else if (x>= 6) {
								if (y<6) {
									if (pedineBlues [x-1] [y+1] == 1 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
									{
										xMangiataCons = x-2;
										yMangiataCons = y+2;
										return true;}
									}
								else {}
							}
					}
					
					else if (pedineReds [x] [y] == 2) { // x damone rosso
						if (x>=2 && x<6 ){
							if( y>=2 && y<6) {
								if (pedineBlues [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineBlues [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}}
							else if (y<2){
								if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
							}
							else if (y>=6){
								if (pedineBlues [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineBlues [x-1] [y-1] >0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
							}
						}
						else if (x<2) {
							if (y>=2 && y<6) {
								if (pedineBlues [x+1] [y-1]>0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
							}
							
							else if (y>=6) {
									if (pedineBlues [x+1] [y-1] >0 && (pedineBlues [x+2] [y-2] ==0 && pedineReds [x+2] [y-2] ==0))
									{
										xMangiataCons = x+2;
										yMangiataCons = y-2;
										return true;}}
							else if (y<2){
								if (pedineBlues [x+1] [y+1] >0 && (pedineBlues [x+2] [y+2] ==0 && pedineReds [x+2] [y+2] ==0))
								{
									xMangiataCons = x+2;
									yMangiataCons = y+2;
									return true;}
							}
							
						}
						else if (x>=6) {
							if (y>=2 && y <6){
								if (pedineBlues [x-1] [y-1]>0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
								else if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
							}
							else if (y>=6) {
								if (pedineBlues [x-1] [y-1]>0 && (pedineBlues [x-2] [y-2] ==0 && pedineReds [x-2] [y-2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y-2;
									return true;}
							}
							else if (y<2) {
								if (pedineBlues [x-1] [y+1] >0 && (pedineBlues [x-2] [y+2] ==0 && pedineReds [x-2] [y+2] ==0))
								{
									xMangiataCons = x-2;
									yMangiataCons = y+2;
									return true;}
							}
						}
					}
					else
						{}	
			return false;
		}
	}

	private static void DoMangiataCons (int xPartenza, int yPartenza, int xArrivo, int yArrivo) {
		if (xPartenza == xMangiataCons && yPartenza == yMangiataCons) {
		if (turnoUserSimul) {		// turno blu
			if ((pedineBlues[xPartenza][yPartenza] == 1) && (pedineBlues[xArrivo][yArrivo] == 0 && pedineReds[xArrivo][yArrivo] == 0)) {
				
				if ((xArrivo - xPartenza) >0){
					if (pedineReds [xPartenza+1] [yPartenza-1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
				}
				else if ((xArrivo - xPartenza) <0) {
					if (pedineReds [xPartenza-1] [yPartenza-1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
				} }
			else if ((pedineBlues[xPartenza][yPartenza] == 2) && (pedineBlues[xArrivo][yArrivo] == 0 && pedineReds[xArrivo][yArrivo] == 0)) {
					if ((xArrivo - xPartenza) >0){
						if (yArrivo -yPartenza < 0)
							if (pedineReds [xPartenza+1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
							else {}
						else {
							if (pedineReds [xPartenza+1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
							else {}
						}}
					else {
						if (yArrivo -yPartenza < 0)
							if (pedineReds [xPartenza-1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
							else {}
						else {
							if (pedineReds [xPartenza-1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
							else {}
						}}}
			
		}
		else {
			if ((pedineReds[xPartenza][yPartenza] == 1) && (pedineReds[xArrivo][yArrivo] == 0 && pedineBlues[xArrivo][yArrivo] == 0)) {
				
				if ((xArrivo - xPartenza) >0){
					if (pedineBlues [xPartenza+1] [yPartenza+1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
				}
				else if ((xArrivo - xPartenza) <0) {
					if (pedineBlues [xPartenza-1] [yPartenza+1] == 1 )
						DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
				}
		}
		else if ((pedineReds[xPartenza][yPartenza] == 2) && (pedineReds[xArrivo][yArrivo] == 0 && pedineBlues[xArrivo][yArrivo] == 0)) {
					if ((xArrivo - xPartenza) >0){
						if (yArrivo -yPartenza < 0)
							if (pedineBlues [xPartenza+1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza-1);
							else {}
						else {
							if (pedineBlues [xPartenza+1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza+1,yPartenza+1);
							else {}
						}}
					else {
						if (yArrivo -yPartenza < 0)
							if (pedineBlues [xPartenza-1] [yPartenza-1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza-1);
							else {}
						else {
							if (pedineBlues [xPartenza-1] [yPartenza+1] > 0 )
								DoMangia(xPartenza,yPartenza,xArrivo,yArrivo,xPartenza-1,yPartenza+1);
							else {}
						}}}
			}}
		}

}
