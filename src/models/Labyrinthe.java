package models;

import java.util.Observable;

public class Labyrinthe extends Observable {
    private Case[][] grille;
    private Case depart;
    private Case arrivee;

    public Labyrinthe(int largeur, int hauteur) {
        grille = new Case[largeur][hauteur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                grille[i][j] = new Case(i, j, Case.Statut.VIDE);
            }
        }
    }

    public int getLargeur() {
        return grille.length;
    }

    public int getHauteur() {
        return grille[0].length;
    }

    public Case getCase(int x, int y) {
        return grille[x][y];
    }

    public Case getDepart() {
        return depart;
    }

    public Case getArrivee() {
        return arrivee;
    }

    public void setCaseStatut(int x, int y, Case.Statut statut) {
        if (statut == Case.Statut.DEPART) {
            if (depart != null) {
                depart.setStatut(Case.Statut.VIDE);
                setChanged();
                notifyObservers(depart);
            }
            depart = grille[x][y];
        } else if (statut == Case.Statut.ARRIVEE) {
            if (arrivee != null) {
                arrivee.setStatut(Case.Statut.VIDE);
                setChanged();
                notifyObservers(arrivee);
            }
            arrivee = grille[x][y];
        }

        grille[x][y].setStatut(statut);
        setChanged();
        notifyObservers(grille[x][y]);
    }
}
