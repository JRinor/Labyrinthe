package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.util.*;

public class IDAStar {
    private VueGrille vueGrille;
    private List<Case> bestPath;
    private List<Case> allVisited;

    public IDAStar(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        bestPath = null;
        allVisited = new ArrayList<>();
        int threshold = ManhattanHeuristic.calculate(start, goal);

        while (true) {
            int temp = search(start, 0, threshold, goal, labyrinthe, new HashSet<>());
            if (temp == -1) {
                Map<String, List<Case>> result = new HashMap<>();
                result.put("shortestPath", bestPath);
                result.put("allVisited", allVisited);
                System.out.println("Nombre de cases explorées : " + allVisited.size());
                return result;
            }
            if (temp == Integer.MAX_VALUE) {
                return null; // Aucun chemin trouvé
            }
            threshold = temp;
        }
    }

    private int search(Case current, int g, int threshold, Case goal, Labyrinthe labyrinthe, Set<Case> path) {
        path.add(current);
        updateUI(current);

        int f = g + ManhattanHeuristic.calculate(current, goal);
        if (f > threshold) {
            return f;
        }
        if (current.equals(goal)) {
            bestPath = new ArrayList<>(path);
            return -1;
        }

        int min = Integer.MAX_VALUE;
        List<Case> neighbors = getNeighbors(current, labyrinthe);
        for (Case neighbor : neighbors) {
            if (!path.contains(neighbor)) {
                int temp = search(neighbor, g + 1, threshold, goal, labyrinthe, path);
                if (temp == -1) {
                    return -1;
                }
                if (temp < min) {
                    min = temp;
                }
            }
        }

        path.remove(current);
        return min;
    }

    private List<Case> getNeighbors(Case current, Labyrinthe labyrinthe) {
        List<Case> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // droite, gauche, bas, haut
        for (int[] dir : directions) {
            int newX = current.getX() + dir[0];
            int newY = current.getY() + dir[1];
            if (newX >= 0 && newX < labyrinthe.getLargeur() && newY >= 0 && newY < labyrinthe.getHauteur()) {
                Case neighbor = labyrinthe.getCase(newX, newY);
                if (neighbor.getStatut() != Case.Statut.MUR) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    private void updateUI(Case c) {
        if (!allVisited.contains(c)) {
            allVisited.add(c);
            SwingUtilities.invokeLater(() -> {
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
                Timer timer = new Timer(100, e -> {});
                timer.setRepeats(false);
                timer.start();
            });
        }
    }
}