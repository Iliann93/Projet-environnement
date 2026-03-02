import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.imageio.ImageIO;

import java.awt.geom.Point2D;
import java.io.File;
import java.awt.RadialGradientPaint;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Image;



public class World {

	//deja un mutant
	boolean mutant = false;

	//meteor
	int meteore_temps = 0;
	int feu_meteore = 0;
	Image[] tab_feu1;
	Image feu;
    Image feu1;
    Image feu2;
    Image feu3;
	int atteriex;
	int atteriey;
	boolean atterre = false;

	//malade
	boolean malade = false;

	int spriteLength = 32;

	int _dx;
	int _dy;

	int nb_proies_vivantes = 40;
	int nb_pred_vivantes = 50;
	int nb_tree = 0;

	int[][] case_world_pred;
	Tree[][] case_tree;
	Camps[][] case_camps;
	int[][] case_tree_after;
	int[][] case_tree_after1;

	//initialisation bord
	HashSet<String> bordy = new HashSet<>();
	HashSet<String> treegrass = new HashSet<>();
	HashSet<String> treegrass1 = new HashSet<>();

	//monde en feu
	boolean world_fire = false;
	boolean thunder_world = false;

	//mode nuit
	boolean night;
	int duré_night;

	//pluis 
	boolean isRaining = false;
	int duree_pluis = 500;
	float pluis_transition = 0.0f;

	//camps
	int camps_totale = 0;

	
	
	//repro prois&predateur
	double repro_prey = 0.045;
	double repro_pred = 0.025;
	double repro_pred_muter = 0.030;
	double repro_tree = 0.00002;
	double repro_tree_8 = 0.00004;
	double repro_herbe = 0.00005;
	double repro_herbe_seche = 0.00007;


	//faim pred
	int faim = 40;

	//faim prois
	int faim_p = 60;



	boolean buffering;
	boolean cloneBuffer; // if buffering, clone buffer after swith
	
	int activeIndex;
	
