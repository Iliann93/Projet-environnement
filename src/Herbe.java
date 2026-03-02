import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;


public class Herbe extends Agent {
    boolean _alive;

    // Cache statique pour toutes les textures
    private static final Map<String, Image> TEXTURES = new HashMap<>();
    
    static {
        try {
            // Textures normales
            TEXTURES.put("herbe", ImageIO.read(new File("./ressources/sprite_herbe/herbe.png")));
            TEXTURES.put("herbe1", ImageIO.read(new File("./ressources/sprite_herbe/herbe1.png")));
            TEXTURES.put("herbe2", ImageIO.read(new File("./ressources/sprite_herbe/herbe2.png")));
            TEXTURES.put("herbe3", ImageIO.read(new File("./ressources/sprite_herbe/herbe_seche.png")));

            // Textures nuit
            TEXTURES.put("herbe_night", ImageIO.read(new File("./ressources/sprite_herbe/herbe_night.png")));
            TEXTURES.put("herbe1_night", ImageIO.read(new File("./ressources/sprite_herbe/herbe1_night.png")));
            TEXTURES.put("herbe2_night", ImageIO.read(new File("./ressources/sprite_herbe/herbe2_night.png")));
            TEXTURES.put("herbe3_night", ImageIO.read(new File("./ressources/sprite_herbe/herbe_seche_night.png")));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // Références aux textures partagées
    Image herbe, herbe1, herbe2, herbe3;
    Image herbe_night, herbe1_night, herbe2_night, herbe3_night;
    Image real_herbe;
    Image[] tab_herbe;

    public Herbe(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _alive = true;

        // Assignation des textures
        herbe = TEXTURES.get("herbe");
        herbe1 = TEXTURES.get("herbe1");
        herbe2 = TEXTURES.get("herbe2");
        herbe3 = TEXTURES.get("herbe3");

        herbe_night = TEXTURES.get("herbe_night");
        herbe1_night = TEXTURES.get("herbe1_night");
        herbe2_night = TEXTURES.get("herbe2_night");
        herbe3_night = TEXTURES.get("herbe3_night");

        // Initialisation du tableau et sélection aléatoire
        tab_herbe = new Image[]{herbe, herbe1, herbe2, herbe3};
        real_herbe = tab_herbe[(int)(Math.random() * 3)]; // Note: herbe3 (sèche) n'est pas incluse ici
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void step(){
        if(_alive){










            
        }

    }
}