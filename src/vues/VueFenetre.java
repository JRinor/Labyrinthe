package vues;

import models.Labyrinthe;

import javax.swing.*;
import java.awt.*;

public class VueFenetre {
    private static final int LABYRINTH_WIDTH = 10;
    private static final int LABYRINTH_HEIGHT = 10;
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;

    private JFrame frame;
    private Labyrinthe labyrinthe;

    public VueFenetre() {
        SwingUtilities.invokeLater(() -> {
            labyrinthe = new Labyrinthe(LABYRINTH_WIDTH, LABYRINTH_HEIGHT);

            frame = new JFrame("Labyrinthe");
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // VueGrille au centre
            VueGrille vueGrille = new VueGrille(LABYRINTH_WIDTH, LABYRINTH_HEIGHT, labyrinthe);
            labyrinthe.addObserver(vueGrille);
            frame.add(vueGrille, BorderLayout.CENTER);

            // VueBouttons en haut
            VueBouttons vueBouttons = new VueBouttons(labyrinthe, vueGrille);
            labyrinthe.addObserver(vueBouttons);
            frame.add(vueBouttons, BorderLayout.NORTH);

            // VueAffichage en bas
            VueAffichage vueAffichage = new VueAffichage();
            labyrinthe.addObserver(vueAffichage);
            frame.add(vueAffichage, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}