// src/algorithms/DepthFirstSearch.java
package algorithms;

import models.Case;
import models.Labyrinthe;

import java.util.*;

public class DepthFirstSearch {
    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        Stack<Case> frontier = new Stack<>();
        Map<Case, Case> cameFrom = new HashMap<>();
        List<Case> allVisited = new ArrayList<>();
        frontier.push(start);
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Case current = frontier.pop();
            allVisited.add(current);

            if (current.equals(goal)) {
                break;
            }

            for (Case next : current.getNeighbors(labyrinthe)) {
                if (!cameFrom.containsKey(next)) {
                    frontier.push(next);
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