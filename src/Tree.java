import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Tree extends Agent {
    boolean _alive;
    int regle = 0;
    int thunder = 0;
    boolean infire = false;
    int fire = 0;

    // Textures partagées (static final)
    private static final Map<String, Image> TEXTURES = new HashMap<>();


    static {
        try {
            // Chargement des textures une seule fois
            TEXTURES.put("tree", ImageIO.read(new File("./tree_sprite/tree4.png")));
            TEXTURES.put("tree1", ImageIO.read(new File("./tree_sprite/tree5.png")));
            TEXTURES.put("tree2", ImageIO.read(new File("./tree_sprite/tree6.png")));
            TEXTURES.put("tree3", ImageIO.read(new File("./tree_sprite/tree7.png")));
            TEXTURES.put("tree4", ImageIO.read(new File("./tree_sprite/tree8.png")));
            TEXTURES.put("tree_night", ImageIO.read(new File("./tree_sprite/tree10.png")));
            
            TEXTURES.put("thunder1", ImageIO.read(new File("./sprite_thunder/map2.png")));
            TEXTURES.put("thunder2", ImageIO.read(new File("./sprite_thunder/map3.png")));
            TEXTURES.put("thunder3", ImageIO.read(new File("./sprite_thunder/map4.png")));
            TEXTURES.put("thunder4", ImageIO.read(new File("./sprite_thunder/map5.png")));

        

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // Références aux textures partagées
    Image tree, tree1, tree2, tree3, tree4, tree_night;
    Image thunder1, thunder2, thunder3, thunder4;
    Image[] tab, burn;

    public Tree(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _alive = true;
        infire = false;

        // Assignation des textures
        tree = TEXTURES.get("tree");
        tree1 = TEXTURES.get("tree1");
        tree2 = TEXTURES.get("tree2");
        tree3 = TEXTURES.get("tree3");
        tree4 = TEXTURES.get("tree4");
        tree_night = TEXTURES.get("tree_night");

        thunder1 = TEXTURES.get("thunder1");
        thunder2 = TEXTURES.get("thunder2");
        thunder3 = TEXTURES.get("thunder3");
        thunder4 = TEXTURES.get("thunder4");

        // Initialisation des tableaux d'animation
        burn = new Image[]{
            tree1, tree1, tree2, tree2, tree3, tree3, 
            tree4, tree4, tree4, tree3, tree3, tree2, tree2, tree1, tree1
        };
        tab = new Image[]{thunder1, thunder2, thunder3, thunder4};
    }

    // ... (méthodes draw() et autres restent inchangées)

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void step() {
        if (_alive) {

//collision arbre en feu--------------------------------------------------------------------------------------------------------
            regle = 0;
            if(infire == false && _world.isRaining == false){
                
                        for (int i = 0; i < _world.tree.size(); i++) {
            				Tree predateur = _world.tree.get(i);

           	 				// Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
            				if (predateur._x == (_x + 32 + _world.getWidth())  % _world.getWidth() && predateur._y == _y && predateur.infire && predateur.fire < 350) {
                			// Predateur a droite
                				infire = true;
                                _world.world_fire = true;
                                fire = 400;
                                regle++;
                				break;
            				}else if (predateur._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && predateur._y == _y && predateur.infire && predateur.fire < 350) {
                			// Predateur a gauche
                				infire = true;
                                _world.world_fire = true;
                                fire = 400;
                                regle++;
                				break;
            				} else if (predateur._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur.infire && predateur.fire < 350) {
                			// Predateur en bas
                				infire = true;
                                _world.world_fire = true;
                                fire = 400;
                                regle++;
                				break;
            					} else if (predateur._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && predateur._x == _x && predateur.infire && predateur.fire < 350) {
               	 			// Predateur en haut
               	 				infire = true;
                                _world.world_fire = true;
                                fire = 400;
                                regle++;
                				break;
            				}
						}
                    

//collision prois en feu---------------------------------------------------------------------------------------------------------
                        if(regle == 0){
                            for (int i = 0; i < _world.agents_prey.size(); i++) {
                                PreyAgent proi = _world.agents_prey.get(i);
            
                                // Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
                                if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi.infire && infire == false)  {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                }
                            }
                        } 
                        

//collision predateure en feu-----------------------------------------------------------------------------------------------
                        if(regle == 0){
                            for (int i = 0; i < _world.agents_pred.size(); i++) {
                                PredatorAgent proi = _world.agents_pred.get(i);
            
                                // Verifie la position du predateur par rapport a la proie (haut, bas, gauche, droite)
                                if (proi._x == (_x + 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi.infire && infire == false)  {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._x == (_x - 32 + _world.getWidth()) % _world.getWidth() && proi._y == _y && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._y == (_y + 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                } else if (proi._y == (_y - 32 + _world.getHeight()) % _world.getHeight() && proi._x == _x && proi.infire && infire == false) {
            
                                    infire = true;
                                    _world.world_fire = true;
                                    fire = 400;
                                    regle++;
                                    break;
            
                                }
                            }
                        }                  
            }

            
/* 

if(Math.random() < 0.0004 && infire == false){
    infire = true;
    fire = 500;
}*/
//foudre qui frappe--------------------------------------------------------------------------------------------
            if(thunder > 0 || fire > 0){
                if(thunder == 7){
                    
                    infire = true;
                }
                if(infire == true && _world.isRaining == true){
                    if(fire < 350){
                        infire = false;
                        fire = 0;
                        _world.world_fire = false;
                    }
                }
                else if(infire == true && fire == 1 && _world.isRaining == false){
                    _alive = false;
                    _world.nb_tree--;
                    int mtn = 0;
                    for(int i = 0; i < _world.tree.size(); i++){
                        if(_world.tree.get(i).infire){
                            mtn++;
                        }
                    }
                    if(mtn < 1){
                        _world.world_fire = false;
                    }
                }
                thunder--;
                fire--;


//foudre qui frappe-----------------------------------------------------------------------------------------------
            }else{
                if(Math.random() < 0.001 && thunder == 0 && _world.world_fire == false && _world.nb_tree > 170){
                    thunder = 10;
                    fire = 400;
                    _world.world_fire = true;
                    
                }
            }
            
        }
        else{
            _world.case_tree[_x/32][_y/32] = null;
            _world.case_tree_after[_x / 32][_y / 32] = 0;
            infire = false;
            int mtn = 0;
            for(int i = 0; i < _world.tree.size(); i++){
                if(_world.tree.get(i).infire){
                    mtn++;
                }
            }
            if(mtn < 1){
                _world.world_fire = false;
            }
            
        }
    }
}