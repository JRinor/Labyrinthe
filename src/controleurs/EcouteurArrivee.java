package controleurs;

import models.Labyrinthe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurArrivee implements ActionListener {
    private Labyrinthe labyrinthe;

    public EcouteurArrivee(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Arrivee");
        labyrinthe.setCaseStatut(0, 0, models.Case.Statut.ARRIVEE);
    }
}