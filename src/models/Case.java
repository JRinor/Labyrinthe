// src/models/Case.java
package models;

import java.util.ArrayList;
import java.util.List;

public class Case {
    public enum Statut {
        MUR, DEPART, ARRIVEE, VIDE
    }

    private int x;
    private int y;
    private Statut statut;

    public Case(int x, int y, Statut statut) {
        this.x = x;
        this.y = y;
        this.statut = statut;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public int getCost() {
        return 1; // Assuming uniform cost for simplicity
    }

    public List<Case> getNeighbors(Labyrinthe labyrinthe) {
        List<Case> neighbors = new ArrayList<>();
        if (x + 1 < labyrinthe.getLargeur() && labyrinthe.getCase(x + 1, y).getStatut() != Statut.MUR) neighbors.add(labyrinthe.getCase(x + 1, y));
        if (x - 1 >= 0 && labyrinthe.getCase(x - 1, y).getStatut() != Statut.MUR) neighbors.add(labyrinthe.getCase(x - 1, y));
        if (y + 1 < labyrinthe.getHauteur() && labyrinthe.getCase(x, y + 1).getStatut() != Statut.MUR) neighbors.add(labyrinthe.getCase(x, y + 1));
        if (y - 1 >= 0 && labyrinthe.getCase(x, y - 1).getStatut() != Statut.MUR) neighbors.add(labyrinthe.getCase(x, y - 1));
        return neighbors;
    }
}