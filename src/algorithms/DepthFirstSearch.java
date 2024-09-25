package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.*;
import java.awt.Color;
import java.util.*;

public class DepthFirstSearch {
    private VueGrille vueGrille;
    private List<Case> bestPath;
    private List<Case> allVisited;

    public DepthFirstSearch(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        bestPath = null;
        allVisited = new ArrayList<>();
        Stack<Case> frontier = new Stack<>();
        Map<Case, Case> cameFrom = new HashMap<>();
        frontier.push(start);
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Case current = frontier.pop();
            allVisited.add(current);
            updateUI(current, false);

            if (current.equals(goal)) {
                bestPath = reconstructPath(cameFrom, start, goal);
                break;
            }

            List<Case> neighbors = getNeighbors(current, labyrinthe);
            for (Case next : neighbors) {
                if (!cameFrom.containsKey(next)) {
                    frontier.push(next);
                    cameFrom.put(next, current);
                }
            }
        }

        if (bestPath != null) {
            for (Case c : bestPath) {
                updateUI(c, true);
            }
        }

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", bestPath);
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