	ArrayList<Agent> agents;
	ArrayList<Camps> camps;
	ArrayList<PreyAgent> agents_prey;
	ArrayList<PredatorAgent> agents_pred;
	ArrayList<Tree> tree;
	ArrayList<Herbe> herbe;
	ArrayList<Meteor> meteors;

	
	public World ( int __dx , int __dy, boolean __buffering, boolean __cloneBuffer )
	{
		_dx = __dx;
		_dy = __dy;
		
		buffering = __buffering;
		cloneBuffer = __cloneBuffer;

		night = false;
		duré_night = 1000;
		
		world_fire = false;
		activeIndex = 1000;
		
		agents = new ArrayList<Agent>();
		agents_pred = new ArrayList<PredatorAgent>();
		agents_prey = new ArrayList<PreyAgent>();
		tree = new ArrayList<Tree>();
		herbe = new ArrayList<Herbe>();
		camps = new ArrayList<Camps>();
		meteors = new ArrayList<Meteor>();

		case_world_pred = new int[_dx / 32][_dy / 32]; 
		case_tree = new Tree[_dx / 32][_dy / 32]; 
		case_camps = new Camps[_dx / 32][_dy / 32]; 
		case_tree_after = new int[_dx / 32][_dy / 32]; 
		case_tree_after1 = new int[_dx / 32][_dy / 32]; 

		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40; j++){
				case_world_pred[i][j] = 0 ;
			}
		}

		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40; j++){
				case_tree[i][j] = null ;
			}
		}
		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40; j++){
				case_camps[i][j] = null ;
			}
		}
		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40; j++){
				case_tree_after1[i][j] = 0;
			}
		}
		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40; j++){
				case_tree_after[i][j] = 0;
			}
		}
		treegrass1.add("y=8,x=32-41");
		treegrass1.add("y=9,x=32-41");
		treegrass1.add("y=10,x=32-41");
		treegrass1.add("y=11,x=32-44");
		treegrass1.add("y=12,x=32-44");
		treegrass1.add("y=13,x=32-50");
		treegrass1.add("y=14,x=32-50");
		treegrass1.add("y=15,x=32-50");
		treegrass1.add("y=16,x=32-50");
		treegrass1.add("y=17,x=32-50");
		treegrass1.add("y=18,x=32-50");
		treegrass1.add("y=19,x=32-46");
		


		

		treegrass.add("y=5,x=18-19");
		treegrass.add("y=5,x=32-38");
		treegrass.add("y=6,x=15-19");
		treegrass.add("y=6,x=27-38");
		treegrass.add("y=7,x=14-22");
		treegrass.add("y=7,x=26-41");
		treegrass.add("y=8,x=13-41");
		treegrass.add("y=9,x=12-44");
		treegrass.add("y=10,x=11-45");
		treegrass.add("y=11,x=10-48");
		treegrass.add("y=12,x=10-49");
		treegrass.add("y=13,x=10-50");
		treegrass.add("y=14,x=10-51");
		treegrass.add("y=15,x=9-52");
		treegrass.add("y=16,x=8-52");
		treegrass.add("y=17,x=8-53");
		treegrass.add("y=18,x=8-53");
		treegrass.add("y=19,x=9-53");
		treegrass.add("y=20,x=9-53");
		treegrass.add("y=21,x=9-51");
		treegrass.add("y=22,x=10-51");
		treegrass.add("y=23,x=10-50");
		treegrass.add("y=24,x=10-50");
		treegrass.add("y=25,x=10-49");
		treegrass.add("y=26,x=9-49");
		treegrass.add("y=27,x=10-49");
		treegrass.add("y=28,x=9-49");
		treegrass.add("y=29,x=10-48");
		treegrass.add("y=30,x=11-48");
		treegrass.add("y=31,x=12-13");
		treegrass.add("y=31,x=16-29");
		treegrass.add("y=31,x=34-14");
		treegrass.add("y=31,x=45-47");
		treegrass.add("y=32,x=17-18");
		treegrass.add("y=32,x=26-29");


		bordy.add("y=0,x=29-35");
		bordy.add("y=1,x=13-35");
		bordy.add("y=2,x=10-39");
		bordy.add("y=3,x=8-42");
		bordy.add("y=4,x=8-45");
		bordy.add("y=5,x=7-47");
		bordy.add("y=6,x=7-49");
		bordy.add("y=7,x=5-50");
		bordy.add("y=8,x=5-52");
		bordy.add("y=9,x=4-53");
		bordy.add("y=10,x=4-53");
		bordy.add("y=11,x=2-54");
		bordy.add("y=12,x=2-54");
		bordy.add("y=13,x=2-55");
		bordy.add("y=14,x=2-56");
		bordy.add("y=15,x=2-56");
		bordy.add("y=16,x=2-57");
		bordy.add("y=17,x=3-57");
		bordy.add("y=18,x=3-57");
		bordy.add("y=19,x=3-57");
		bordy.add("y=20,x=2-57");
		bordy.add("y=21,x=2-57");
		bordy.add("y=22,x=2-58");
		bordy.add("y=23,x=3-58");
		bordy.add("y=24,x=3-57");
		bordy.add("y=25,x=3-57");
		bordy.add("y=26,x=3-57");
		bordy.add("y=27,x=3-56");
		bordy.add("y=28,x=3-54");
		bordy.add("y=29,x=3-54");
		bordy.add("y=30,x=3-53");
		bordy.add("y=31,x=3-52");
		bordy.add("y=32,x=4-51");
		bordy.add("y=33,x=5-50");
		bordy.add("y=34,x=4-49");
		bordy.add("y=35,x=5-48");

		bordy.add("y=36,x=6-13");
		bordy.add("y=36,x=16-33");
		bordy.add("y=36,x=40-47");

		bordy.add("y=37,x=8-12");
		bordy.add("y=37,x=20-32");
		bordy.add("y=37,x=42-47");

		bordy.add("y=38,x=9-9");
		bordy.add("y=38,x=23-29");
		bordy.add("y=38,x=43-46");



		case_world_pred = new int[60][40];
    	for (int i = 0; i < 60; i++) {
        	for (int j = 0; j < 40; j++) {
           		case_world_pred[i][j] = 3; // Par défaut, tout est interdit
        	}
    	}

		// Appliquer les intervalles de bordy
    	for (String bord : bordy) {
        	String[] parts = bord.split(",");
        	int y = Integer.parseInt(parts[0].split("=")[1]); // Récupérer y
        	String[] xRange = parts[1].split("=")[1].split("-");
        	int xStart = Integer.parseInt(xRange[0]); // Début de l'intervalle x
        	int xEnd = Integer.parseInt(xRange[1]);   // Fin de l'intervalle x

        	// Mettre à 0 les cases correspondant à l'intervalle
        	for (int x = xStart; x <= xEnd; x++) {
            	if (x >= 0 && x < 60 && y >= 0 && y < 40) { // Vérifier les limites
                	case_world_pred[x][y] = 0;
            	}
        	}
    	}
		for (String grass : treegrass) {
        	String[] parts = grass.split(",");
        	int y = Integer.parseInt(parts[0].split("=")[1]); // Récupérer y
        	String[] xRange = parts[1].split("=")[1].split("-");
        	int xStart = Integer.parseInt(xRange[0]); // Début de l'intervalle x
        	int xEnd = Integer.parseInt(xRange[1]);   // Fin de l'intervalle x

        	// Mettre à 0 les cases correspondant à l'intervalle
        	for (int x = xStart; x <= xEnd; x++) {
            	if (x >= 0 && x < 60 && y >= 0 && y < 40) { // Vérifier les limites
                	case_world_pred[x][y] = 7;
            	}
        	}
    	}
		for (String grass : treegrass1) {
        	String[] parts = grass.split(",");
        	int y = Integer.parseInt(parts[0].split("=")[1]); // Récupérer y
        	String[] xRange = parts[1].split("=")[1].split("-");
        	int xStart = Integer.parseInt(xRange[0]); // Début de l'intervalle x
        	int xEnd = Integer.parseInt(xRange[1]);   // Fin de l'intervalle x

        	// Mettre à 0 les cases correspondant à l'intervalle
        	for (int x = xStart; x <= xEnd; x++) {
            	if (x >= 0 && x < 60 && y >= 0 && y < 40) { // Vérifier les limites
                	case_world_pred[x][y] = 8;
            	}
        	}
    	}
	
    	for (int y = 39; y < 40; y++) {
        	for (int x = 0; x < 60; x++) {
            	case_world_pred[x][y] = 3; // Afficher chaque case
        	}
     
    	}  
		
		System.out.println("Affichage de la matrice case_world_pred (60x40) :");
    	for (int y = 0; y < 40; y++) {
        	for (int x = 0; x < 60; x++) {
            	System.out.print(case_world_pred[x][y] + " "); // Afficher chaque case
        	}
        	System.out.println(); // Nouvelle ligne après chaque ligne y
    	} 

		try{

            feu = ImageIO.read(new File("./ressources/ezgif-split/fire/feu.png"));
            feu1 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu1.png"));
            feu2 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu2.png"));
            feu3 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu2.png"));

        }catch(Exception e){

            e.printStackTrace();
            System.exit(-1);
        }


		tab_feu1= new Image[]{feu, feu, feu, feu, feu, feu, feu1, feu1, feu1, feu1, feu1, feu1, feu2, feu2, feu2, feu2, feu2, feu2, feu3, feu3, feu3, feu3, feu3, feu3};

	}
	
	
	
	/**
	 * Update the world state and return an array for the current world state (may be used for display)
	 * @return
	 */
	public void step ( )
	{
// Gestion de la nuit----------------------------------------------------------------------------------------------------
		 if (duré_night > 0) {

			duré_night--;
		} else {
			// Changer l'état de la nuit avec une probabilité de 1%
			if (night == true) { // 1% de chance de changer l'état
				night = false; // Basculer entre jour et nuit
				duré_night = 1000; // Durée de la nuit ou du jour
				
			}else{
				night = true; // Basculer entre jour et nuit
				duré_night = 1000; // Durée de la nuit ou du jour
			}
		}

//gestion pluit------------------------------------------------------------------------------------------------------------
		if(duree_pluis > 0){

			duree_pluis--;

		}else{

			if(Math.random() <= 0.25){
				isRaining = true;
				duree_pluis = 300;
			}else{
				isRaining = false;
				duree_pluis = 300;
				pluis_transition = 0.0f;
			}

		}


//step agent et step world---------------------------------------------------------------------------------------------------

		stepWorld();
		stepAgents();
		stepWorld();

		
		
		

	}
	
	
	
	public int getWidth(){
		return _dx;
	}

	public int getHeight(){
		return _dy;
	}
	
	
	public void add (Agent agent)
	{
		if (agent instanceof PredatorAgent){
			agents_pred.add((PredatorAgent)agent);
		}
		if (agent instanceof PreyAgent){
			agents_prey.add((PreyAgent)agent);
		}
		if (agent instanceof Tree){
			tree.add((Tree)agent);
		}
		if (agent instanceof Herbe){
			herbe.add((Herbe)agent);
		}
		if (agent instanceof Camps){
			camps.add((Camps)agent);
			
		}if (agent instanceof Meteor){
			meteors.add((Meteor)agent);
			
		}
		agents.add(agent);
	}
	public void cleanAllAgents() {
		// 1. Nettoyage de la liste globale
		Iterator<Agent> globalIter = agents.iterator();
		while (globalIter.hasNext()) {
			Agent agent = globalIter.next();
			
			boolean shouldRemove = switch (agent) {
				case PredatorAgent p -> !p._predator;  // Condition spécifique
				case PreyAgent p -> !p._alive;
				case Herbe h -> !h._alive;
				case Tree t -> !t._alive;
				default -> false;
			};
			
			if (shouldRemove) globalIter.remove();
		}
	
		// 2. Nettoyage des listes spécifiques
		agents_pred.removeIf(p -> !p._predator || !agents.contains(p));
		agents_prey.removeIf(p -> !p._alive || !agents.contains(p));
		herbe.removeIf(h -> !h._alive || !agents.contains(h));
		tree.removeIf(t -> !t._alive || !agents.contains(t));
	}
	
	
	public void stepWorld() // world THEN agents
	{	
		cleanAllAgents();

//ajout d'une meteorite--------------------------------------------------------------------------------------------------------
		
		
		if(nb_tree > 236 && meteore_temps == 0){
			meteore_temps++;
			this.add(new Meteor(_dy, _dx, this));
		}
		
//compte le nombre d'arbres-----------------------------------------------------------------------------
		nb_tree = 0;

		world_fire = false;
		for ( int i = 0 ; i != tree.size() ; i++ )
		{
				
				if(tree.get(i)._alive){
					nb_tree++;
					case_tree[tree.get(i)._x / 32][tree.get(i)._y/32] = tree.get(i);
					case_tree_after[tree.get(i)._x / 32][tree.get(i)._y/32] = 1;
				}else{
					case_tree[tree.get(i)._x / 32][tree.get(i)._y/32] = null;
					case_tree_after[tree.get(i)._x / 32][tree.get(i)._y/32] = 0;
				}


				if(tree.get(i)._alive && tree.get(i).infire ){
					world_fire = true;
				}
			
			
		}
		case_tree_after1 = case_tree_after;

//compte le nombre de mutant et malade------------------------------------------------------------------------
		malade = false;
		mutant = false;
		for ( int i = 0 ; i != agents_pred.size() ; i++ )
		{
				
				if(agents_pred.get(i)._predator && agents_pred.get(i).malade ){
					malade = true;
				}
				if(agents_pred.get(i)._predator && agents_pred.get(i).muter){
					mutant = true;
				}
			
			
		}


		
		
	}
	
	public void stepAgents() // world THEN agents
	{

//main magique sur la reproduction-----------------------------------------------------------------------------------


		if(world_fire){
			repro_tree = 0.000003;
			repro_tree_8 = 0.000003;
		}else{
			repro_tree = 0.00002;
		}

		if(isRaining){
			repro_tree = 0.00009;
			repro_tree_8 = 0.00012;
			repro_herbe = 0.00005;
			repro_herbe_seche = 0.000086;

		}else{
			repro_tree = 0.000008;
			repro_tree_8 = 0.00001;
			repro_herbe = 0.000002;
			repro_herbe_seche = 0.000004;
		}
		//changement repro prey si trop de prey
		if(nb_proies_vivantes > 135){
			repro_prey = 0.0035;
		}else{
			repro_prey = 0.0545;
		}if(nb_proies_vivantes <= 135 && nb_pred_vivantes < 50){
			repro_prey = 0.03;
		}

		if(nb_pred_vivantes > 79){
			repro_pred = 0.014;
		}else{
			repro_pred = 0.034;
		}
/* 
		if(nb_pred_vivantes > 100){
			faim = 23;
		}else{
			faim = 40;
		}
*/

	for (int i = 0; i < 60; i++) {
		for (int j = 0; j < 40; j++) {
			if(case_world_pred[i][j] == 20 && isRaining){

				case_world_pred[i][j] = 21;

			}
		}	
	}		

//pred mangees-----------------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_pred.size();i++){
			for(int j =0; j< agents_prey.size();j++){

				//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
					if ((agents_pred.get(i)._x == agents_prey.get(j)._x)
						&&(agents_pred.get(i)._y == agents_prey.get(j)._y)
						&&(agents_pred.get(i)._predator)){

						if (agents_prey.get(j)._alive==true){

							if(agents_pred.get(i).faim < 17 && agents_pred.get(i).stock < 5){
								agents_pred.get(i).stock++;
							}else{
								agents_pred.get(i).faim = Math.max(0, agents_pred.get(i).faim - 11);
							}

							agents_prey.get(j)._alive=false;
							agents_prey.get(j)._y = -1;
							agents_prey.get(j)._x = -1;
							nb_proies_vivantes --;
							
						}
					}
				}
			//}
		}
