package algorithms;

import models.Case;

public class ManhattanHeuristic {
    public static int calculate(Case start, Case goal) {
        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY());
    }
}