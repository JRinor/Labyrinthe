package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import java.awt.Color;
import java.util.*;
import javax.swing.SwingUtilities;

public class BreadthFirstSearch {
    private VueGrille vueGrille;

    public BreadthFirstSearch(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        Queue<Case> frontier = new LinkedList<>();
        Map<Case, Case> cameFrom = new HashMap<>();
        List<Case> allVisited = new ArrayList<>();
        frontier.add(start);
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);

            if (current.equals(goal)) {
                break;
            }

            List<Case> neighbors = getNeighbors(current, labyrinthe);
            for (Case next : neighbors) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, current);
                    updateUI(next);
                }
            }
        }

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", reconstructPath(cameFrom, start, goal));
        result.put("allVisited", allVisited);
        return result;
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

    private List<Case> reconstructPath(Map<Case, Case> cameFrom, Case start, Case goal) {
        List<Case> path = new ArrayList<>();
        Case current = goal;
        while (current != null && !current.equals(start)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        if (current != null) {
            path.add(start);
            Collections.reverse(path);
            return path;
        } else {
            return null; // Aucun chemin trouvé
        }
    }

    private void updateUI(Case c) {
        SwingUtilities.invokeLater(() -> {
            vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
            try {
                Thread.sleep(100); // Délai de 100ms pour ralentir l'affichage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
