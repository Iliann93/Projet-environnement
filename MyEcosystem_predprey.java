import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.BufferedImage; // Import manquant ajouté ici

public class MyEcosystem_predprey extends JPanel {

    private World world;
    public static JFrame frame;
    private int dx = 1920; // Largeur de la grille
    private int dy = 1280; // Hauteur de la grille
    private int spriteSize = 32; // Taille des sprites
    private Timer gameLoop; // Timer pour gérer l'animation
    private Image map; // Image de fond
    private Image map_night;
    private boolean spacePressed = false;

    private static class MapCache {
        private static Image map;
        private static Image map_night;
        private static boolean loaded = false;

        static {
            try {
                map = ImageIO.read(new File("./sprite_map/final_map1.png"));
                map_night = ImageIO.read(new File("./sprite_map/final_map1_night1.png"));
                loaded = true;
            } catch (Exception e) {
                e.printStackTrace();
                // Fallback : créer des maps vierges si chargement échoue
                map = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
                map_night = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            }
        }

        public static Image getMap(boolean nightMode, int screenWidth, int screenHeight) {
            Image original = nightMode ? map_night : map;
            return original.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        }
    }



    public MyEcosystem_predprey() {
        
        // Récupérer la résolution de l'écran
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        // Adapter la taille du JPanel à la résolution de l'écran
        setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Charger l'image de fond
        try {
            map = ImageIO.read(new File("./sprite_map/final_map1.png"));
            map_night = ImageIO.read(new File("./sprite_map/final_map1_night1.png"));
            if (map == null) {
                System.out.println("L'image n'a pas pu être chargée.");
            } else {
                System.out.println("Image chargée avec succès. Taille: " + map.getWidth(null) + "x" + map.getHeight(null));
                // Redimensionner l'image pour qu'elle remplisse l'écran tout en conservant les proportions
                map = map.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Si une erreur se produit, l'afficher
        }

        // Initialiser le monde avec la taille de la grille (dx, dy)
        world = new World(dx, dy, true, true);
        double densite = 0.02;
        double densite_h = 0.02;

        // Ajout des arbres 
        
        for (int x = 0; x < dx; x += spriteSize)
            for (int y = 0; y < dy; y += spriteSize)
                if (densite >= Math.random() && world.case_world_pred[x / spriteSize][y / spriteSize] == 7){
                    Tree arbre = new Tree(x, y, world);
                    world.add(arbre);
                    world.case_tree[x / spriteSize][y / spriteSize] = arbre;
                    world.case_tree_after[x / spriteSize][y / spriteSize] = 1;
                }

        for (int x = 0; x < dx; x += spriteSize)
            for (int y = 0; y < dy; y += spriteSize)
                if (0.3 >= Math.random() && world.case_world_pred[x / spriteSize][y / spriteSize] == 8){
                    Tree arbre = new Tree(x, y, world);
                    world.add(arbre);
                    world.case_tree[x / spriteSize][y / spriteSize] = arbre;
                    world.case_tree_after[x / spriteSize][y / spriteSize] = 1;
                }

        // Ajout de l'herbre
        for (int x = 0; x < dx; x += spriteSize)
            for (int y = 0; y < dy; y += spriteSize)
                if (densite_h >= Math.random() && world.case_world_pred[x / spriteSize][y / spriteSize] == 7){
                    Herbe herbe = new Herbe(x, y, world);
                    world.add(herbe);
                    //world.case_tree[x / spriteSize][y / spriteSize] = herbe;
                    world.case_tree_after[x / spriteSize][y / spriteSize] = 2;
                }
 // Ajout de l'herbre seche
        for (int x = 0; x < dx; x += spriteSize)
            for (int y = 0; y < dy; y += spriteSize)
                if (densite_h >= Math.random() && world.case_world_pred[x / spriteSize][y / spriteSize] == 0){
                    Herbe herbe = new Herbe(x, y, world);
                    herbe.real_herbe = herbe.tab_herbe[3];
                    world.add(herbe);
                    //world.case_tree[x / spriteSize][y / spriteSize] = herbe;
                    world.case_tree_after[x / spriteSize][y / spriteSize] = 2;
                }

        // Ajout des proies
        for (int i = 0; i < 40; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * (dx / spriteSize)) * spriteSize;
                y = (int) (Math.random() * (dy / spriteSize)) * spriteSize;
            } while (world.case_world_pred[x / spriteSize][y / spriteSize] != 0 && world.case_world_pred[x / spriteSize][y / spriteSize] != 7 && world.case_world_pred[x / spriteSize][y / spriteSize] != 2); // Répéter jusqu'à trouver une case libre
            world.add(new PreyAgent(x, y, world));
        }

