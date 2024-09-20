package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import java.awt.Color;
import java.util.*;

public class DepthFirstSearch {
    private VueGrille vueGrille;
    private List<Case> allVisited;
    private Map<Case, Case> cameFrom;
    private boolean found;

    public DepthFirstSearch(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        allVisited = new ArrayList<>();
        cameFrom = new HashMap<>();
        found = false;

        dfs(start, goal, labyrinthe);

        Map<String, List<Case>> result = new HashMap<>();
        result.put("shortestPath", found ? reconstructPath(cameFrom, start, goal) : null);
        result.put("allVisited", allVisited);
        return result;
    }

    private void dfs(Case current, Case goal, Labyrinthe labyrinthe) {
        allVisited.add(current);

        if (current.equals(goal)) {
            found = true;
            return;
        }

        List<Case> neighbors = getNeighbors(current, labyrinthe);
        for (Case next : neighbors) {
            if (!allVisited.contains(next) && !found) {
                cameFrom.put(next, current);
                dfs(next, goal, labyrinthe);
            }
        }
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
}
