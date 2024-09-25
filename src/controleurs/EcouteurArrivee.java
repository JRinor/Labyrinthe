package controleurs;

import models.Labyrinthe;
import models.Case;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EcouteurArrivee implements ActionListener {
    private final Labyrinthe labyrinthe;
    private int x;
    private int y;

    public EcouteurArrivee(Labyrinthe labyrinthe) {
        if (labyrinthe == null) {
            throw new IllegalArgumentException("Labyrinthe cannot be null");
        }
        this.labyrinthe = labyrinthe;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Arrivee");
        labyrinthe.setCaseStatut(x, y, Case.Statut.ARRIVEE);
    }
}