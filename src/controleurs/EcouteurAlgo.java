// src/controleurs/EcouteurAlgo.java
package controleurs;

import algorithms.*;
import models.Case;
import models.Labyrinthe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class EcouteurAlgo implements ActionListener {
    private Labyrinthe labyrinthe;
    private Case depart;
    private Case arrivee;
    private String algo;

    public EcouteurAlgo(Labyrinthe labyrinthe, Case depart, Case arrivee, String algo) {
        this.labyrinthe = labyrinthe;
        this.depart = depart;
        this.arrivee = arrivee;
        this.algo = algo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Algo");
        Map<String, List<Case>> result = null;
        switch (algo) {
            case "AStar":
                result = new AStar().search(labyrinthe, depart, arrivee);
                break;
            case "BreadthFirstSearch":
                result = new BreadthFirstSearch().search(labyrinthe, depart, arrivee);
                break;
            case "DepthFirstSearch":
                result = new DepthFirstSearch().search(labyrinthe, depart, arrivee);
                break;
            case "Dijkstra":
                result = new Dijkstra().search(labyrinthe, depart, arrivee);
                break;
            case "GreedyBestFirstSearch":
                result = new GreedyBestFirstSearch().search(labyrinthe, depart, arrivee);
                break;
            case "IDAStar":
                result = new IDAStar().search(labyrinthe, depart, arrivee);
                break;
        }

        if (result != null) {
            List<Case> shortestPath = result.get("shortestPath");
            // Process the shortestPath as needed
        } else {
            System.out.println("No path found");
        }
    }
}