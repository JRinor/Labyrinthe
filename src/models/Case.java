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
    private int cost;

    public Case(int x, int y, Statut statut) {
        this.x = x;
        this.y = y;
        this.statut = statut;
        this.cost = 0;
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

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

    public List<Case> getNeighbors(Labyrinthe labyrinthe) {
        List<Case> neighbors = new ArrayList<>();
        // Droite
        if (x + 1 < labyrinthe.getLargeur()) neighbors.add(labyrinthe.getCase(x + 1, y));
        // Gauche
        if (x - 1 >= 0) neighbors.add(labyrinthe.getCase(x - 1, y));
        // Bas
        if (y + 1 < labyrinthe.getHauteur()) neighbors.add(labyrinthe.getCase(x, y + 1));
        // Haut
        if (y - 1 >= 0) neighbors.add(labyrinthe.getCase(x, y - 1));
        return neighbors.stream()
                .filter(c -> c.getStatut() != Statut.MUR)
                .collect(java.util.stream.Collectors.toList());
    }
}
