import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class PredatorAgent extends Agent {
    // 1. Toutes vos variables d'instance (dans le même ordre)
    boolean _predator;
    boolean muter = false;
    boolean malade = false;
    boolean gueris = false;
    boolean constructeur;
    boolean en_cour = false;
    boolean deja_poser = false;
    boolean infire = false;
    
    int faim;
    int bois = 0;
    int temps_malade = 0;
    int stock = 0;
    int tempsImmobile = 0;
    int xmove = 1;
    int ymove = 1;
    int fire_count = 0;
    
    double proba;
    
    Tree tree_cour;
    
    Image[] tab_feu;
    
    // Textures (maintenant références aux textures partagées)
    Image haut, bas, droite, gauche;
    Image haut_night, bas_night, droite_night, gauche_night;
    Image haut_malade, bas_malade, droite_malade, gauche_malade;
    Image haut_malade_night, bas_malade_night, droite_malade_night, gauche_malade_night;
    Image haut_muter, bas_muter, droite_muter, gauche_muter;
    Image haut_night_muter, bas_night_muter, droite_night_muter, gauche_night_muter;
    Image feu, feu1, feu2, feu3;

    // 2. Dictionnaire de textures partagées (static)
    private static final Map<String, Image> TEXTURES = new HashMap<>();
    
    static {
        try {
            // Normal
            TEXTURES.put("haut", ImageIO.read(new File("./sprite_gars/agent1.png")));
            TEXTURES.put("bas", ImageIO.read(new File("./sprite_gars/agent.png")));
            TEXTURES.put("droite", ImageIO.read(new File("./sprite_gars/agent2.png")));
            TEXTURES.put("gauche", ImageIO.read(new File("./sprite_gars/agent3.png")));

            // Night
            TEXTURES.put("haut_night", ImageIO.read(new File("./sprite_gars_night/agent1.png")));
            TEXTURES.put("bas_night", ImageIO.read(new File("./sprite_gars_night/agent.png")));
            TEXTURES.put("droite_night", ImageIO.read(new File("./sprite_gars_night/agent2.png")));
            TEXTURES.put("gauche_night", ImageIO.read(new File("./sprite_gars_night/agent3.png")));

            // Malade
            TEXTURES.put("haut_malade", ImageIO.read(new File("./sprite_gars/sprite_gars_malade/agent1.png")));
            TEXTURES.put("bas_malade", ImageIO.read(new File("./sprite_gars/sprite_gars_malade/agent.png")));
            TEXTURES.put("droite_malade", ImageIO.read(new File("./sprite_gars/sprite_gars_malade/agent2.png")));
            TEXTURES.put("gauche_malade", ImageIO.read(new File("./sprite_gars/sprite_gars_malade/agent3.png")));

            // Malade Night
            TEXTURES.put("haut_malade_night", ImageIO.read(new File("./sprite_gars_night/sprite_malade_night/agent1.png")));
            TEXTURES.put("bas_malade_night", ImageIO.read(new File("./sprite_gars_night/sprite_malade_night/agent.png")));
            TEXTURES.put("droite_malade_night", ImageIO.read(new File("./sprite_gars_night/sprite_malade_night/agent2.png")));
            TEXTURES.put("gauche_malade_night", ImageIO.read(new File("./sprite_gars_night/sprite_malade_night/agent3.png")));

            // Muter
            TEXTURES.put("haut_muter", ImageIO.read(new File("./sprite_gars_muter/agent1.png")));
            TEXTURES.put("bas_muter", ImageIO.read(new File("./sprite_gars_muter/agent.png")));
            TEXTURES.put("droite_muter", ImageIO.read(new File("./sprite_gars_muter/agent2.png")));
            TEXTURES.put("gauche_muter", ImageIO.read(new File("./sprite_gars_muter/agent3.png")));

            // Muter Night
            TEXTURES.put("haut_night_muter", ImageIO.read(new File("./sprite_gars_muter/night/agent1.png")));
            TEXTURES.put("bas_night_muter", ImageIO.read(new File("./sprite_gars_muter/night/agent.png")));
            TEXTURES.put("droite_night_muter", ImageIO.read(new File("./sprite_gars_muter/night/agent2.png")));
            TEXTURES.put("gauche_night_muter", ImageIO.read(new File("./sprite_gars_muter/night/agent3.png")));

            // Feu
            TEXTURES.put("feu", ImageIO.read(new File("./ezgif-split/fire/feu.png")));
            TEXTURES.put("feu1", ImageIO.read(new File("./ezgif-split/fire/feu1.png")));
            TEXTURES.put("feu2", ImageIO.read(new File("./ezgif-split/fire/feu2.png")));
            TEXTURES.put("feu3", ImageIO.read(new File("./ezgif-split/fire/feu2.png")));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // 3. Constructeur identique à votre version
    public PredatorAgent(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _predator = true;
        malade = false;
        constructeur = Math.random() < 0.01;
        faim = 0;
        proba = 0.98;

        // Assignation des références aux textures partagées
        haut = TEXTURES.get("haut");
        bas = TEXTURES.get("bas");
        droite = TEXTURES.get("droite");
        gauche = TEXTURES.get("gauche");
        
        haut_night = TEXTURES.get("haut_night");
        bas_night = TEXTURES.get("bas_night");
        droite_night = TEXTURES.get("droite_night");
        gauche_night = TEXTURES.get("gauche_night");
        
        haut_malade = TEXTURES.get("haut_malade");
        bas_malade = TEXTURES.get("bas_malade");
        droite_malade = TEXTURES.get("droite_malade");
        gauche_malade = TEXTURES.get("gauche_malade");
        
        haut_malade_night = TEXTURES.get("haut_malade_night");
        bas_malade_night = TEXTURES.get("bas_malade_night");
        droite_malade_night = TEXTURES.get("droite_malade_night");
        gauche_malade_night = TEXTURES.get("gauche_malade_night");
        
        haut_muter = TEXTURES.get("haut_muter");
        bas_muter = TEXTURES.get("bas_muter");
        droite_muter = TEXTURES.get("droite_muter");
        gauche_muter = TEXTURES.get("gauche_muter");
        
        haut_night_muter = TEXTURES.get("haut_night_muter");
        bas_night_muter = TEXTURES.get("bas_night_muter");
        droite_night_muter = TEXTURES.get("droite_night_muter");
        gauche_night_muter = TEXTURES.get("gauche_night_muter");
        
        feu = TEXTURES.get("feu");
        feu1 = TEXTURES.get("feu1");
        feu2 = TEXTURES.get("feu2");
        feu3 = TEXTURES.get("feu3");
        
        tab_feu = new Image[]{feu, feu, feu, feu, feu, feu, feu1, feu1, feu1, feu1, feu1, feu1, feu2, feu2, feu2, feu2, feu2, feu2, feu3, feu3, feu3, feu3, feu3, feu3};
    }



	public int getX() {
    	return _x;
	}

	public int getY() {
    	return _y;
	}

	public void move(){
		if(tempsImmobile == 0 && _predator){

			switch (_orient) {
				case 0: // nord	
					_y = (_y - ymove + _world.getHeight()) % _world.getHeight();
					break;
				case 1: // est
					_x = (_x + xmove + _world.getWidth()) % _world.getWidth();
					break;
				case 2: // sud
					_y = (_y + ymove + _world.getHeight()) % _world.getHeight();
					break;
				case 3: // ouest
					_x = (_x - xmove + _world.getWidth()) % _world.getWidth();
					break;
			}
		}
	}
	
	public void step( )
	{
		
		
		if(_predator){
			if(!muter){



//malade---------------------------------------------------------------------------------------------------------------------
			
			if(_world.isRaining && _world.malade == false && Math.random() < 0.002 && (_world.duree_pluis % 100) == 0 && gueris == false && malade == false){

				malade = true;
				_world.malade = true;
				temps_malade = 800;


			}
			if(temps_malade > 1){
				temps_malade--;
			}
			if(temps_malade == 1 && malade){

				if(0.9 < Math.random()){
					gueris = true;
					temps_malade = 0;
					malade = false;
				}else{
					_predator = false;
					malade = false;
					temps_malade = 0;
					_x = -1;
					_y = -1;
					_world.nb_pred_vivantes--;
				}

			

				
			}
			

//depos camps------------------------------------------------------------------------------------------------------------
			if(bois == 3 && deja_poser == false && _world.camps_totale < 4 && _world.case_tree_after[_x / 32][_y / 32] != 9 && Math.random() < 0.01 && (_x % 32) == 0 && (_y % 32) == 0 && _world.case_world_pred[_x / 32][_y / 32] != 8 ){

				_world.case_tree_after[_x / 32][_y / 32] = 9;
				Camps c = new Camps(_x, _y, _world);
				c.stock = 10;
				_world.add(c);
				deja_poser = true;
				bois = 0;
				_world.camps_totale++;
				_world.case_camps[_x / 32][_y / 32] = c;

			}

//en feu----------------------------------------------------------------------------------------------------------------
			if(fire_count == 1 && infire == true){
				_predator = false;
				infire = false;
				fire_count--;
				_world.nb_pred_vivantes--;
			}
			if(fire_count > 1){
				fire_count --;
			}
			if(_world.isRaining == true && infire == true){

				infire = false;
				fire_count = 0;

			}

//faim predateur, ralentissement-----------------------------------------------------------------------------------------------
			if(faim >= 30){
				proba = 0.73;
			}
			else{
				proba = 0.95;
			}
			boolean chaud = false;
			for(int i = 0; i < 60; i++){
				for(int j = 0; j < 40 ; j++){

					if(_world.case_world_pred[i][j] == 20){
					chaud = true;
					}
				}
			}

			if(chaud == true){
				proba = 0.73;
			}else{
				proba = 0.95;
			}

//si il fait nuit------------------------------------------------------------------------------------------------------------
			if(_world.night == true){

				proba = 0.73;
			}else{

				proba = 0.98;
			}
//proximiter d'un camps ----------------------------------------------------------------------------------------------------------
			final int forwardRange1 = 3; // 5 cases vers l'avant
    				final int sideRange1 = 2;    // 1 case sur les côtés (pour former un rectangle)
    
    				campLoop:
    				for (Camps camp : _world.camps) {
        				if (!camp._alive) continue;
        
       				 // Rectangle vers la droite (x+)
        				for (int step = 1; step <= forwardRange1; step++) {
            				for (int side = -sideRange1; side <= sideRange1; side++) {
                				int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && _world.night)) {
									proba = 0.98;
                   					break campLoop;
               					}
            				}
        				}
        
        				// Rectangle vers la gauche (x-)
        				for (int step = 1; step <= forwardRange1; step++) {
            				for (int side = -sideRange1; side <= sideRange1; side++) {
                				int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && _world.night)) {
                   					
									proba = 0.98;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le bas (y+)
        				for (int step = 1; step <= forwardRange1; step++) {
            				for (int side = -sideRange1; side <= sideRange1; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && _world.night)) {
									proba = 0.98;
							
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le haut (y-)
        				for (int step = 1; step <= forwardRange1; step++) {
           					for (int side = -sideRange1; side <= sideRange1; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && _world.night)) {
                    				proba = 0.98;
							
                    				break campLoop;
                				}
            				}
        				}
   					}

//immobile-------------------------------------------------------------------------------------------------------------------
			if(tempsImmobile > 0){
				
				tempsImmobile--;
			}if(tempsImmobile == 0 && en_cour == true){
			

					
					en_cour = false;
					tree_cour._alive = false;

				
			}

			if ((_x % 32) != 0 || (_y % 32) != 0) {
   

       		}else {

			if(Math.random() < proba && tempsImmobile == 0){
				

//bord map--------------------------------------------------------------------------------------------------------------------------

			
				int regle = 0;
				//collision
				// Vérifier les collisions avec les bords (case_world_pred[x][y] == 3)
				if (_world.case_world_pred[((_x + 32) % _world.getWidth()) / 32][_y / 32] == 3) {
    			// Collision à droite : déplacer à gauche
    				regle++;
    				_orient = 3; // Orientation vers la gauche
				}else if (_world.case_world_pred[_x / 32][((_y + 32) % _world.getHeight()) / 32] == 3) {
    			// Collision en bas : déplacer vers le haut
    				regle++;
    				_orient = 0; // Orientation vers le haut
				} else if (_world.case_world_pred[((_x - 32 + _world.getWidth()) % _world.getWidth()) / 32][_y / 32] == 3) {
    			// Collision à gauche : déplacer à droite
    				regle++;
    				_orient = 1; // Orientation vers la droite
				} else if (_world.case_world_pred[_x / 32][((_y - 32 + _world.getHeight()) % _world.getHeight()) / 32] == 3) {
    			// Collision en haut : déplacer vers le bas
    				regle++;
    				_orient = 2; // Orientation vers le bas
				}

				//collision arbre-------------------------------------------------------------------------------------------------------
				if (regle == 0) {
    				List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));
   						 // Gérer la collision avec les arbres
    					for (int i = 0; i < _world.tree.size(); i++) {
        					Tree proi = _world.tree.get(i);

        						// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
        					if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y  && proi._alive == true) {
            					// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(1));

								}else{
									tree_cour = proi;
									_orient = 1;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
            				

        					} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive == true) {
            					// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(3));

								}else{
									tree_cour = proi;
									_orient = 3;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
            					

        					} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
            				// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(2));
								}else{
									tree_cour = proi;
									_orient = 2;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
						
            					
        					} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
            					// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){

									direction.remove(Integer.valueOf(0));
								}else{
									tree_cour = proi;
									_orient = 0;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
							
        					}
    					}
       
				
//collision camps----------------------------------------------------------------------------------------------------

					for (int i = 0; i < _world.camps.size(); i++) {
						Camps proi = _world.camps.get(i);

							// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
						if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y  && proi._alive == true) {
							if(stock > 0 && proi.stock < 15){
								proi.stock += stock % 15;
								stock = 0;
								tempsImmobile = 64;
								regle++;
								_orient = 1;
								break;

							}
							else if(proi.stock > 0 && faim >= 27){
								faim = 0;
								proi.stock--;
								tempsImmobile = 64;
								regle++;
								_orient = 1;
								break;

							}else{
								direction.remove(Integer.valueOf(1));
							}
							// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes

						} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive == true) {
							// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 27){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(3));
								}

							
							

						} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
						// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 27){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(2));
								}
							
							
					
							
						} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
							// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 27){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(0));
								}
						
						
						}
					}
					
					if(direction.size() != 4){
						if (!direction.isEmpty()) {
							_orient = direction.get((int) (Math.random() * direction.size()));
						}
						regle++;
						
					}
						
						
				
					
				}
