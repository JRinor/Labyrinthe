package vues;

import models.Labyrinthe;

import javax.swing.*;
import java.awt.*;

public class VueFenetre {
    private JFrame frame;
    private Labyrinthe labyrinthe;

    public VueFenetre() {
        labyrinthe = new Labyrinthe(10, 10);

        frame = new JFrame("Labyrinthe");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // VueGrille au centre
        VueGrille vueGrille = new VueGrille(10, 10, labyrinthe);
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
    }
}