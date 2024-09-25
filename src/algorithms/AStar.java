package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.*;
import java.awt.Color;
import java.util.*;

public class AStar {
    private VueGrille vueGrille;

    public AStar(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Les cases de départ et d'arrivée ne peuvent pas être nulles");
        }

        Map<Case, Integer> costSoFar = new HashMap<>();
        PriorityQueue<Case> frontier = new PriorityQueue<>(Comparator.comparingInt(c -> costSoFar.get(c) + ManhattanHeuristic.calculate(c, goal)));
        Map<Case, Case> cameFrom = new HashMap<>();
        List<Case> allVisited = new ArrayList<>();

        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);
            updateUI(current, false);

            if (current.equals(goal)) {
                break;
            }

            List<Case> neighbors = getNeighbors(current, labyrinthe);
            for (Case next : neighbors) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        List<Case> shortestPath = reconstructPath(cameFrom, start, goal);
        if (shortestPath != null) {
            for (Case c : shortestPath) {
                updateUI(c, true);
            }
        }

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", shortestPath);
        result.put("allVisited", allVisited);
        return result;
    }

    private List<Case> getNeighbors(Case current, Labyrinthe labyrinthe) {
        List<Case> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
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