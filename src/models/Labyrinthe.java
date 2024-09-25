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
        Case targetCase = grille[x][y];
        if (statut == Case.Statut.DEPART) {
            updateSpecialCase(depart, targetCase, statut);
            depart = targetCase;
        } else if (statut == Case.Statut.ARRIVEE) {
            updateSpecialCase(arrivee, targetCase, statut);
            arrivee = targetCase;
        } else {
            targetCase.setStatut(statut);
            setChanged();
            notifyObservers(targetCase);
        }
    }

    private void updateSpecialCase(Case oldCase, Case newCase, Case.Statut newStatut) {
        if (oldCase != null) {
            oldCase.setStatut(Case.Statut.VIDE);
            setChanged();
            notifyObservers(oldCase);
        }
        newCase.setStatut(newStatut);
        setChanged();
        notifyObservers(newCase);
    }
}