//chasse prois------------------------------------------------------------------------------------------------------

				if (regle == 0) {
    				final int forwardRange = 1; // 5 cases vers l'avant
    				final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
    
    				campLoop:
    				for (PreyAgent camp : _world.agents_prey) {
        				if (!camp._alive) continue;
        
       				 // Rectangle vers la droite (x+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 17) || (camp._x == checkX && camp._y == checkY && stock <= 3) ) {
                					_orient = 1; // Droite
									regle++;
                   					break campLoop;
               					}
            				}
        				}
        
        				// Rectangle vers la gauche (x-)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 17) || (camp._x == checkX && camp._y == checkY && stock <= 3) ) {
                   					_orient = 3; // Gauche
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le bas (y+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 17) || (camp._x == checkX && camp._y == checkY && stock <= 3) ) {
                   					_orient = 2; // Bas
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le haut (y-)
        				for (int step = 1; step <= forwardRange; step++) {
           					for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 17) || (camp._x == checkX && camp._y == checkY && stock <= 3) ) {
                    				_orient = 0; // Haut
									regle++;
                    				break campLoop;
                				}
            				}
        				}
   					}
				}
				


//aller vers un camps-------------------------------------------------------------------------------------------------
				
				if (regle == 0) {
    				final int forwardRange = 13; // 5 cases vers l'avant
    				final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
    
    				campLoop:
    				for (Camps camp : _world.camps) {
        				if (!camp._alive) continue;
        
       				 // Rectangle vers la droite (x+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                					_orient = 1; // Droite
									regle++;
                   					break campLoop;
               					}
            				}
        				}
        
        				// Rectangle vers la gauche (x-)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                   					_orient = 3; // Gauche
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le bas (y+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                   					_orient = 2; // Bas
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le haut (y-)
        				for (int step = 1; step <= forwardRange; step++) {
           					for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                    				_orient = 0; // Haut
									regle++;
                    				break campLoop;
                				}
            				}
        				}
   					}
				}

				if(regle == 0){
					_orient = (int)(Math.random()*4);

					// met a jour: la position de l'agent (depend de l'orientation)
		 			
				}
			}else{
				if(tempsImmobile == 0){

					tempsImmobile = 32;
				}
			}
			}
		}else{

//muter__________________________________________________________________________________________________________________________
//_________________________________________________________________________________________________________________________________________

			
			
			

//depos camps--------------------------------------------------------------------------------------------------------------------
			if(bois == 3 && deja_poser == false && _world.camps_totale < 4 && _world.case_tree_after[_x / 32][_y / 32] != 9 && Math.random() < 0.01 && (_x % 32) == 0 && (_y % 32) == 0 && _world.case_world_pred[_x / 32][_y / 32] != 8 ){

				_world.case_tree_after[_x / 32][_y / 32] = 9;
				Camps c = new Camps(_x, _y, _world);
				c.stock = 10;
				_world.add(c);
				deja_poser = true;
				bois = 0;
				_world.camps_totale++;
				_world.case_camps[_x / 32][_y / 32] = c;

			}

//en feu---------------------------------------------------------------------------------------------------------------------------
			if(fire_count == 1 && infire == true){
				_predator = false;
				fire_count--;
				infire = false;
				_world.nb_pred_vivantes--;
			}
			if(fire_count > 1){
				fire_count --;
			}
			if(_world.isRaining == true && infire == true){

				infire = false;
				fire_count = 0;

			}

//faim predateur, ralentissement---------------------------------------------------------------------------------------------
			if(faim >= 30){
				proba = 0.79;
			}
			else{
				proba = 0.98;
			}

//si il fait nuit--------------------------------------------------------------------------------------------------------------
			if(_world.night == true){

				proba = 0.78;
			}else{

				proba = 0.98;
			}

//immobile--------------------------------------------------------------------------------------------------------------------
			if(tempsImmobile > 0){
				
				tempsImmobile--;
			}if(tempsImmobile == 0 && en_cour == true){
			

					
					en_cour = false;
					tree_cour._alive = false;

				
			}

			if ((_x % 32) != 0 || (_y % 32) != 0) {
   

       		}else {

			if(Math.random() < proba && tempsImmobile == 0){
				
//collision bord--------------------------------------------------------------------------------------------------------
				int regle = 0;
				//collision
				// Vérifier les collisions avec les bords (case_world_pred[x][y] == 3)
				if (_world.case_world_pred[((_x + 32) % _world.getWidth()) / 32][_y / 32] == 3) {
    			// Collision à droite : déplacer à gauche
    				regle++;
    				_orient = 3; // Orientation vers la gauche
				}else if (_world.case_world_pred[_x / 32][((_y + 32) % _world.getHeight()) / 32] == 3) {
    			// Collision en bas : déplacer vers le haut
    				regle++;
    				_orient = 0; // Orientation vers le haut
				} else if (_world.case_world_pred[((_x - 32 + _world.getWidth()) % _world.getWidth()) / 32][_y / 32] == 3) {
    			// Collision à gauche : déplacer à droite
    				regle++;
    				_orient = 1; // Orientation vers la droite
				} else if (_world.case_world_pred[_x / 32][((_y - 32 + _world.getHeight()) % _world.getHeight()) / 32] == 3) {
    			// Collision en haut : déplacer vers le bas
    				regle++;
    				_orient = 2; // Orientation vers le bas
				}
				//collision arbre-----------------------------------------------------------------------------------------------------------
				if (regle == 0) {
    				List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));
   						 // Gérer la collision avec les arbres
    					for (int i = 0; i < _world.tree.size(); i++) {
        					Tree proi = _world.tree.get(i);

        						// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
        					if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y  && proi._alive == true) {
            					// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(1));

								}else{
									tree_cour = proi;
									_orient = 1;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
            				

        					} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive == true) {
            					// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(3));

								}else{
									tree_cour = proi;
									_orient = 3;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
            					

        					} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
            				// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){
									direction.remove(Integer.valueOf(2));
								}else{
									tree_cour = proi;
									_orient = 2;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
						
            					
        					} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
            					// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
								if(bois >= 3 || deja_poser == true || _world.camps_totale > 3){

									direction.remove(Integer.valueOf(0));
								}else{
									tree_cour = proi;
									_orient = 0;
									tempsImmobile = 96;
									regle++;
									en_cour = true;
									bois++;
									if(bois > 3) bois = 3;
									break;
								}
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
								
							
        					}
    					}

	
