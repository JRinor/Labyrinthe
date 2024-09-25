package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.Color;
import java.util.*;

public class GreedyBestFirstSearch {
    private VueGrille vueGrille;
    private List<Case> bestPath;
    private List<Case> allVisited;

    public GreedyBestFirstSearch(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Start and goal cannot be null");
        }

        bestPath = null;
        allVisited = new ArrayList<>();
        PriorityQueue<Case> frontier = new PriorityQueue<>(Comparator.comparingInt(c -> ManhattanHeuristic.calculate(c, goal)));
        Map<Case, Case> cameFrom = new HashMap<>();
        frontier.add(start);
        cameFrom.put(start, null);

        long startTime = System.currentTimeMillis();

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);
            updateUI(current);

            if (current.equals(goal)) {
                bestPath = reconstructPath(cameFrom, start, goal);
                break;
            }

            List<Case> neighbors = getNeighbors(current, labyrinthe);
            for (Case next : neighbors) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Temps d'exécution : " + executionTime + " ms");
        System.out.println("Nombre de cases explorées : " + allVisited.size());

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

    private void updateUI(Case c) {
        SwingUtilities.invokeLater(() -> {
            vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
            Timer timer = new Timer(100, e -> {});
            timer.setRepeats(false);
            timer.start();
        });
    }
}