        // Ajout des prédateurs
        for (int i = 0; i < 50; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * (dx / spriteSize)) * spriteSize;
                y = (int) (Math.random() * (dy / spriteSize)) * spriteSize;
            } while (world.case_world_pred[x / spriteSize][y / spriteSize] != 0 && world.case_world_pred[x / spriteSize][y / spriteSize] != 7 && world.case_world_pred[x / spriteSize][y / spriteSize] != 2); // Répéter jusqu'à trouver une case libre
            world.add(new PredatorAgent(x, y, world));
        }
         // Configuration des entrées clavier (intégrée directement dans le constructeur)
         setFocusable(true);
         requestFocusInWindow();
         addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                     spacePressed = true;
                 }
             }
             
             @Override
             public void keyReleased(KeyEvent e) {
                 if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                     spacePressed = false;
                 }
             }
         });
 
        // Démarrer le Timer pour l'animation
        gameLoop = new Timer(1000 / 100, e -> updateGame()); // 60 FPS
        gameLoop.start();
    }
  

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);/* 
        if (map != null) {
            // Dessiner l'image de fond redimensionnée
            g.drawImage(map, 0, 0, getWidth(), getHeight(), this);
        }*/Image currentMap = world.night ? map_night : map;
        g.drawImage(currentMap, 0, 0, getWidth(), getHeight(), this);

        // Calculer les marges pour centrer la grille
        int offsetX = (getWidth() - dx) / 2;
        int offsetY = (getHeight() - dy) / 2;

        // Dessiner le monde en tenant compte des marges
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(offsetX, offsetY); // Décaler le dessin pour centrer la grille
        world.display(g2d, spriteSize);
        g2d.dispose();
        requestFocusInWindow();
    }

    private int stepCounter = 0;

    private void updateGame() {
        /* 
        if (System.currentTimeMillis() % 100 == 0) { // Toutes les 10 secondes
            Runtime rt = Runtime.getRuntime();
            long usedMB = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
            long totalMB = rt.totalMemory() / (1024 * 1024);
            
            System.out.printf(
                "Mémoire: %d/%d %n",
                usedMB, totalMB
              
            );
            
            // Force un GC pour test (ne pas faire en production)
            System.gc();
        }*/
        if (spacePressed) {
            handleSpaceAction();
            spacePressed = false;
        }
        stepCounter++;
        
        if (stepCounter % 2 == 0) { // Change seulement toutes les 2 frames
            world.step();
        }
        repaint();
    }
    private void handleSpaceAction() {
        System.out.println("Espace pressé - Action exécutée");
        // Ajoutez ici votre logique personnalisée
        // Exemple : ajouter un nouvel agent
        int x = (int)(Math.random() * dx);
        int y = (int)(Math.random() * dy);
        world.add(new Meteor(x, y, world));
    }

    public static void main(String[] args) {
        frame = new JFrame("World of Sprite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyEcosystem_predprey panel = new MyEcosystem_predprey();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        frame.setUndecorated(true); // Supprime les bordures de la fenêtre
        frame.add(panel);
        frame.pack(); // Ajuste la taille de la fenêtre en fonction de la taille préférée du JPanel
        frame.setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        frame.setVisible(true);
    }
}