//collision camps---------------------------------------------------------------------------------------------------------------

					for (int i = 0; i < _world.camps.size(); i++) {
						Camps proi = _world.camps.get(i);

							// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
						if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y  && proi._alive == true) {
							if(stock > 0 && proi.stock < 15){
								proi.stock += stock % 15;
								stock = 0;
								tempsImmobile = 64;
								regle++;
								_orient = 1;
								break;

							}
							else if(proi.stock > 0 && faim >= 17){
								faim = 0;
								proi.stock--;
								tempsImmobile = 64;
								regle++;
								_orient = 1;
								break;

							}else{
								direction.remove(Integer.valueOf(1));
							}
							// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes
							
							
								

							
							
						

						} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive == true) {
							// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 17){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(3));
								}

							
							

						} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
						// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 17){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(2));
								}
							
							
					
							
						} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
							// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
								if(stock > 0 && proi.stock < 15){
									proi.stock += stock % 15;
									stock = 0;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}
								else if(proi.stock > 0 && faim >= 17){
									faim = 0;
									proi.stock--;
									tempsImmobile = 64;
									regle++;
									_orient = 1;
									break;
	
								}else{
									direction.remove(Integer.valueOf(0));
								}
						
						
						}
					}
					
					if(direction.size() != 4){
						if (!direction.isEmpty()) {
							_orient = direction.get((int) (Math.random() * direction.size()));
						}
						regle++;
						
					}
						
						
				
					
				}

