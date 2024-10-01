package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import java.awt.Color;
import java.util.*;
import javax.swing.SwingUtilities;

public class BreadthFirstSearch {
    private final VueGrille vueGrille;
    private final AlgorithmStats stats;

    public BreadthFirstSearch(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
        this.stats = new AlgorithmStats();
    }

    public Map<String, Object> search(Labyrinthe labyrinthe, Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Les cases de départ et d'arrivée ne peuvent pas être nulles");
        }

        stats.reset();
        long startTime = System.currentTimeMillis();

        Queue<Case> frontier = new LinkedList<>();
        Map<Case, Case> cameFrom = new HashMap<>();
        Set<Case> allVisited = new HashSet<>();

        frontier.add(start);
        cameFrom.put(start, null);
        allVisited.add(start);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            stats.incrementStatesGenerated();
            updateUI(current, false);

            if (current.equals(goal)) {
                break;
            }

            for (Case next : getNeighbors(current, labyrinthe)) {
                if (!allVisited.contains(next)) {
                    frontier.add(next);
                    cameFrom.put(next, current);
                    allVisited.add(next);
                }
            }
        }

        List<Case> shortestPath = reconstructPath(cameFrom, start, goal);
        long endTime = System.currentTimeMillis();

        stats.setExecutionTime(endTime - startTime);
        stats.setSuccess(shortestPath != null);
        stats.setPathLength(shortestPath != null ? shortestPath.size() - 1 : 0);

        if (shortestPath != null) {
            shortestPath.forEach(c -> updateUI(c, true));
        }

        return Map.of(
                "shortestPath", shortestPath,
                "allVisited", new ArrayList<>(allVisited),
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
