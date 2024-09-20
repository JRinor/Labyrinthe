// src/algorithms/IDAStar.java
package algorithms;

import models.Case;
import models.Labyrinthe;

import java.util.*;

public class IDAStar {
    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        int threshold = ManhattanHeuristic.calculate(start, goal);
        List<Case> path = new ArrayList<>();
        List<Case> allVisited = new ArrayList<>();
        path.add(start);

        while (true) {
            int temp = search(path, 0, threshold, goal, labyrinthe, allVisited);
            if (temp == -1) {
                Map<String, List<Case>> result = new HashMap<>();
                result.put("shortestPath", new ArrayList<>(path));
                result.put("allVisited", allVisited);
                return result;
            }
            if (temp == Integer.MAX_VALUE) {
                return null;
            }
            threshold = temp;
        }
    }

    private int search(List<Case> path, int g, int threshold, Case goal, Labyrinthe labyrinthe, List<Case> allVisited) {
        Case current = path.get(path.size() - 1);
        allVisited.add(current);
        int f = g + ManhattanHeuristic.calculate(current, goal);
        if (f > threshold) {
            return f;
        }
        if (current.equals(goal)) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        for (Case neighbor : current.getNeighbors(labyrinthe)) {
            if (!path.contains(neighbor)) {
                path.add(neighbor);
                int temp = search(path, g + neighbor.getCost(), threshold, goal, labyrinthe, allVisited);
                if (temp == -1) {
                    return -1;
                }
                if (temp < min) {
                    min = temp;
                }
                path.remove(path.size() - 1);
            }
        }
        return min;
    }
}