//chassse prois----------------------------------------------------------------------------------------------------------
				if (regle == 0) {
    				final int forwardRange = 3; // 5 cases vers l'avant
    				final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
    
    				campLoop:
    				for (PreyAgent camp : _world.agents_prey) {
        				if (!camp._alive) continue;
        
       				 // Rectangle vers la droite (x+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp.noir == true) || (camp._x == checkX && camp._y == checkY && stock <= 3  && camp.noir == true) ) {
                					_orient = 1; // Droite
									regle++;
                   					break campLoop;
               					}
            				}
        				}
        
        				// Rectangle vers la gauche (x-)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 20  && camp.noir == true) || (camp._x == checkX && camp._y == checkY && stock <= 3  && camp.noir == true) ) {
                   					_orient = 3; // Gauche
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le bas (y+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp.noir == true) || (camp._x == checkX && camp._y == checkY && stock <= 3  && camp.noir == true) ) {
                   					_orient = 2; // Bas
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le haut (y-)
        				for (int step = 1; step <= forwardRange; step++) {
           					for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp.noir == true) || (camp._x == checkX && camp._y == checkY && stock <= 3 && camp.noir == true) ) {
                    				_orient = 0; // Haut
									regle++;
                    				break campLoop;
                				}
            				}
        				}
   					}
				}
				



