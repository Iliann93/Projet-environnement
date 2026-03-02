import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Meteor extends Agent {
    // Cache statique pour toutes les textures
    private static final Map<String, Image> TEXTURES = new HashMap<>();
    private static final int REPETITIONS = 4;
    
    static {
        try {
            // Textures de base
            TEXTURES.put("cratere", ImageIO.read(new File("./sprite_meteore/cratere_meteor.png")));
            TEXTURES.put("cratere_night", ImageIO.read(new File("./sprite_meteore/cratere_meteor_night.png")));

            TEXTURES.put("meteorite", ImageIO.read(new File("./sprite_meteore/meteor_fall.png")));

            //fissure
            TEXTURES.put("fissure1", ImageIO.read(new File("./sprite_meteore/fissure1.png")));
            TEXTURES.put("fissure2", ImageIO.read(new File("./sprite_meteore/fissure2.png")));

            
            // Feu
            TEXTURES.put("feu", ImageIO.read(new File("./ezgif-split/fire/feu.png")));
            TEXTURES.put("feu1", ImageIO.read(new File("./ezgif-split/fire/feu1.png")));
            TEXTURES.put("feu2", ImageIO.read(new File("./ezgif-split/fire/feu2.png")));
            TEXTURES.put("feu3", ImageIO.read(new File("./ezgif-split/fire/feu2.png")));

            // Préchargement des explosions
            loadExplosionFrames("explosion-e", 22);  // 22 images × 4 = 88 frames
            loadExplosionFrames("explosion-b", 12);  // 12 images × 4 = 48 frames
            loadExplosionFrames("explosion-d", 12);  // 18 images × 6 = 72 frames

        } catch (Exception e) {
            e.printStackTrace();
            createFallbackTextures();
        }
    }

    // Méthode helper pour charger les frames d'explosion
    private static void loadExplosionFrames(String prefix, int frameCount) {
        for (int i = 1; i <= frameCount; i++) {
            String path = String.format("./ezgif-split/%s/Sprites/%s%d.png", 
                prefix.contains("-") ? prefix : prefix + "-1", 
                prefix.replace("-1", ""), 
                i);
            try {
                TEXTURES.put(prefix + i, ImageIO.read(new File(path)));
            } catch (Exception e) {
                System.err.println("Erreur de chargement: " + path);
                TEXTURES.put(prefix + i, createFallbackImage(32, 32));
            }
        }
    }

    // Création d'images de secours
    private static void createFallbackTextures() {
        TEXTURES.putIfAbsent("cratere", createFallbackImage(32, 32));
        TEXTURES.putIfAbsent("cratere_night", createFallbackImage(32, 32));

        TEXTURES.putIfAbsent("meteorite", createFallbackImage(32, 32));

        TEXTURES.putIfAbsent("fissure1", createFallbackImage(600, 300));
        TEXTURES.putIfAbsent("fissure2", createFallbackImage(600, 300));
        // ... (autres textures)
    }

    private static Image createFallbackImage(int w, int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    // Variables d'instance
    boolean _alive;
    int endroit = 0;
    boolean atterit = false;
    int temps_atteri = 0;
    boolean deja_fait = false;
    int indice = 0;
    int indice_explos = 0;
    int indice_explos1 = 0;
    int indice_explos2 = 0;
    int fire_count = 0;

    Image cratere, meteorite, fissure1, fissure2, cratere_nigth;
    Image feu, feu1, feu2, feu3;
    Image[] tab_feu;
    Image[] explos = new Image[88];
    Image[] explos1 = new Image[48];
    Image[] explos2 = new Image[72];

    public Meteor(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _alive = true;

        // Assignation des textures
        cratere = TEXTURES.get("cratere");
        cratere_nigth = TEXTURES.get("cratere_night");

        meteorite = TEXTURES.get("meteorite");
        fissure1 = TEXTURES.get("fissure1");
        fissure2 = TEXTURES.get("fissure2");
        feu = TEXTURES.get("feu");
        feu1 = TEXTURES.get("feu1");
        feu2 = TEXTURES.get("feu2");
        feu3 = TEXTURES.get("feu3");

        // Initialisation des animations
        initAnimation("explosion-e", explos, 22, REPETITIONS);
        initAnimation("explosion-b", explos1, 12, REPETITIONS);
        initAnimation("explosion-d", explos2, 18, 6); // Note: 6 répétitions pour explos2

        tab_feu = new Image[]{feu, feu, feu, feu, feu, feu, 
                             feu1, feu1, feu1, feu1, feu1, feu1,
                             feu2, feu2, feu2, feu2, feu2, feu2,
                             feu3, feu3, feu3, feu3, feu3, feu3};
    }

    private void initAnimation(String prefix, Image[] array, int frameCount, int repetitions) {
        for (int i = 0; i < array.length; i++) {
            int imageIndex = (i / repetitions) + 1;
            array[i] = TEXTURES.get(prefix + imageIndex);
        }
    }



    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void step(){
        if(_alive){


//initialiser ou tombe la meteorite-----------------------------
            if(deja_fait == false){

                endroit = (int)(Math.random() * 4);
                deja_fait = true;

                if(endroit == 0){

                    _x = 1312;
                    _y = 0;
    
                }else if( endroit == 1){

                    _x = 1052;
                    _y = 0;

                }else if( endroit == 2){

                    _x = 1920;
                    _y = 64;

                }else if( endroit == 3){

                    _x = 1920;
                    _y = 0;
                }
            }
//faire descendre la meteorite----------------------------------

            if(endroit == 0 && atterit == false){

                _x-=64;
                _y+=64;

                if(_x <= 960 && _y >= 384){

                    atterit = true;
                    _world.atterre = true;

                }


            }

            if(endroit == 1 && atterit == false){

                _x-=64;
                _y+=64;

                if(_x <= 640 && _y >= 512){

                    atterit = true;
                    _world.atterre = true;

                }


            }

            if(endroit == 2 && atterit == false){

                _x-=64;
                _y+=64;

                if(_x <= 1312 && _y >= 608){

                    atterit = true;
                    _world.atterre = true;

                }


            }
            if(endroit == 3 && atterit == false){

                _x-=64;
                _y+=64;

                if(_x <= 896 && _y >= 1024){

                    atterit = true;
                    _world.atterre = true;

                }


            }

//detruction zone----------------------------------------------------------------------------------------------------
            if(atterit == true && temps_atteri == 34){

                _world.atteriex = _x / 32;
                _world.atteriey = _y / 32;

                _world.isRaining = false;
				_world.duree_pluis = 300;
				_world.pluis_transition = 0.0f;

                for(PredatorAgent p : _world.agents_pred){

                    if(p._x < (_x + 516) && p._x > (_x - 324) && p._y < (_y + 516) && p._y > (_y - 420)){
                        p._predator = false;
                        _world.nb_pred_vivantes--;
                        p._x = -1;
                        p._y = -1;
                    }
                    if(p._x < (_x + 816) && p._x > (_x - 624) && p._y < (_y + 816) && p._y > (_y - 720)){
                        p.fire_count = 500;
                        p.infire = true;
                        
                    }

                }

                for(PreyAgent p : _world.agents_prey){

                    if(p._x < (_x + 516) && p._x > (_x - 324) && p._y < (_y + 516) && p._y > (_y - 420)){
                        p._alive= false;
                        _world.nb_proies_vivantes--;
                        p._x = -1;
                        p._y = -1;
                    }
                    if(p._x < (_x + 816) && p._x > (_x - 624) && p._y < (_y + 816) && p._y > (_y - 720)){
                        p.fire_count = 500;
                        p.infire = true;
                        
                    }

                }

                for(Tree t : _world.tree){

                    if(t._x < (_x + 516) && t._x > (_x - 324) && t._y < (_y + 516) && t._y > (_y - 420)){
                        t._alive = false;
                        _world.nb_tree--;
                        t._x = -1;
                        t._y = -1;
                    }
                    if(t._x < (_x + 816) && t._x > (_x - 624) && t._y < (_y + 816) && t._y > (_y - 720)){
                        t.infire = true;
                        t._world.world_fire = true;
                        t.fire = 400;
                    }

                }

                for(Camps t : _world.camps){

                    if(t._x < (_x + 516) && t._x > (_x - 324) && t._y < (_y + 516) && t._y > (_y - 420)){
                        t._alive = false;
                        t._x = -1;
                        t._y = -1;
                        _world.camps_totale--;
                    }

                }
                for(Herbe t : _world.herbe){

                    if(t._x < (_x + 516) && t._x > (_x - 324) && t._y < (_y + 516) && t._y > (_y - 420)){
                        t._alive = false;
                        t._x = -1;
                        t._y = -1;
                    }

                }

                

                temps_atteri++;

            }else if(atterit == true && temps_atteri < 50){
                temps_atteri++;
            }if( atterit == true && temps_atteri == 34){

                for(int i = ((_x/32) - 7); i < ((_x/32) + 13); i++){
                    for(int j = ((_y/32) - 10); j < ((_y/32) + 13) ; j++){

                        if(_world.case_world_pred[(i+60) % 60][(j+40) % 40] == 7 || _world.case_world_pred[i % 60][j % 40] == 8 || _world.case_world_pred[i % 60][j % 40] == 0 && i < 60 && j < 40){

                            if(Math.random() <= 0.13){
                                _world.case_world_pred[i % 60][j % 40] = 20;

                            }else{

                                _world.case_world_pred[i % 60][j % 40] = 21;
                            }
                        }
                    }
                }}
        }
    }
}