//pred contamine-----------------------------------------------------------------------------------------------------------
	
			for(int i =0; i< agents_pred.size();i++){
				for(int j =0; j< agents_pred.size();j++){

					//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
						if ((agents_pred.get(i)._x == agents_pred.get(j)._x)
							&&(agents_pred.get(i)._y == agents_pred.get(j)._y)
							&&(agents_pred.get(i)._predator) && i != j){

							if (agents_pred.get(j)._predator == true && agents_pred.get(j).malade && agents_pred.get(i).malade == false && agents_pred.get(i).gueris == false){

								agents_pred.get(i).malade = true ;
								//case_tree_after[herbe.get(j)._x/32][herbe.get(j)._y/32] = 0;
								malade = true;
								agents_pred.get(i).temps_malade = 800;
								//nb_proies_vivantes --;
					
							}
					}
			//}
				}
			}

//combat entre rouge et bleu---------------------------------------------------------------------------------------------


			for(int i =0; i< agents_pred.size();i++){
				for(int j =0; j< agents_pred.size();j++){

					//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
						if ((agents_pred.get(i)._x == agents_pred.get(j)._x)
							&&(agents_pred.get(i)._y == agents_pred.get(j)._y)
							&&(agents_pred.get(i)._predator) && i != j){

							if (agents_pred.get(j)._predator==true && agents_pred.get(j).muter == false && agents_pred.get(i).muter == true && agents_pred.get(i)._predator == true ){
								if(Math.random() < 0.25){
									if(Math.random() < 0.20){
										agents_pred.get(i)._predator = false;
										agents_pred.get(i)._x = -1;
										agents_pred.get(i)._y= -1;
										nb_pred_vivantes--;

									}else{
										agents_pred.get(j)._predator = false;
										agents_pred.get(j)._x = -1;
										agents_pred.get(j)._y= -1;
										nb_pred_vivantes--;
									}
								}

								
					
							}
					}
			//}
				}
			}

