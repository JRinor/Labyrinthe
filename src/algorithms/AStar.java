package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.*;

public class AStar {
    private VueGrille vueGrille;

    public AStar(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        Map<Case, Integer> costSoFar = new HashMap<>();
        PriorityQueue<Case> frontier = new PriorityQueue<>(
                Comparator.comparingInt(c -> costSoFar.getOrDefault(c, Integer.MAX_VALUE) + ManhattanHeuristic.calculate(c, goal))
        );
        Map<Case, Case> cameFrom = new HashMap<>();
        Set<Case> allVisited = new HashSet<>();

        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);

            if (current.equals(goal)) {
                break;
            }

            for (Case next : getNeighbors(current, labyrinthe)) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    int priority = newCost + ManhattanHeuristic.calculate(next, goal);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }

            updateUI(current);
        }

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", reconstructPath(cameFrom, start, goal));
        result.put("allVisited", new ArrayList<>(allVisited));
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
            try {
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
                Thread.sleep(50); // Réduit le délai pour une exécution plus rapide
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
