import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class PreyAgent extends Agent {
    boolean _alive;
    boolean malade;
    boolean blanc = false;
    boolean noir = false;
    boolean gris = false;

    // Textures partagées (static final)
    private static final Map<String, Image> TEXTURES = new HashMap<>();
    
    static {
        try {
            // Chargement des textures pour toutes les variantes (noir/blanc/gris)
            // Noir
            TEXTURES.put("noir_haut", ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_dos.png")));
            TEXTURES.put("noir_bas", ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_face.png")));
            TEXTURES.put("noir_droite", ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_gauche.png")));
            TEXTURES.put("noir_gauche", ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_droite.png")));

            // Blanc
            TEXTURES.put("blanc_haut", ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_dos.png")));
            TEXTURES.put("blanc_bas", ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_face.png")));
            TEXTURES.put("blanc_droite", ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_gauche.png")));
            TEXTURES.put("blanc_gauche", ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_droite.png")));

            // Gris
            TEXTURES.put("gris_haut", ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_dos.png")));
            TEXTURES.put("gris_bas", ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_face.png")));
            TEXTURES.put("gris_droite", ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_gauche.png")));
            TEXTURES.put("gris_gauche", ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_droite.png")));

            // Night (commun à toutes les couleurs)
            TEXTURES.put("night_haut", ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_dos.png")));
            TEXTURES.put("night_bas", ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_face.png")));
            TEXTURES.put("night_droite", ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_gauche.png")));
            TEXTURES.put("night_gauche", ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_droite.png")));

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

    // Références aux textures (partagées)
    Image haut, bas, droite, gauche;
    Image haut_night, bas_night, droite_night, gauche_night;
    Image feu, feu1, feu2, feu3;
    Image[] tab_feu;
    boolean infire = false;

    double proba;
    int tempsImmobile = 0;
    int faim = 0;
    int xmove = 1;
    int ymove = 1;
    int fire_count = 0;

    public PreyAgent(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _alive = true;
        malade = false;
        proba = 0.84;

        // Choix aléatoire de la couleur
        int probs = (int)(Math.random() * 3);
        if (probs == 0) noir = true;
        else if (probs == 1) blanc = true;
        else gris = true;

        // Assignation des textures en fonction de la couleur
        if (noir) {
            haut = TEXTURES.get("noir_haut");
            bas = TEXTURES.get("noir_bas");
            droite = TEXTURES.get("noir_droite");
            gauche = TEXTURES.get("noir_gauche");
        } else if (blanc) {
            haut = TEXTURES.get("blanc_haut");
            bas = TEXTURES.get("blanc_bas");
            droite = TEXTURES.get("blanc_droite");
            gauche = TEXTURES.get("blanc_gauche");
        } else {
            haut = TEXTURES.get("gris_haut");
            bas = TEXTURES.get("gris_bas");
            droite = TEXTURES.get("gris_droite");
            gauche = TEXTURES.get("gris_gauche");
        }

        // Textures night (communes)
        haut_night = TEXTURES.get("night_haut");
        bas_night = TEXTURES.get("night_bas");
        droite_night = TEXTURES.get("night_droite");
        gauche_night = TEXTURES.get("night_gauche");

        // Feu
        feu = TEXTURES.get("feu");
        feu1 = TEXTURES.get("feu1");
        feu2 = TEXTURES.get("feu2");
        feu3 = TEXTURES.get("feu3");
        tab_feu = new Image[]{feu, feu, feu, feu, feu, feu, feu1, feu1, feu1, feu1, feu1, feu1, feu2, feu2, feu2, feu2, feu2, feu2, feu3, feu3, feu3, feu3, feu3, feu3};
    }



	public void change_color(int probs){
		noir = false;
		blanc = false;
		gris = false;


		if(probs == 0) noir = true;
		else if( probs == 1) blanc = true;
		else gris = true;
		
		
		try{	
			if(noir == true){
				haut = ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_dos.png"));
				bas = ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_face.png"));
				droite = ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_gauche.png"));
				gauche= ImageIO.read(new File("./sprite_gars/mouton_sprite_noir/mouton_droite.png"));
			}else if(blanc == true){
				haut = ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_dos.png"));
				bas = ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_face.png"));
				droite = ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_gauche.png"));
				gauche= ImageIO.read(new File("./sprite_gars/mouton_sprite/mouton_droite.png"));
			}else{
				haut = ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_dos.png"));
				bas = ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_face.png"));
				droite = ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_gauche.png"));
				gauche= ImageIO.read(new File("./sprite_gars/mouton_sprite_gris/mouton_droite.png"));
			}
		

				haut_night = ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_dos.png"));
				bas_night = ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_face.png"));
				droite_night = ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_gauche.png"));
				gauche_night = ImageIO.read(new File("./sprite_gars_night/mouton_sprite_night/mouton_droite.png"));

				feu = ImageIO.read(new File("./ezgif-split/fire/feu.png"));
            	feu1 = ImageIO.read(new File("./ezgif-split/fire/feu1.png"));
            	feu2 = ImageIO.read(new File("./ezgif-split/fire/feu2.png"));
            	feu3 = ImageIO.read(new File("./ezgif-split/fire/feu2.png"));
			
			}catch(Exception e){
				e.printStackTrace();
				System.exit(-1);
			}
	}

	public int getX() {
    	return _x;
	}

	public int getY() {
    	return _y;
	}

	public void move(){

		if(tempsImmobile == 0 && _alive){

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
		
		if(_alive){
			
			if(fire_count == 1 && infire == true){
				_alive = false;
				fire_count--;
				_world.nb_proies_vivantes--;
			}
			if(fire_count > 1){
				fire_count --;
			}
			if(_world.isRaining == true && infire == true){

				infire = false;
				fire_count = 0;

			}

			if(faim > 50){
				proba = 0.73;
			}
			else{
				proba = 0.98;
			}

			//si il fait nuit
			if(_world.night == true && faim <= 25){

				proba = 0.98;
			}else if(_world.night == false && faim <= 25){

				proba = 0.84;
			}

			//si immobile
			if (tempsImmobile > 0) {
                tempsImmobile--;  // Réduit le temps restant d'immobilité
            }

			//si deja en train de bouger
			if((_x % 32) != 0 || (_y % 32) != 0){

				
			}
			else{
				//System.out.println("x = "+_x+" y = "+_y);
				if(Math.random()< proba && tempsImmobile == 0){
					
				
					int regle = 0;

					List<Integer> direction1 = new ArrayList<>(List.of(0, 1, 2, 3));
				
					if (_world.case_world_pred[((_x + 32) % _world.getWidth()) / 32][_y / 32] == 3) {
    				// Collision à droite : déplacer à gauche
						direction1.remove(Integer.valueOf(1));
    					
    					
    		
					} if (_world.case_world_pred[_x / 32][((_y + 32) % _world.getHeight()) / 32] == 3) {
    				// Collision en bas : déplacer vers le haut
					direction1.remove(Integer.valueOf(2));
    				
    			
					} if (_world.case_world_pred[((_x - 32 + _world.getWidth()) % _world.getWidth()) / 32][_y / 32] == 3) {
    				// Collision à gauche : déplacer à droite
					direction1.remove(Integer.valueOf(3));
    				
    					
					} if (_world.case_world_pred[_x / 32][((_y - 32 + _world.getHeight()) % _world.getHeight()) / 32] == 3) {
    				// Collision en haut : déplacer vers le bas
					direction1.remove(Integer.valueOf(0));
    		
    			
					}

					if(direction1.size() != 4){
						if(direction1.size() != 0){
							_orient = direction1.get((int) (Math.random() * direction1.size()));
							regle++;
						}
						
					}

					//collision arbre-----------------------------------------------------------------
					if (regle == 0) {

    					List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));

   						 // Gérer la collision avec les arbres
    					for (int i = 0; i < _world.tree.size(); i++) {

        					Tree proi = _world.tree.get(i);

        						// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
        					if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive) {
            					// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes
								direction.remove(Integer.valueOf(1));
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
            				

        					} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive) {
            					// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
								direction.remove(Integer.valueOf(3));
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
            					

        					} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive)  {
            				// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
								direction.remove(Integer.valueOf(2));
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
						
            					
        					} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive) {
            					// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
								direction.remove(Integer.valueOf(0));
								if(proi.infire && infire == false){
									infire = true;
									fire_count = 350;
								}
							
        					}
    					}
						

						
				
						//collision camps----------------------------------------------------------------------
	
						for (int i = 0; i < _world.camps.size(); i++) {
							Camps proi = _world.camps.get(i);
	
								// Vérifier la position du prédateur par rapport à l'arbre (haut, bas, gauche, droite)
							if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y  && proi._alive == true) {
								// Arbre à droite : choisir une direction aléatoire parmi les 3 restantes
								
									direction.remove(Integer.valueOf(1));
	
								
								
							
	
							} else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive == true) {
								// Arbre à gauche : choisir une direction aléatoire parmi les 3 restantes
									direction.remove(Integer.valueOf(3));
	
								
								
	
							} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
							// Arbre en bas : choisir une direction aléatoire parmi les 3 restantes
									direction.remove(Integer.valueOf(2));
								
								
						
								
							} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive == true) {
								// Arbre en haut : choisir une direction aléatoire parmi les 3 restantes
	
									direction.remove(Integer.valueOf(0));
							
							
							}
						}
						if(direction.size() != 4){
							if (!direction.isEmpty()) {
								_orient = direction.get((int) (Math.random() * direction.size()));
							}
							regle++;
							
						}
					}


					//herbre---------------------------------------------------------------------------
					/* 
					if(regle == 0){

						for (int i = 0; i < _world.herbe.size(); i++) {

							Herbe proi = _world.herbe.get(i);
	
							// Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
							if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive && faim >= 20) {
								// Proi a droite
								 // Deplace la proie a gauche
								regle++;
								_orient = 1;
								break;

							}else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi._alive && faim >= 20) {
							// Predateur a gauche
								regle++;
								_orient = 3;
								break;

							} else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive && faim >= 20) {
							// Predateur en bas
								regle++;
								_orient = 2;
								break;

							} else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi._alive && faim >= 20) {
						// Predateur en haut
								regle++;
								_orient = 0;
								break;

							}
						}
					}*/
					if (regle == 0) {
						final int forwardRange = 4; // 5 cases vers l'avant
						final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
		
						campLoop:
						for (Herbe camp : _world.herbe) {
							if (!camp._alive) continue;
			
							// Rectangle vers la droite (x+)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp._alive)) {
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
									if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp._alive)) {
										_orient = 3; // Droite
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
									if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp._alive)) {
										_orient = 2; // Droite
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
									if ((camp._x == checkX && camp._y == checkY && faim >= 20 && camp._alive)) {
										_orient = 0; // Droite
										regle++;
										   break campLoop;
									   }
							}
						   }
						}
					}

					


					//fuite predateur--------------------------------------------------------------
					if(regle == 0){

						List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));

						/*for (int i = 0; i < _world.agents_pred.size(); i++) {

            				PredatorAgent predateur = _world.agents_pred.get(i);

           	 				// Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
            				if ((predateur._x == (_x + 32 + _world.getWidth())  % _world.getWidth() && predateur._y == _y && predateur._predator) || (predateur._x == (_x + 64 + _world.getWidth())  % _world.getWidth() && predateur._y == _y && predateur._predator)) {
                			// Predateur a droite
								direction.remove(Integer.valueOf(1));
								regle++;
								_orient = 3;
                				break;

            				}else if ((predateur._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && predateur._y == _y && predateur._predator) || (predateur._x == (_x - 64 + _world.getWidth()) % _world.getWidth() && predateur._y == _y && predateur._predator)) {
                			// Predateur a gauche
								direction.remove(Integer.valueOf(3));
								regle++;
								_orient = 1;
                				break;

            				} else if ((predateur._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur._predator) || (predateur._y == (_y + 64 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur._predator)) {
                			// Predateur en bas
								direction.remove(Integer.valueOf(2));
								regle++;
								_orient = 0;
                				break;

            					} else if ((predateur._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur._predator) || (predateur._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur._predator) ) {
               	 			// Predateur en haut
								direction.remove(Integer.valueOf(0));
								regle++;
								_orient = 2;
                				break;

            				}
						}*/
						final int forwardRange = 2; // 5 cases vers l'avant
						final int sideRange = 1;    // 1 case sur les côtés (pour former un rectangle)
				
		
						campLoop:
						for (PredatorAgent camp : _world.agents_pred) {
						
							if (!camp._predator) continue;
			
							// Rectangle vers la droite (x+)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
						
										direction.remove(Integer.valueOf(1));
										regle++;
										_orient = 3;
										break campLoop ;						
									   }
								}
							}
			
							// Rectangle vers la gauche (x-)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
										direction.remove(Integer.valueOf(3));
										regle++;
										_orient = 1;
										break campLoop;
										
									   }
								}
							}
			
							// Rectangle vers le bas (y+)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
										direction.remove(Integer.valueOf(2));
										regle++;
										_orient = 0;
										break campLoop;
										
									   }
								}
							}
			
							// Rectangle vers le haut (y-)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
									int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._predator)) {
										direction.remove(Integer.valueOf(0));
										regle++;
										_orient = 2;
                						break campLoop;
										
									}
								}
						   }
						}

						if(direction.size() != 4){

							_orient = direction.get((int) (Math.random() * direction.size()));
							regle++;
			
						}
					}

					//migre si trop de mouton------------------------------------------------------------------
					if (regle == 0) {
						final int forwardRange = 8; // 5 cases vers l'avant
						final int sideRange = 2;    // 1 case sur les côtés (pour former un rectangle)
						int max_droite = 0;
						int max_gauche = 0;
						int max_bas = 0;
						int max_haut = 0;
		
						
						for (PreyAgent camp : _world.agents_prey) {
						
							if (!camp._alive) continue;
			
							// Rectangle vers la droite (x+)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*step + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._alive)) {
										max_droite++;
										
									   }
								}
							}
			
							// Rectangle vers la gauche (x-)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x - 32*step + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*side + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._alive)) {
										max_gauche++;
										
									   }
								}
							}
			
							// Rectangle vers le bas (y+)
							for (int step = 1; step <= forwardRange; step++) {
								for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
									int checkY = (_y + 32*step + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._alive)) {
										max_bas++;
										
									   }
								}
							}
			
							// Rectangle vers le haut (y-)
							for (int step = 1; step <= forwardRange; step++) {
								   for (int side = -sideRange; side <= sideRange; side++) {
									int checkX = (_x + 32*side + _world.getWidth()) % _world.getWidth();
									int checkY = (_y - 32*step + _world.getHeight()) % _world.getHeight();
									if ((camp._x == checkX && camp._y == checkY && camp._alive)) {
										max_haut++;
										
									   }
							}
						   }
						}

						List<Integer> direction = new ArrayList<>(List.of(0, 1, 2, 3));

						// Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
						if (max_droite > 4) {
                			// Predateur a droite
								direction.remove(Integer.valueOf(1));
								regle++;
								_orient = 3;
                		

            				} if (max_gauche > 4) {
                			// Predateur a gauche
								direction.remove(Integer.valueOf(3));
								regle++;
								_orient = 1;
                				

            				} if (max_bas > 4) {
                			// Predateur en bas
								direction.remove(Integer.valueOf(2));
								regle++;
								_orient = 0;
                			

            				} if (max_haut > 4) {
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
					

					//mouvement aleatoire sinon--------------------------------------------------------
					if(regle == 0){

						_orient = (int)(Math.random()*4);
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