//proies mangees--------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_prey.size();i++){
			for(int j =0; j< herbe.size();j++){

				//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
					if ((agents_prey.get(i)._x == herbe.get(j)._x)
						&&(agents_prey.get(i)._y == herbe.get(j)._y)
						&&(agents_prey.get(i)._alive)){

						if (herbe.get(j)._alive==true){

							herbe.get(j)._alive=false;
							case_tree_after[herbe.get(j)._x/32][herbe.get(j)._y/32] = 0;
							herbe.get(j)._y = -1;
							herbe.get(j)._x = -1;
							//nb_proies_vivantes --;
							agents_prey.get(i).faim = Math.max(0, agents_prey.get(i).faim - 15);
						}
					}
				}
			//}
		}
//prois feu-----------------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_prey.size();i++){
			for(int j =0; j< agents_prey.size();j++){

				//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
					if ((agents_prey.get(i)._x == agents_prey.get(j)._x)
						&&(agents_prey.get(i)._y == agents_prey.get(j)._y)
						&&(agents_prey.get(i)._alive) && i != j){

						if (agents_prey.get(j)._alive==true && agents_prey.get(j).infire && agents_prey.get(i).infire == false){

							agents_prey.get(i).infire = true ;
							//case_tree_after[herbe.get(j)._x/32][herbe.get(j)._y/32] = 0;
							agents_prey.get(i).fire_count = 350;
							//nb_proies_vivantes --;
					
						}
					}
				}
			//}
		}

//case en feu prois-------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_prey.size();i++){
			if(agents_prey.get(i)._alive && case_world_pred[agents_prey.get(i)._x/32][agents_prey.get(i)._y/32] == 20){

				agents_prey.get(i).infire = true ;
				agents_prey.get(i).fire_count = 350;

			}

		}
//pred feu--------------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_pred.size();i++){
			for(int j =0; j< agents_pred.size();j++){

				//if(((agents_prey.get(j)._x % 32) == 0 && (agents_prey.get(j)._y % 32) == 0) && ((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0) ){
					
					if ((agents_pred.get(i)._x == agents_pred.get(j)._x)
						&&(agents_pred.get(i)._y == agents_pred.get(j)._y)
						&&(agents_pred.get(i)._predator) && i != j){

						if (agents_pred.get(j)._predator==true && agents_pred.get(j).infire && agents_pred.get(i).infire == false){

							agents_pred.get(i).infire = true ;
							//case_tree_after[herbe.get(j)._x/32][herbe.get(j)._y/32] = 0;
							agents_pred.get(i).fire_count = 350;
							//nb_proies_vivantes --;
					
						}
					}
				}
			//}
		}
//case en feu pred--------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_pred.size();i++){
			if(agents_pred.get(i)._predator&& case_world_pred[agents_pred.get(i)._x/32][agents_pred.get(i)._y/32] == 20){

				agents_pred.get(i).infire = true ;
				agents_pred.get(i).fire_count = 350;

			}

		}

//repro proies-----------------------------------------------------------------------------------------------------------------

		for(int i =0; i< agents_prey.size();i++){
			if((agents_prey.get(i)._x % 32) == 0 && (agents_prey.get(i)._y % 32) == 0){

				if(agents_prey.get(i)._alive && agents_prey.get(i).tempsImmobile == 0 && agents_prey.get(i).faim <35 && agents_prey.get(i).infire == false){

					if(repro_prey > Math.random()){

						int x = agents_prey.get(i)._x;
						int y = agents_prey.get(i)._y;
						PreyAgent mouton = new PreyAgent(x, y, this);

						if(agents_prey.get(i).noir == true){
							mouton.change_color(0);
						}else if(agents_prey.get(i).blanc == true){
							mouton.change_color(1);
						}else{
							mouton.change_color(2);
						}

						this.add(mouton);
						nb_proies_vivantes++;
			
					}
				}
			}
		}

// Reproduction des prédateurs-------------------------------------------------------------------------------------------


		for (int i = 0; i < agents_pred.size(); i++) {
    		PredatorAgent pred = agents_pred.get(i);
    
    		// Vérifie si sur une case de la grille (32x32)
    		if ((pred._x % 32) == 0 && (pred._y % 32) == 0) {
        		// Conditions de base pour la reproduction
        		if (pred._predator && pred.faim < 29 && pred.tempsImmobile == 0 && !pred.malade && !pred.infire) {
            		// Cas 1: Parent NON muteur
           			if (!pred.muter) {
                		// Reproduction normale
                		if (Math.random() < repro_pred) {
                    		PredatorAgent enfant = new PredatorAgent(pred._x, pred._y, this);
                    
                    		// 50% de chance de mutation
							if(Math.random() < 0.02 && mutant == false){
								enfant.muter = true;
								mutant = true;
							}else{
								enfant.muter = false;
							}
                    	
                    
                    		this.add(enfant);
                    		nb_pred_vivantes++;
                		}
            		}
            		// Cas 2: Parent muteur
            		else {
                		// Reproduction avec mutation forcée
                		if (Math.random() < repro_pred_muter) { // Probabilité réduite de moitié
                    		PredatorAgent enfant = new PredatorAgent(pred._x, pred._y, this);
                    		enfant.muter = true; // Mutation obligatoire
                    
                    		this.add(enfant);
                    		nb_pred_vivantes++;
                		}
            		}
        		}
    		}
		}

//famine pred----------------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_pred.size();i++){
			if((agents_pred.get(i)._x % 32) == 0 && (agents_pred.get(i)._y % 32) == 0 && agents_pred.get(i).tempsImmobile == 0){

				agents_pred.get(i).faim++;

				if((agents_pred.get(i).faim > faim)&&(agents_pred.get(i)._predator)){
					agents_pred.get(i)._predator =false;
					agents_pred.get(i)._x = -1;
					agents_pred.get(i)._y = -1;
					nb_pred_vivantes--;
				}
			}
		}

//famine proi---------------------------------------------------------------------------------------------------------------


		for(int i =0; i< agents_prey.size();i++){
			if((agents_prey.get(i)._x % 32) == 0 && (agents_prey.get(i)._y % 32) == 0){
				if(agents_prey.get(i).tempsImmobile == 0){

					agents_prey.get(i).faim++;

					if((agents_prey.get(i).faim > faim_p)&&(agents_prey.get(i)._alive)){
						agents_prey.get(i)._alive =false;
						nb_proies_vivantes--;
					}
				}
			}
		}

