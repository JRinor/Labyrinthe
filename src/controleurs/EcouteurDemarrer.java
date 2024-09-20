// src/controleurs/EcouteurDemarrer.java
package controleurs;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;
import algorithms.*;

import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class EcouteurDemarrer implements ActionListener {
    private Labyrinthe labyrinthe;
    private VueGrille vueGrille;
    private JComboBox<String> comboBox;

    public EcouteurDemarrer(Labyrinthe labyrinthe, VueGrille vueGrille, JComboBox<String> comboBox) {
        this.labyrinthe = labyrinthe;
        this.vueGrille = vueGrille;
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Demarrer");
        String selectedAlgo = (String) comboBox.getSelectedItem();
        Case start = labyrinthe.getDepart();
        Case goal = labyrinthe.getArrivee();
        Map<String, List<Case>> result = null;

        if (start == null || goal == null) {
            System.out.println("Le point de départ ou d'arrivée n'est pas défini.");
            return;
        }

        System.out.println("Départ: (" + start.getX() + ", " + start.getY() + ")");
        System.out.println("Arrivée: (" + goal.getX() + ", " + goal.getY() + ")");

        vueGrille.resetPathColors(); // Reset path colors before running the new algorithm

        switch (selectedAlgo) {
            case "AStar":
                result = new AStar().search(labyrinthe, start, goal);
                break;
            case "BreadthFirstSearch":
                result = new BreadthFirstSearch().search(labyrinthe, start, goal);
                break;
            case "DepthFirstSearch":
                result = new DepthFirstSearch().search(labyrinthe, start, goal);
                break;
            case "Dijkstra":
                result = new Dijkstra().search(labyrinthe, start, goal);
                break;
            case "GreedyBestFirstSearch":
                result = new GreedyBestFirstSearch().search(labyrinthe, start, goal);
                break;
            case "IDAStar":
                result = new IDAStar().search(labyrinthe, start, goal);
                break;
        }

        if (result != null) {
            List<Case> allVisited = result.get("allVisited");
            List<Case> shortestPath = result.get("shortestPath");

            for (Case c : allVisited) {
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
            }

            for (Case c : shortestPath) {
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.CYAN);
            }
        } else {
            System.out.println("No path found");
        }
    }
}