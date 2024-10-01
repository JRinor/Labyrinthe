package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import algorithms.AStar;
import models.Labyrinthe;
import models.Case;
import vues.VueGrille;

import java.util.List;
import java.util.Map;

class AStarTest {

    private AStar aStar;
    private Labyrinthe labyrinthe;
    private VueGrille vueGrille;

    @BeforeEach
    void setUp() {
        labyrinthe = new Labyrinthe(5, 5);
        vueGrille = new VueGrille(5, 5, labyrinthe);
        aStar = new AStar(vueGrille);
    }

    @Test
    void testPathFound() {
        Case start = labyrinthe.getCase(0, 0);
        Case goal = labyrinthe.getCase(4, 4);
        Map<String, Object> result = aStar.search(labyrinthe, start, goal);

        assertNotNull(result.get("shortestPath"));
        assertTrue(((List<Case>)result.get("shortestPath")).size() > 0);
    }


    @Test
    void testStartEqualsGoal() {
        Case start = labyrinthe.getCase(0, 0);
        Map<String, Object> result = aStar.search(labyrinthe, start, start);

        assertNotNull(result.get("shortestPath"));
        assertEquals(1, ((List<Case>)result.get("shortestPath")).size());
    }

    @Test
    void testNullStartOrGoal() {
        Case start = labyrinthe.getCase(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            aStar.search(labyrinthe, null, start);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            aStar.search(labyrinthe, start, null);
        });
    }
}
