package algorithms;

import models.Case;

public class ManhattanHeuristic {
    public static int calculate(Case start, Case goal) {
        if (start == null || goal == null) {
            throw new IllegalArgumentException("Les cases de départ et d'arrivée ne peuvent pas être nulles");
        }
        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY());
    }
}
