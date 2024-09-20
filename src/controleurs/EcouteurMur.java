package controleurs;

import models.Labyrinthe;
import models.Case;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurMur implements ActionListener {
    private Labyrinthe labyrinthe;

    public EcouteurMur(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Mur");
        labyrinthe.setCaseStatut(0, 0, Case.Statut.MUR);
    }
}