//spawn arbre-------------------------------------------------------------------------------------------------------------------


		int time = 0;
		if(time % 32 != 0){
			time++;
		}else{
			time = 0;
			for (int x = 0; x < _dx; x += spriteLength)
            	for (int y = 0; y < _dy; y += spriteLength){
                	if (repro_tree >= Math.random() && case_world_pred[x / spriteLength][y / spriteLength] == 7 && case_tree_after[x / spriteLength][y / spriteLength] == 0){
						Tree arbre = new Tree(x, y, this);
						this.add(arbre);
						case_tree[x/32][y/32] = arbre;
						case_tree_after[x/32][y/32] = 2;

					}else if(repro_tree_8 >= Math.random() && case_world_pred[x / spriteLength][y / spriteLength] == 8 && case_tree_after[x / spriteLength][y / spriteLength] == 0){
						Tree arbre = new Tree(x, y, this);
						this.add(arbre);
						case_tree[x/32][y/32] = arbre;
						case_tree_after[x/32][y/32] = 2;
					}
				}
		}

//spawn herbe-----------------------------------------------------------------------------------------------------------------


		int time1 = 0;
		if(time1 % 32 != 0){
			time1++;
		}else{
			time1 = 0;
			for (int x = 0; x < _dx; x += spriteLength)
            	for (int y = 0; y < _dy; y += spriteLength)
                	if ((repro_herbe >= Math.random() && case_world_pred[x / spriteLength][y / spriteLength] == 7 && case_tree_after[x / spriteLength][y / spriteLength] == 0) || (repro_herbe_seche >= Math.random() && case_world_pred[x / spriteLength][y / spriteLength] == 0 && case_tree_after[x / spriteLength][y / spriteLength] == 0)){
						if(case_world_pred[x / spriteLength][y / spriteLength] == 7 ){
							Herbe herbe = new Herbe(x, y, this);
							this.add(herbe);
							//case_tree[x/32][y/32] = arbre;
							case_tree_after[x/32][y/32] = 2;
						}else{
							Herbe herbe = new Herbe(x, y, this);
							herbe.real_herbe = herbe.tab_herbe[3];
							this.add(herbe);
							//case_tree[x/32][y/32] = arbre;
							case_tree_after[x/32][y/32] = 2;

						}
					}
		}

// Supprimer les agents morts de la liste -----------------------------------------------------------------------------------------------------------------
		// Supprimer les prédateurs morts
		int predatorRemovals = 0;
		int maxRemovalsPerStep = 300;
		Iterator<PredatorAgent> predatorIterator = agents_pred.iterator();
		while (predatorIterator.hasNext() && predatorRemovals < maxRemovalsPerStep) {
    		PredatorAgent predator = predatorIterator.next();
    		if (!predator._predator) {
       			predatorIterator.remove();
        		predatorRemovals++;
    		}
		}

		// Supprimer les proies mortes
		int preyRemovals = 0;
		Iterator<PreyAgent> preyIterator = agents_prey.iterator();
		while (preyIterator.hasNext() && preyRemovals < maxRemovalsPerStep) {
    		PreyAgent prey = preyIterator.next();
    		if (!prey._alive) {
        		preyIterator.remove();
        		preyRemovals++;
    		}
		}

		// Supprimer les herbes mortes
		int herbeRemovals = 0;
		Iterator<Herbe> herbeIterator = herbe.iterator();
		while (herbeIterator.hasNext() && herbeRemovals < maxRemovalsPerStep) {
    		Herbe prey = herbeIterator.next();
    		if (!prey._alive) {
        		herbeIterator.remove();
        		herbeRemovals++;
    		}
		}
		// Supprimer les arbres morts
		int treeRemovals = 0;
		Iterator<Tree> treeIterator = tree.iterator();
		while (treeIterator.hasNext() && treeRemovals < maxRemovalsPerStep) {
    		Tree prey = treeIterator.next();
    		if (!prey._alive) {
        		treeIterator.remove();
        		treeRemovals++;
    		}
		}


//step agent----------------------------------------------------------------------------------------------------------------------


		for ( int i = 0 ; i != agents_pred.size() ; i++ )
		{
		
				agents_pred.get(i).step();
			
			
		}
		for ( int i = 0 ; i != agents_prey.size() ; i++ )
		{
			
				agents_prey.get(i).step();
			
			
		}
		
		for ( int i = 0 ; i != tree.size() ; i++ )
		{
				
				tree.get(i).step();
			
			
		}
		for ( int i = 0 ; i != camps.size() ; i++ )
		{
		
				//System.out.println(camps.get(i).stock);
			
			
		}for ( int i = 0 ; i != meteors.size() ; i++ )
		{
		
				meteors.get(i).step();
			
			
		}
		//System.out.println(nb_tree);

//move--------------------------------------------------------------------------------------------------------------------


		for ( int i = 0 ; i != agents_prey.size() ; i++ )
		{
			
				agents_prey.get(i).move();
			
			
		}
		for ( int i = 0 ; i != agents_pred.size() ; i++ )
		{
		
				agents_pred.get(i).move();
			
			
		}