//aller vers un camps---------------------------------------------------------------------------------------------------------
				
				if (regle == 0) {
    				final int forwardRange = 13; // 5 cases vers l'avant
    				final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
    
    				campLoop:
    				for (Camps camp : _world.camps) {
        				if (!camp._alive) continue;
        
       				 // Rectangle vers la droite (x+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                					_orient = 1; // Droite
									regle++;
                   					break campLoop;
               					}
            				}
        				}
        
        				// Rectangle vers la gauche (x-)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                   					_orient = 3; // Gauche
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le bas (y+)
        				for (int step = 1; step <= forwardRange; step++) {
            				for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                   					_orient = 2; // Bas
									regle++;
                    				break campLoop;
                				}
            				}
        				}
        
        				// Rectangle vers le haut (y-)
        				for (int step = 1; step <= forwardRange; step++) {
           					for (int side = -sideRange; side <= sideRange; side++) {
                				int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
                				int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
                				if ((camp._x == checkX && camp._y == checkY && stock > 0 && camp.stock < 15) || (camp._x == checkX && camp._y == checkY && faim >= 17 && camp.stock > 0)) {
                    				_orient = 0; // Haut
									regle++;
                    				break campLoop;
                				}
            				}
        				}
   					}
				}

//migre si trop de predateur-----------------------------------------------------------------------------------------------------
				if (regle == 0) {
					final int forwardRange = 8; // 5 cases vers l'avant
					final int sideRange = 2;    // 1 case sur les côtés (pour former un rectangle)
					int max_droite = 0;
					int max_gauche = 0;
					int max_bas = 0;
					int max_haut = 0;
	
					
					for (PredatorAgent camp : _world.agents_pred) {
					
						if (!camp._predator) continue;
		
						// Rectangle vers la droite (x+)
						for (int step = 1; step <= forwardRange; step++) {
							for (int side = -sideRange; side <= sideRange; side++) {
								int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
								int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
								if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
									max_droite++;
									
								   }
							}
						}
		
						// Rectangle vers la gauche (x-)
						for (int step = 1; step <= forwardRange; step++) {
							for (int side = -sideRange; side <= sideRange; side++) {
								int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
								int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
								if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
									max_gauche++;
									
								   }
							}
						}
		
						// Rectangle vers le bas (y+)
						for (int step = 1; step <= forwardRange; step++) {
							for (int side = -sideRange; side <= sideRange; side++) {
								int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
								int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
								if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
									max_bas++;
									
								   }
							}
						}
		
						// Rectangle vers le haut (y-)
						for (int step = 1; step <= forwardRange; step++) {
							   for (int side = -sideRange; side <= sideRange; side++) {
								int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
								int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
								if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
									max_haut++;
									
								   }
						}
					   }
					}

					List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));

					// Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
					if (max_droite > 3) {
						// Predateur a droite
							direction.remove(Integer.valueOf(1));
							regle++;
							_orient = 3;
					

						} if (max_gauche > 3) {
						// Predateur a gauche
							direction.remove(Integer.valueOf(3));
							regle++;
							_orient = 1;
							

						} if (max_bas > 3) {
						// Predateur en bas
							direction.remove(Integer.valueOf(2));
							regle++;
							_orient = 0;
						

						} if (max_haut > 3) {
							// Predateur en haut
							direction.remove(Integer.valueOf(0));
							regle++;
							_orient = 2;
							

						}

						if(direction.size() != 4){
							if (!direction.isEmpty()) {
								_orient = direction.get((int) (Math.random() * direction.size()));
							}
							regle++;
							
						}

				}


				if(regle == 0){
					_orient = (int)(Math.random()*4);

					// met a jour: la position de l'agent (depend de l'orientation)
		 			
				}
			}else{
				if(tempsImmobile == 0){

					tempsImmobile = 32;
				}
			}
			}

		}
		}
	}
}
