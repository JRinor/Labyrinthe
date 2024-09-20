// src/algorithms/Dijkstra.java
package algorithms;

import models.Case;
import models.Labyrinthe;

import java.util.*;

public class Dijkstra {
    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        PriorityQueue<Case> frontier = new PriorityQueue<>(Comparator.comparingInt(Case::getCost));
        Map<Case, Case> cameFrom = new HashMap<>();
        Map<Case, Integer> costSoFar = new HashMap<>();
        List<Case> allVisited = new ArrayList<>();
        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);

            if (current.equals(goal)) {
                break;
            }

            for (Case next : current.getNeighbors(labyrinthe)) {
                int newCost = costSoFar.get(current) + next.getCost();
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", reconstructPath(cameFrom, start, goal));
        result.put("allVisited", allVisited);
        return result;
    }

    private List<Case> reconstructPath(Map<Case, Case> cameFrom, Case start, Case goal) {
        List<Case> path = new ArrayList<>();
        Case current = goal;
        while (!current.equals(start)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }
}