//print-------------------------------------------------------------------------------------------------------------------

		System.out.print("tree : " + nb_tree);
		System.out.print(" | predateur : " + (nb_pred_vivantes+1));
		System.out.print(" | prois : " + nb_proies_vivantes);

		
		if(mutant){
			System.out.print(" | mutant : oui ");
		}else{
			System.out.print(" | mutant : non ");
		}

		if(malade){
			System.out.print(" | malade : oui ");
		}else{
			System.out.print(" | malade : non ");
		}
		

		System.out.println();
		
		
	}

	



	public void display(Graphics g, int spriteSize) {

		Graphics2D g2 = (Graphics2D) g;
		thunder_world = false;
	
		// Calculer les facteurs d'échelle
		float scaleX = (float) MyEcosystem_predprey.frame.getWidth() / _dx;
		float scaleY = (float) MyEcosystem_predprey.frame.getHeight() / _dy;
	
		// Appliquer le facteur d'échelle
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(scaleX, scaleY);
		g2.setTransform(scaleTransform);
	
// Dessiner l'herbe ------------------------------------------------------------------------------
		for (int i = 0; i < herbe.size(); i++) {
			if (herbe.get(i)._alive) {
				double posX = herbe.get(i).getX();
				double posY = herbe.get(i).getY();
	
				// Créer une transformation pour l'agent
				AffineTransform at = new AffineTransform();
				at.translate(posX, posY);
	
				if (night) {
					if (herbe.get(i).real_herbe == herbe.get(i).herbe) {
						g2.drawImage(herbe.get(i).herbe_night, at, MyEcosystem_predprey.frame);
					} else if (herbe.get(i).real_herbe == herbe.get(i).herbe1) {
						g2.drawImage(herbe.get(i).herbe1_night, at, MyEcosystem_predprey.frame);
					} else if (herbe.get(i).real_herbe == herbe.get(i).herbe2) {
						g2.drawImage(herbe.get(i).herbe2_night, at, MyEcosystem_predprey.frame);
					} else {
						g2.drawImage(herbe.get(i).herbe3_night, at, MyEcosystem_predprey.frame);
					}
				} else {
					g2.drawImage(herbe.get(i).real_herbe, at, MyEcosystem_predprey.frame);
				}
			}
		}

	
// Dessiner les proies --------------------------------------------------------------------------
		for (int i = 0; i < agents_prey.size(); i++) {
			if (agents_prey.get(i)._alive) {
				double posX = agents_prey.get(i).getX();
				double posY = agents_prey.get(i).getY();
	
				// Créer une transformation pour l'agent
				AffineTransform at = new AffineTransform();
				at.translate(posX, posY);
	
				if (night && agents_prey.get(i).blanc) {
					if (agents_prey.get(i)._orient == 0) {
						g2.drawImage(agents_prey.get(i).haut_night, at, MyEcosystem_predprey.frame);
					} else if (agents_prey.get(i)._orient == 1) {
						g2.drawImage(agents_prey.get(i).droite_night, at, MyEcosystem_predprey.frame);
					} else if (agents_prey.get(i)._orient == 2) {
						g2.drawImage(agents_prey.get(i).bas_night, at, MyEcosystem_predprey.frame);
					} else {
						g2.drawImage(agents_prey.get(i).gauche_night, at, MyEcosystem_predprey.frame);
					}
				} else{
					if (agents_prey.get(i)._orient == 0) {
						g2.drawImage(agents_prey.get(i).haut, at, MyEcosystem_predprey.frame);
					} else if (agents_prey.get(i)._orient == 1) {
						g2.drawImage(agents_prey.get(i).droite, at, MyEcosystem_predprey.frame);
					} else if (agents_prey.get(i)._orient == 2) {
						g2.drawImage(agents_prey.get(i).bas, at, MyEcosystem_predprey.frame);
					} else {
						g2.drawImage(agents_prey.get(i).gauche, at, MyEcosystem_predprey.frame);
					}
				}
				if(agents_prey.get(i).fire_count > 0){
					g2.drawImage(agents_prey.get(i).tab_feu[agents_prey.get(i).fire_count % 24],agents_prey.get(i).getX(), agents_prey.get(i).getY() , 32 , 32, MyEcosystem_predprey.frame);
			

				}
			}
		}
	
// Dessiner les prédateurs ----------------------------------------------------------------------------
		for (int i = 0; i < agents_pred.size(); i++) {
			if (agents_pred.get(i)._predator) {
				double posX = agents_pred.get(i).getX();
				double posY = agents_pred.get(i).getY();
	
				// Créer une transformation pour l'agent
				AffineTransform at = new AffineTransform();
				at.translate(posX, posY);
	
				if (night) {

					if(agents_pred.get(i).malade){

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut_malade_night, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite_malade_night, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas_malade_night, at, MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche_malade_night, at, MyEcosystem_predprey.frame);
						}

					}else if(agents_pred.get(i).muter){

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut_night_muter, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite_night_muter, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas_night_muter, at, MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche_night_muter, at, MyEcosystem_predprey.frame);
						}

					}else{

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut_night, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite_night, at, MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas_night, at, MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche_night, at, MyEcosystem_predprey.frame);
						}
					}
				}else {

					if(agents_pred.get(i).malade){

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut_malade, agents_pred.get(i).getX(),agents_pred.get(i).getY(),  MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite_malade, agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas_malade,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche_malade,  agents_pred.get(i).getX(),agents_pred.get(i).getY(),  MyEcosystem_predprey.frame);
						}
					}else if(agents_pred.get(i).muter){

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut_muter,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite_muter,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas_muter,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche_muter,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						}

					}else{

						if (agents_pred.get(i)._orient == 0) {
							g2.drawImage(agents_pred.get(i).haut,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 1) {
							g2.drawImage(agents_pred.get(i).droite,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else if (agents_pred.get(i)._orient == 2) {
							g2.drawImage(agents_pred.get(i).bas,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(agents_pred.get(i).gauche,  agents_pred.get(i).getX(),agents_pred.get(i).getY(), MyEcosystem_predprey.frame);
						}
					}
				}

				if(agents_pred.get(i).fire_count > 0){
					g2.drawImage(agents_pred.get(i).tab_feu[agents_pred.get(i).fire_count % 24],agents_pred.get(i).getX(), agents_pred.get(i).getY() , 32 , 32, MyEcosystem_predprey.frame);
			

				}
			}
		}
	
// Dessiner les arbres -----------------------------------------------------------------
		for (int i = 0; i < 60; i++) {
			for (int j = 0; j < 40; j++) {
				if (case_tree[i][j] != null && case_tree[i][j]._alive) {
					if (case_tree[i][j].infire == false) {
						if (night) {
							g2.drawImage(case_tree[i][j].tree_night,
								case_tree[i][j].getX() - 16,
								case_tree[i][j].getY() - 64,
								spriteSize * 2, spriteSize * 3,
								MyEcosystem_predprey.frame);
						} else {
							g2.drawImage(case_tree[i][j].tree,
								case_tree[i][j].getX() - 16,
								case_tree[i][j].getY() - 64,
								spriteSize * 2, spriteSize * 3,
								MyEcosystem_predprey.frame);
						}
					} else {
						if (case_tree[i][j].fire > 0) {
							// Définir les couleurs du dégradé (flamme)
							Color[] colors = { 
								new Color(255, 140, 0, 140),
								new Color(255, 165, 0, 90),
								new Color(255, 200, 100, 40),
								new Color(255, 255, 255, 0)
							};                  
							float[] fractions = { 0.0f, 0.3f, 0.5f, 0.8f };
	
							// Définir le centre et le rayon du dégradé
							Point2D center = new Point2D.Float(case_tree[i][j].getX(), case_tree[i][j].getY());
							float radius = 150; // Rayon du cercle lumineux
	
							// Créer le dégradé radial
							RadialGradientPaint gradient = new RadialGradientPaint(center, radius, fractions, colors);
	
							// Appliquer le dégradé
							g2.setPaint(gradient);
	
							// Appliquer la transparence
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
	
							// Dessiner le cercle
							float radiusVariation = (float) (Math.sin(System.currentTimeMillis() * 0.01)) * 10;
							float dynamicRadius = radius + radiusVariation;
	
							// Dessiner le cercle avec le rayon dynamique
							g2.fillOval(case_tree[i][j].getX() - (int) dynamicRadius / 2, case_tree[i][j].getY() - (int) dynamicRadius / 2, (int) dynamicRadius, (int) dynamicRadius);
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	
							g2.drawImage(case_tree[i][j].burn[case_tree[i][j].fire % 15],
								case_tree[i][j].getX() - 16,
								case_tree[i][j].getY() - 64,
								spriteSize * 2, spriteSize * 3,
								MyEcosystem_predprey.frame);
						}
					}
	
// Dessiner un cercle lumineux si la foudre frappe ------------------------------------------------------
					if (case_tree[i][j].thunder > 0) {
						thunder_world = true;
						// Définir les couleurs du dégradé (bleu et blanc transparent)
						Color[] colors = { 
							new Color(173, 216, 230, 180),
							new Color(173, 216, 230, 75),
							new Color(173, 216, 230, 50),
							new Color(255, 255, 255, 0)
						};                    
						float[] fractions = { 0.0f, 0.3f, 0.7f, 1.0f };
	
						// Définir le centre et le rayon du dégradé
						Point2D center = new Point2D.Float(case_tree[i][j].getX(), case_tree[i][j].getY());
						float radius = 4000; // Rayon du cercle lumineux
	
						// Créer le dégradé radial
						RadialGradientPaint gradient = new RadialGradientPaint(center, radius, fractions, colors);
	
						// Appliquer le dégradé
						g2.setPaint(gradient);
	
						// Appliquer la transparence
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
	
						// Dessiner le cercle
						g2.fillOval(case_tree[i][j].getX() - (int) radius / 2, case_tree[i][j].getY() - (int) radius / 2, (int) radius, (int) radius);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	
						// Dessiner l'éclair
						g2.drawImage(case_tree[i][j].tab[(int) (Math.random() * 4)],
							case_tree[i][j].getX() - 90,
							case_tree[i][j].getY() - 1150,
							230, 1200, MyEcosystem_predprey.frame);
					}

//dessine les camps ---------------------------------------------------------------------------
				} else if (case_camps[i][j] != null && case_camps[i][j]._alive) {
					int posX = case_camps[i][j].getX();
					int posY = case_camps[i][j].getY();
	
					// Créer une transformation pour l'agent
					AffineTransform at = new AffineTransform();
					at.translate(posX-8, posY);
					Color[] colors = { 
						new Color(255, 140, 0, 140),
						new Color(255, 165, 0, 90),
						new Color(255, 200, 100, 40),
						new Color(255, 255, 255, 0)
					};                  
					float[] fractions = { 0.0f, 0.3f, 0.5f, 0.8f };
	
					// Définir le centre et le rayon du dégradé
					Point2D center = new Point2D.Float(case_camps[i][j].getX(), case_camps[i][j].getY());
					float radius = 250; // Rayon du cercle lumineux
	
					// Créer le dégradé radial
					RadialGradientPaint gradient = new RadialGradientPaint(center, radius, fractions, colors);
	
					// Appliquer le dégradé
					g2.setPaint(gradient);
	
					// Appliquer la transparence
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
	
					// Dessiner le cercle
					float radiusVariation = (float) (Math.sin(System.currentTimeMillis() * 0.01)) * 20;
					float dynamicRadius = radius + radiusVariation;
					if(!isRaining){
					// Dessiner le cercle avec le rayon dynamique
					g2.fillOval((case_camps[i][j].getX() - (int) dynamicRadius / 2) + 10, case_camps[i][j].getY() - (int) dynamicRadius / 2, (int) dynamicRadius, (int) dynamicRadius);
					}
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					
	
					g2.drawImage(case_camps[i][j].bois, posX, posY, 32, 32, MyEcosystem_predprey.frame);
					if(!isRaining){
						g2.drawImage(case_camps[i][j].tab_feu[case_camps[i][j].fire_count % 24],
							case_camps[i][j].getX()  ,
							case_camps[i][j].getY() - 29,
							spriteSize, spriteSize + 16,
							MyEcosystem_predprey.frame);
						case_camps[i][j].fire_count++;
					}
//dessine feu----------------------------------------------------------------------------
				}

				
			}
		}
		feu_meteore++;
//dessine meteore--------------------------------------------------------------------------
		for ( int i = 0 ; i != meteors.size() ; i++ )
		{	if((meteors.get(i).indice_explos2 >= 72 || meteors.get(i).indice_explos2 < 72 )&& meteors.get(i).temps_atteri > 33  &&  meteors.get(i).endroit != 3){
				g2.drawImage(meteors.get(i).fissure1,
					meteors.get(i).getX()-160 ,
					meteors.get(i).getY()-170 ,
					spriteSize *15, spriteSize *15,
					MyEcosystem_predprey.frame);
		}else if((meteors.get(i).indice_explos2 >= 72 || meteors.get(i).indice_explos2 < 72 )&& meteors.get(i).temps_atteri > 33 &&  meteors.get(i).endroit == 3){
			g2.drawImage(meteors.get(i).fissure2,
				meteors.get(i).getX()-160 ,
				meteors.get(i).getY()-170 ,
				spriteSize *15, spriteSize *15,
				MyEcosystem_predprey.frame);
	}
		
			if(meteors.get(i).atterit == false){
				g2.drawImage(meteors.get(i).meteorite,
					meteors.get(i).getX() + 50,
					meteors.get(i).getY() - 225 ,
					spriteSize * 10, spriteSize * 10,
					MyEcosystem_predprey.frame);

			}

			
		
		}

		for ( int i = 0 ; i != meteors.size() ; i++ )
		{
			if(meteors.get(i).atterit == true){
			
				if(night){
					g2.drawImage(meteors.get(i).cratere_nigth,
						meteors.get(i).getX() ,
						meteors.get(i).getY() ,
						spriteSize * 5, spriteSize * 5,
						MyEcosystem_predprey.frame);
				}else{

					g2.drawImage(meteors.get(i).cratere,
						meteors.get(i).getX() ,
						meteors.get(i).getY() ,
						spriteSize * 5, spriteSize * 5,
						MyEcosystem_predprey.frame);
				}
			}
		}


			for(int i = 0; i < 60; i++){
				for(int j = 0; j < 40 ; j++){
					
	
					if(case_world_pred[i][j] == 20 && case_world_pred[i][j] != 3 && ((i < (atteriex -3 )) || (j < (atteriey-4)) || (i > (atteriex + 8)) || (j > (atteriey  +6)) )){
						int posX = (i*32);
					
						int posY = (j*32);
		
						// Créer une transformation pour l'agent
						AffineTransform at = new AffineTransform();
						at.translate(posX-8, posY);
						Color[] colors = { 
							new Color(255, 140, 0, 140),
							new Color(255, 165, 0, 90),
							new Color(255, 200, 100, 40),
							new Color(255, 255, 255, 0)
						};                  
						float[] fractions = { 0.0f, 0.3f, 0.5f, 0.8f };
		
						// Définir le centre et le rayon du dégradé
						Point2D center = new Point2D.Float((i*32), (j*32));
						float radius = 150; // Rayon du cercle lumineux
		
						// Créer le dégradé radial
						RadialGradientPaint gradient = new RadialGradientPaint(center, radius, fractions, colors);
		
						// Appliquer le dégradé
						g2.setPaint(gradient);
		
						// Appliquer la transparence
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
		
						// Dessiner le cercle
						float radiusVariation = (float) (Math.sin(System.currentTimeMillis() * 0.01)) * 10;
						float dynamicRadius = radius + radiusVariation;
		
						// Dessiner le cercle avec le rayon dynamique
						g2.fillOval((i*32)- (int) dynamicRadius / 2, (j*32) - (int) dynamicRadius / 2, (int) dynamicRadius, (int) dynamicRadius);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
	
						g2.drawImage(tab_feu1[feu_meteore % 24],
						(i*32),
						(j*32)  ,
						spriteSize , spriteSize ,
						MyEcosystem_predprey.frame);
	
	
					}
				}
			}
			





			for ( int i = 0 ; i != meteors.size() ; i++ )
		{
			
			if(meteors.get(i).atterit == true){

				

				

					if(meteors.get(i).indice_explos < 88){
						// Explosion doit couvrir tout l'écran (1.5x la taille)
						int explosionWidth = (int)(_dx * 1.5);
						int explosionHeight = (int)(_dy * 1.5);
					
						// Position centrée sur l'impact
						int explosionX = meteors.get(i).getX() - (explosionWidth / 2) +79;
						int explosionY = meteors.get(i).getY() - (explosionHeight / 2)+40;

						explosionX = Math.max(-explosionWidth / 4, explosionX);  // Permet un dépassement sans crash
    					explosionY = Math.max(-explosionHeight / 4, explosionY);
					
						g2.drawImage(meteors.get(i).explos[meteors.get(i).indice_explos],
							explosionX,
							explosionY,
							explosionWidth, explosionHeight,
							MyEcosystem_predprey.frame);
					
						meteors.get(i).indice_explos++;
					}
						
					if(meteors.get(i).indice_explos1 < 48){
						// Explosion doit couvrir tout l'écran (1.5x la taille)
						int explosionWidth = (int)(_dx * 1.5);
						int explosionHeight = (int)(_dy * 1.5);
					
						// Position centrée sur l'impact
						int explosionX = meteors.get(i).getX() - (explosionWidth / 2) +74;
						int explosionY = meteors.get(i).getY() - (explosionHeight / 2)+40;

						explosionX = Math.max(-explosionWidth / 4, explosionX);  // Permet un dépassement sans crash
    					explosionY = Math.max(-explosionHeight / 4, explosionY);
					
						g2.drawImage(meteors.get(i).explos1[meteors.get(i).indice_explos1],
							meteors.get(i).getX()-150,
							meteors.get(i).getY()-190,
							450, 450,
							MyEcosystem_predprey.frame);
					
						meteors.get(i).indice_explos1++;

					}if(meteors.get(i).indice_explos2 < 72 && meteors.get(i).temps_atteri > 33){
					
						// Explosion doit couvrir tout l'écran (1.5x la taille)
						int explosionWidth = (int)(_dx * 1.5);
						int explosionHeight = (int)(_dy * 1.5);
					
						// Position centrée sur l'impact
						int explosionX = meteors.get(i).getX() - (explosionWidth / 2) +54;
						int explosionY = meteors.get(i).getY() - (explosionHeight / 2)+40;

						explosionX = Math.max(-explosionWidth / 4, explosionX);  // Permet un dépassement sans crash
    					explosionY = Math.max(-explosionHeight / 4, explosionY);
					
						g2.drawImage(meteors.get(i).explos2[meteors.get(i).indice_explos2],
							meteors.get(i).getX()-530,
							meteors.get(i).getY()-930,
							1200, 1200,
							MyEcosystem_predprey.frame);
					
						meteors.get(i).indice_explos2++;
						
					}
					

				
				
				}



			
			
			
			
		}
		
		
		boolean chaud = false;
		for(int i = 0; i < 60; i++){
			for(int j = 0; j < 40 ; j++){

				if(case_world_pred[i][j] == 20){
					chaud = true;
				}
			}
		}
		if (atterre && night == false && chaud == true) {
			// Appliquer un filtre rouge/orange semi-transparent
			g2.setColor(new Color(255, 80, 0, 150)); // Rouge/orange avec plus d'opacité
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)); // 60% d'opacité
			g2.fillRect(0, 0, _dx, _dy); // Couvrir toute la scène
		}
		
		
		
