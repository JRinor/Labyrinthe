package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.*;

public class IDAStar {
    private final VueGrille vueGrille;
    private final AlgorithmStats stats;

    public IDAStar(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
        this.stats = new AlgorithmStats();
    }

    public Map<String, Object> search(Labyrinthe labyrinthe, Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Start and goal cannot be null");
        }

        stats.reset();
        long startTime = System.currentTimeMillis();

        List<Case> bestPath = null;
        List<Case> allVisited = new ArrayList<>();
        int threshold = ManhattanHeuristic.calculate(start, goal);

        while (true) {
            SearchResult result = search(start, 0, threshold, goal, labyrinthe, new HashSet<>(), allVisited);
            if (result.found) {
                bestPath = result.path;
                break;
            }
            if (result.cost == Integer.MAX_VALUE) {
                break;
            }
            threshold = result.cost;
        }

        long endTime = System.currentTimeMillis();
        stats.setExecutionTime(endTime - startTime);
        stats.setSuccess(bestPath != null);
        stats.setPathLength(bestPath != null ? bestPath.size() - 1 : 0);

        if (bestPath != null) {
            bestPath.forEach(c -> updateUI(c, true, allVisited));
        }

        return Map.of(
                "shortestPath", bestPath,
                "allVisited", allVisited,
                "stats", stats
        );
    }

    private SearchResult search(Case current, int g, int threshold, Case goal, Labyrinthe labyrinthe, Set<Case> path, List<Case> allVisited) {
        path.add(current);
        updateUI(current, false, allVisited);
        stats.incrementStatesGenerated();

        int f = g + ManhattanHeuristic.calculate(current, goal);
        if (f > threshold) {
            return new SearchResult(f, null, false);
        }
        if (current.equals(goal)) {
            return new SearchResult(-1, new ArrayList<>(path), true);
        }

        int min = Integer.MAX_VALUE;
        for (Case neighbor : getNeighbors(current, labyrinthe)) {
            if (!path.contains(neighbor)) {
                SearchResult result = search(neighbor, g + 1, threshold, goal, labyrinthe, path, allVisited);
                if (result.found) {
                    return result;
                }
                if (result.cost < min) {
                    min = result.cost;
                }
            }
        }

        path.remove(current);
        return new SearchResult(min, null, false);
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

    private void updateUI(Case c, boolean isShortestPath, List<Case> allVisited) {
        if (!allVisited.contains(c) || isShortestPath) {
            if (!isShortestPath) {
                allVisited.add(c);
            }
            SwingUtilities.invokeLater(() -> {
                Color color = isShortestPath ? Color.CYAN : Color.YELLOW;
                vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), color);
            });
        }
    }

    private static class SearchResult {
        final int cost;
        final List<Case> path;
        final boolean found;

        SearchResult(int cost, List<Case> path, boolean found) {
            this.cost = cost;
            this.path = path;
            this.found = found;
        }
    }
}
