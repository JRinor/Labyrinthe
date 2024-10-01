package controleurs;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;
import vues.VueAffichage;
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
    private VueAffichage vueAffichage;
    private JComboBox<String> comboBox;

    public EcouteurDemarrer(Labyrinthe labyrinthe, VueGrille vueGrille, VueAffichage vueAffichage, JComboBox<String> comboBox) {
        this.labyrinthe = labyrinthe;
        this.vueGrille = vueGrille;
        this.vueAffichage = vueAffichage;
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Demarrer");
        String selectedAlgo = (String) comboBox.getSelectedItem();
        Case start = labyrinthe.getDepart();
        Case goal = labyrinthe.getArrivee();
        Map<String, Object> result = null;

        if (start == null || goal == null) {
            System.out.println("Le point de départ ou d'arrivée n'est pas défini.");
            return;
        }

        System.out.println("Départ: (" + start.getX() + ", " + start.getY() + ")");
        System.out.println("Arrivée: (" + goal.getX() + ", " + goal.getY() + ")");

        vueGrille.resetPathColors();

        switch (selectedAlgo) {
            case "AStar":
                result = new AStar(vueGrille).search(labyrinthe, start, goal);
                break;
            case "BreadthFirstSearch":
                result = new BreadthFirstSearch(vueGrille).search(labyrinthe, start, goal);
                break;
            case "DepthFirstSearch":
                result = new DepthFirstSearch(vueGrille).search(labyrinthe, start, goal);
                break;
            case "Dijkstra":
                result = new Dijkstra(vueGrille).search(labyrinthe, start, goal);
                break;
            case "GreedyBestFirstSearch":
                result = new GreedyBestFirstSearch(vueGrille).search(labyrinthe, start, goal);
                break;
            case "IDAStar":
                result = new IDAStar(vueGrille).search(labyrinthe, start, goal);
                break;
        }

        if (result != null) {
            List<Case> allVisited = (List<Case>) result.get("allVisited");
            List<Case> shortestPath = (List<Case>) result.get("shortestPath");
            AlgorithmStats stats = (AlgorithmStats) result.get("stats");

            if (shortestPath != null) {
                updateUI(allVisited, shortestPath);
                System.out.println("Chemin trouvé de longueur : " + shortestPath.size());
            } else {
                System.out.println("Aucun chemin trouvé");
            }

            if (stats != null) {
                vueAffichage.updateStats(stats);
                System.out.println(stats.toString());
            }
        } else {
            System.out.println("Erreur lors de l'exécution de l'algorithme");
        }
    }

    private void updateUI(List<Case> allVisited, List<Case> shortestPath) {
        for (Case c : allVisited) {
            if (!shortestPath.contains(c)) {
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
            }
        }
        for (Case c : shortestPath) {
            vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.CYAN);
        }
    }
}
