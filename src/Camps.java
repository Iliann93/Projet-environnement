import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Camps extends Agent{

    boolean _alive;

    Image bois;
    Image resizedTree; 
    Image feu;
    Image feu1;
    Image feu2;
    Image feu3;
    int fire_count = 0;

    int stock = 0;

    




    Image real_herbe;
    Image[] tab_feu;

    public Camps(int __x, int __y, World __w) {
        super(__x, __y, __w);
        _alive = true;

        try{

            bois = ImageIO.read(new File("./ressources/ezgif-split/bois.png"));
            feu = ImageIO.read(new File("./ressources/ezgif-split/fire/feu.png"));
            feu1 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu1.png"));
            feu2 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu2.png"));
            feu3 = ImageIO.read(new File("./ressources/ezgif-split/fire/feu2.png"));

            resizedTree = bois.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            resizedTree = feu.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            resizedTree = feu1.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            resizedTree = feu2.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            resizedTree = feu3.getScaledInstance(32, 32, Image.SCALE_SMOOTH);




        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

        tab_feu= new Image[]{feu, feu, feu, feu, feu, feu, feu1, feu1, feu1, feu1, feu1, feu1, feu2, feu2, feu2, feu2, feu2, feu2, feu3, feu3, feu3, feu3, feu3, feu3};

        

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