//dessine la pluis ----------------------------------------------------------------------------------------
		if (isRaining) {
			if (pluis_transition < 0.55f) {
				pluis_transition += 0.01f;	

			}

			if(night == false && thunder_world == false){
				// Appliquer un filtre de couleur nocturne (teinte bleutée)
				g2.setColor(new Color(0, 0, 07, 255)); // Bleu nuit avec 40 % d'opacité (100/255)
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pluis_transition)); // 40 % d'opacité
				g2.fillRect(0, 0, _dx, _dy); // Couvrir toute la scène
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // 40 % d'opacité

			}	
			// Appliquer un calque sombre pour assombrir la scène

			// Calculer les facteurs d'échelle
			double scaleX1 = (double) MyEcosystem_predprey.frame.getWidth() / _dx;
			double scaleY1 = (double) MyEcosystem_predprey.frame.getHeight() / _dy;

			// Appliquer le facteur d'échelle
			AffineTransform scaleTransform1 = new AffineTransform();
			scaleTransform.scale(scaleX1, scaleY1);
			g2.setTransform(scaleTransform1);

			// Dessiner la scène (herbe, proies, prédateurs, arbres...)


			// Dessiner la pluie si activée

			drawRain(g2, 2);
		}

		
		

		
}



	private void drawRain(Graphics2D g2, int frameCount) {
    	int rainDensity = 300;
    	int rainLength = 25;
    	int speed = 10; // Vitesse de chute

    	g2.setColor(new Color(173, 216, 230, 150));
    	g2.setStroke(new java.awt.BasicStroke(4));

    	for (int i = 0; i < rainDensity; i++) {
        	int x = (int) (Math.random() * _dx);
        	int y = (int) ((Math.random() * _dy + (frameCount * speed)) % _dy); // Ajout de speed
        
        	int x2 = x - 3;
        	int y2 = y + rainLength;
        
        	g2.drawLine(x, y, x2, y2);
    	}
	}
}
	

