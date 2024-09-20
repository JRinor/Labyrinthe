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

public class EcouteurAlgo implements ActionListener {
    private Labyrinthe labyrinthe;
    private VueGrille vueGrille;
    private JComboBox<String> comboBox;

    public EcouteurAlgo(Labyrinthe labyrinthe, VueGrille vueGrille, JComboBox<String> comboBox) {
        this.labyrinthe = labyrinthe;
        this.vueGrille = vueGrille;
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedAlgo = (String) comboBox.getSelectedItem();
        Case start = labyrinthe.getDepart();
        Case goal = labyrinthe.getArrivee();
        Map<String, List<Case>> result = null;

        if (start == null || goal == null) {
            System.out.println("Le point de départ ou d'arrivée n'est pas défini.");
            return;
        }

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
            List<Case> allVisited = result.get("allVisited");
            List<Case> shortestPath = result.get("shortestPath");

            if (shortestPath != null) {
                for (Case c : allVisited) {
                    if (!shortestPath.contains(c)) {
                        vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
                    }
                }
                for (Case c : shortestPath) {
                    vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.CYAN);
                }
                System.out.println("Chemin trouvé de longueur : " + shortestPath.size());
            } else {
                System.out.println("Aucun chemin trouvé");
            }
        } else {
            System.out.println("Erreur lors de l'exécution de l'algorithme");
        }
    }
}
