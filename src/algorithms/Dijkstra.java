package algorithms;

import models.Case;
import models.Labyrinthe;
import vues.VueGrille;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.*;

public class Dijkstra {
    private VueGrille vueGrille;

    public Dijkstra(VueGrille vueGrille) {
        this.vueGrille = vueGrille;
    }

    public Map<String, List<Case>> search(Labyrinthe labyrinthe, Case start, Case goal) {
        PriorityQueue<Case> frontier = new PriorityQueue<>(Comparator.comparingInt(Case::getCost));
        Map<Case, Case> cameFrom = new HashMap<>();
        Map<Case, Integer> costSoFar = new HashMap<>();
        List<Case> allVisited = new ArrayList<>();

        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);
        start.setCost(0);

        while (!frontier.isEmpty()) {
            Case current = frontier.poll();
            allVisited.add(current);
            updateUI(current);

            if (current.equals(goal)) {
                break;
            }

            List<Case> neighbors = current.getNeighbors(labyrinthe);
            for (Case next : neighbors) {
                int newCost = costSoFar.get(current) + 1; // Coût uniforme de 1
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    next.setCost(newCost);
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

    private void updateUI(Case c) {
        SwingUtilities.invokeLater(() -> {
            vueGrille.updateButtonColor(vueGrille.getButtonForCase(c), Color.YELLOW);
            try {
                Thread.sleep(100); // Délai de 100ms pour ralentir l'affichage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
