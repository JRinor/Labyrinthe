package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.*;

public class Dijkstra {
    private final VueGrille vueGrille;
    private final AlgorithmStats stats;

    public Dijkstra(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
        this.stats = new AlgorithmStats();
    }

    public Map<String, Object> search(Labyrinthe labyrinthe, Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Les cases de départ et d'arrivée ne peuvent pas être nulles");
        }

        stats.reset();
        long startTime = System.currentTimeMillis();

        List<Case> bestPath = null;
        List<Case> allVisited = new ArrayList<>();
        PriorityQueue<Case> frontier = new PriorityQueue<>(Comparator.comparingInt(Case::getCost));
        Map<Case, Case> cameFrom = new HashMap<>();
        Map<Case, Integer> costSoFar = new HashMap<>();
        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);
            updateUI(current, false);
            stats.incrementStatesGenerated();

            if (current.equals(goal)) {
                bestPath = reconstructPath(cameFrom, start, goal);
                break;
            }

            for (Case next : getNeighbors(current, labyrinthe)) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    next.setCost(newCost);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        stats.setExecutionTime(endTime - startTime);
        stats.setSuccess(bestPath != null);
        stats.setPathLength(bestPath != null ? bestPath.size() - 1 : 0);

        if (bestPath != null) {
            bestPath.forEach(c -> updateUI(c, true));
        }

        return Map.of(
                "shortestPath", bestPath,
                "allVisited", allVisited,
                "stats", stats
        );
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
            return null;
        }
    }

    private void updateUI(Case c, boolean isShortestPath) {
        SwingUtilities.invokeLater(() -> {
            Color color = isShortestPath ? Color.CYAN : Color.YELLOW;
            vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), color);
        });
    }
}
